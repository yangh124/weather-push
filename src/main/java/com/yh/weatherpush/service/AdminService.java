package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.dto.admin.LoginParam;
import com.yh.weatherpush.dto.admin.UpdPwdParam;
import com.yh.weatherpush.entity.Admin;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author yh
 * @since 2022-03-16
 */
public interface AdminService extends IService<Admin> {

    Admin getAdminByUsername(String username);

    String login(LoginParam param);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 修改密码
     *
     * @param updPwdParam
     */
    void updatePassword(UpdPwdParam updPwdParam);
}
