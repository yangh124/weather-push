package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.weatherpush.dto.tag.TagMembersParam;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.dto.tag.TagPageParam;
import com.yh.weatherpush.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 企业微信标签 服务类
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
public interface TagService extends IService<Tag> {

    void create(AddTagParam param);

    void delete(Long id);

    IPage<TagDTO> pageList(TagPageParam pageParam);

    List<TagDTO> getAll();

    void addTagMembers(TagMembersParam param);

    void delTagMembers(TagMembersParam param);

    List<Tag> getTagListForJob(String taskId);
}
