package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.entity.Admin;
import com.yh.weatherpush.entity.Permission;
import com.yh.weatherpush.mapper.AdminMapper;
import com.yh.weatherpush.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-03-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin getAdminByUsername(String username) {
        return null;
    }

    @Override
    public List<Permission> getPermissionList(Long id) {
        return null;
    }
}
