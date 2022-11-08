package com.yh.weatherpush.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.component.JwtTokenUtil;
import com.yh.weatherpush.dto.AdminUserDetails;
import com.yh.weatherpush.dto.admin.LoginParam;
import com.yh.weatherpush.dto.admin.UpdPwdParam;
import com.yh.weatherpush.entity.Admin;
import com.yh.weatherpush.entity.Permission;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.mapper.AdminMapper;
import com.yh.weatherpush.service.AdminService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-03-16
 */
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminMapper adminMapper;

    @Cacheable(value = {"admin"}, key = "#username")
    @Override
    public Admin getAdminByUsername(String username) {
        LambdaQueryWrapper<Admin> eq =
            new QueryWrapper<Admin>().lambda().eq(Admin::getUsername, username).last("LIMIT 1");
        return adminMapper.selectOne(eq);
    }

    @Override
    public List<Permission> getPermissionList(Long id) {
        return new ArrayList<>();
    }

    @Override
    public String login(LoginParam param) {
        String token = null;
        // 密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(param.getUsername());
            if (!passwordEncoder.matches(param.getPassword(), userDetails.getPassword())) {
                throw new ApiException("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                throw new ApiException("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 获取用户信息
        Admin admin = getAdminByUsername(username);
        if (admin != null) {
            return new AdminUserDetails(admin, null);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @CacheEvict(value ="admin",key = "#updPwdParam.username")
    @Override
    public void updatePassword(UpdPwdParam updPwdParam) {
        Admin admin = getAdminByUsername(updPwdParam.getUsername());
        boolean matches = passwordEncoder.matches(updPwdParam.getOldPassword(), admin.getPassword());
        if (!matches) {
            throw new ApiException("原密码错误");
        }
        String newPassword = updPwdParam.getNewPassword();
        String confirmPassword = updPwdParam.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new ApiException("确认密码与新密码不一致!");
        }
        String pwd = passwordEncoder.encode(newPassword);
        LambdaUpdateWrapper<Admin> updateWrapper =
            new UpdateWrapper<Admin>().lambda().set(Admin::getPassword, pwd).eq(Admin::getId, admin.getId());
        update(updateWrapper);
    }

}
