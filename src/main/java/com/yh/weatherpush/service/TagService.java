package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.tag.AddTagParam;
import com.yh.weatherpush.dto.tag.TagDTO;
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

    IPage<TagDTO> pageList(PageParam pageParam);

    List<TagDTO> getAll();

}
