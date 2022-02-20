package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.mapper.TagMapper;
import com.yh.weatherpush.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业微信标签 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
