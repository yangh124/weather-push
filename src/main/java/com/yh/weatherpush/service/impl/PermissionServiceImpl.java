package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.entity.Permission;
import com.yh.weatherpush.mapper.PermissionMapper;
import com.yh.weatherpush.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户权限表 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-03-16
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
