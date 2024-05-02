package com.yh.weatherpush.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.infrastructure.po.SchTask;
import com.yh.weatherpush.interfaces.dto.PageParam;
import com.yh.weatherpush.interfaces.dto.schtask.AddTaskParam;
import com.yh.weatherpush.interfaces.dto.schtask.SchTaskPageDTO;
import com.yh.weatherpush.interfaces.dto.schtask.UpdateTaskDTO;

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
