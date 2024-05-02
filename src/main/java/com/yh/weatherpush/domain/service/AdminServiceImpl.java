package com.yh.weatherpush.domain.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.application.service.AdminService;
import com.yh.weatherpush.infrastructure.exception.ApiException;
import com.yh.weatherpush.infrastructure.mapper.AdminMapper;
import com.yh.weatherpush.infrastructure.po.Admin;
import com.yh.weatherpush.interfaces.dto.admin.LoginParam;
import com.yh.weatherpush.interfaces.dto.admin.UpdPwdParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
        String s = SaSecureUtil.md5(param.getPassword());
        if (!admin.getPassword().equals(s)) {
            throw new ApiException("密码不正确");
        }
        StpUtil.login(username);
        return StpUtil.getTokenInfo();
    }


    @CacheEvict(value = "admin", key = "#updPwdParam.username")
    @Override
    public void updatePassword(UpdPwdParam updPwdParam) {
        Admin admin = getAdmin(updPwdParam.getUsername());
        String oldPassword = SaSecureUtil.md5(updPwdParam.getOldPassword());
        String password = admin.getPassword();
        boolean matches = StrUtil.equals(oldPassword, password);
        if (!matches) {
            throw new ApiException("原密码错误");
        }
        String newPassword = updPwdParam.getNewPassword();
        String confirmPassword = updPwdParam.getConfirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            throw new ApiException("确认密码与新密码不一致!");
        }
        String pwd = SaSecureUtil.md5(newPassword);
        LambdaUpdateWrapper<Admin> updateWrapper =
                new UpdateWrapper<Admin>().lambda().set(Admin::getPassword, pwd).eq(Admin::getId, admin.getId());
        update(updateWrapper);
        StpUtil.logout();
    }

}
