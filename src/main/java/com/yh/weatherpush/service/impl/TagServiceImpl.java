package com.yh.weatherpush.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.mapper.TagMapper;
import com.yh.weatherpush.service.QywxService;
import com.yh.weatherpush.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Autowired
    private QywxService qywxService;
    @Autowired
    private WeatherService weatherService;

    @Override
    public void create(AddTagParam param) {
        String code = weatherService.getLocation(param.getTagName());
        Tag tag = qywxService.createTag(param.getTagId(), param.getTagName());
        tag.setCode(code);
        tag.setCtime(LocalDateTime.now());
        save(tag);
    }
}
