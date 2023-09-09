package com.yh.weatherpush.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.dto.qywx.QywxTagDTO;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.dto.tag.TagMembersParam;
import com.yh.weatherpush.dto.tag.TagPageParam;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.entity.TaskRelTag;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.manager.QywxManager;
import com.yh.weatherpush.mapper.TagMapper;
import com.yh.weatherpush.mapper.TaskRelTagMapper;
import com.yh.weatherpush.service.TagService;
import com.yh.weatherpush.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private QywxManager qywxManager;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private TaskRelTagMapper taskRelTagMapper;

    @Override
    public void create(AddTagParam param) {
        String code = weatherService.getLocation(param.getTagName());
        Tag tag = new Tag();
        tag.setCode(code);
        tag.setCtime(LocalDateTime.now());
        tag.setTagName(param.getTagName());
        super.save(tag);
        qywxManager.createTag(tag.getId(), param.getTagName());
    }

    @Override
    public void delete(Long id) {
        Tag tag = super.getById(id);
        if (null == tag) {
            throw new ApiException("标签不存在");
        }
        qywxManager.deleteTag(tag.getId());
        super.removeById(id);
    }

    @Override
    public IPage<TagDTO> pageList(TagPageParam pageParam) {
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
        return page.convert(TagDTO::covertFromTag);
    }

    @Override
    public List<TagDTO> getAll() {
        List<Tag> list = this.list();
        if (CollUtil.isNotEmpty(list)) {
            return list.stream().map(TagDTO::covertFromTag).collect(Collectors.toList());
        }
        // todo 怎么去同步数据
        List<QywxTagDTO> tags = qywxManager.getAllTags();
        if (CollUtil.isEmpty(tags)) {
            return CollUtil.newArrayList();
        }
        return tags.stream().map(TagDTO::covertFromQywxTagDTO).collect(Collectors.toList());
    }

    @Override
    public void addTagMembers(TagMembersParam param) {
        qywxManager.addTagMembers(param);
    }

    @Override
    public void delTagMembers(TagMembersParam param) {
        qywxManager.delTagMembers(param);
    }

    @Override
    public List<Tag> getTagListForJob(String taskId) {
        List<Tag> tagList = new ArrayList<>();
        List<TaskRelTag> trtList =
                taskRelTagMapper.selectList(new QueryWrapper<TaskRelTag>().lambda().eq(TaskRelTag::getTaskId, taskId));
        if (CollUtil.isNotEmpty(trtList)) {
            List<Integer> tagIdList = trtList.stream().map(TaskRelTag::getTagId).collect(Collectors.toList());
            tagList = super.listByIds(tagIdList);
        }
        return tagList;
    }
}
