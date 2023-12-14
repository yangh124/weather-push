package com.yh.weatherpush.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.dto.admin.LoginParam;
import com.yh.weatherpush.dto.admin.UpdPwdParam;
import com.yh.weatherpush.entity.Admin;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.mapper.AdminMapper;
import com.yh.weatherpush.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-03-16
 */
@AllArgsConstructor
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private final AdminMapper adminMapper;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Cacheable(value = {"admin"}, key = "#username")
    @Override
    public Admin getAdminByUsername(String username) {
        Admin admin = getAdmin(username);
        admin.setPassword("");
        return admin;
    }

    private Admin getAdmin(String username) {
        LambdaQueryWrapper<Admin> eq =
                new QueryWrapper<Admin>().lambda().eq(Admin::getUsername, username).last("LIMIT 1");
        return adminMapper.selectOne(eq);
    }

    @Override
    public SaTokenInfo login(LoginParam param) {
        String username = param.getUsername();
        Admin admin = getAdmin(username);
        if (null == admin) {
            throw new ApiException("用户名或密码错误");
        }
        if (!passwordEncoder.matches(param.getPassword(), admin.getPassword())) {
            throw new ApiException("密码不正确");
        }
        StpUtil.login(username);
        return StpUtil.getTokenInfo();
    }


    @CacheEvict(value = "admin", key = "#updPwdParam.username")
    @Override
    public void updatePassword(UpdPwdParam updPwdParam) {
        Admin admin = getAdmin(updPwdParam.getUsername());

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
        StpUtil.logout();
    }

}
