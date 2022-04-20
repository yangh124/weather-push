package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.schtask.AddTaskParam;
import com.yh.weatherpush.dto.schtask.UpdateTaskDTO;
import com.yh.weatherpush.dto.tag.TagDTO;
import com.yh.weatherpush.entity.SchTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author yh
 * @since 2022-04-17
 */
public interface SchTaskService extends IService<SchTask> {

    IPage<SchTask> pageList(PageParam pageParam);

    void create(AddTaskParam param);

    void delete(Long id);

    void updateTask(Long id, UpdateTaskDTO dto);

}
