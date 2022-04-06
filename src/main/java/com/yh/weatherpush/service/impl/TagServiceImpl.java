package com.yh.weatherpush.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.mapper.TagMapper;
import com.yh.weatherpush.service.QywxService;
import com.yh.weatherpush.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        super.save(tag);
    }

    @Override
    public void delete(Long id) {
        Tag tag = super.getById(id);
        if (null == tag) {
            throw new ApiException("标签不存在");
        }
        qywxService.deleteTag(tag.getTagId());
        super.removeById(id);
    }

    @Override
    public IPage<TagDTO> pageList(PageParam pageParam) {
        IPage<Tag> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        String tagName = pageParam.getTagName();
        LambdaQueryWrapper<Tag> queryWrapper = null;
        if (StrUtil.isNotBlank(tagName)) {
            queryWrapper = new QueryWrapper<Tag>().lambda().likeRight(Tag::getTagName, tagName);
        }
        super.page(page, queryWrapper);
        List<Tag> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            return new Page<>();
        }
        List<TagDTO> tagDTOS = covertTagDTO(records);
        IPage<TagDTO> res = new Page<>();
        BeanUtil.copyProperties(page, res);
        res.setRecords(tagDTOS);
        return res;
    }

    @Override
    public List<TagDTO> getAll() {
        List<Tag> list = super.list();
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return covertTagDTO(list);
    }

    private List<TagDTO> covertTagDTO(List<Tag> list) {
        List<TagDTO> res = new ArrayList<>(list.size());
        for (Tag tag : list) {
            TagDTO tagDTO = new TagDTO();
            BeanUtil.copyProperties(tag, tagDTO);
            res.add(tagDTO);
        }
        return res;
    }
}
