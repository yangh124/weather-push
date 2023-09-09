package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.dto.PageParam;
import com.yh.weatherpush.dto.schtask.AddTaskParam;
import com.yh.weatherpush.dto.schtask.SchTaskPageDTO;
import com.yh.weatherpush.dto.schtask.UpdateTaskDTO;
import com.yh.weatherpush.entity.SchTask;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author yh
 * @since 2022-04-17
 */
public interface SchTaskService extends IService<SchTask> {

    IPage<SchTaskPageDTO> pageList(PageParam pageParam);

    void create(AddTaskParam param);

    void delete(Integer id);

    void updateTask(Integer id, UpdateTaskDTO dto);

}
