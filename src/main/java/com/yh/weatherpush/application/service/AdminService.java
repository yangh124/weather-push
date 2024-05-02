package com.yh.weatherpush.application.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.infrastructure.po.Admin;
import com.yh.weatherpush.interfaces.dto.admin.LoginParam;
import com.yh.weatherpush.interfaces.dto.admin.UpdPwdParam;

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

    SaTokenInfo login(LoginParam param);

    /**
     * 修改密码
     *
     * @param updPwdParam
     */
    void updatePassword(UpdPwdParam updPwdParam);

}
