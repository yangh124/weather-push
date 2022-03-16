package com.yh.weatherpush.service;

import com.yh.weatherpush.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.entity.Permission;

import java.util.List;

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

    List<Permission> getPermissionList(Long id);
}
