package com.yh.weatherpush.infrastructure.manager.mapstruct;

import com.yh.weatherpush.infrastructure.po.SchTask;
import com.yh.weatherpush.interfaces.dto.QuartzBean;
import com.yh.weatherpush.interfaces.dto.schtask.AddTaskParam;
import com.yh.weatherpush.interfaces.dto.schtask.SchTaskPageDTO;
import com.yh.weatherpush.interfaces.dto.schtask.UpdateTaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ISchTaskMapper {

    ISchTaskMapper INSTANCE = Mappers.getMapper(ISchTaskMapper.class);

    SchTaskPageDTO toSchTaskPageDTO(SchTask schTask);

    SchTask toSchTaskFromAdd(AddTaskParam param);

    QuartzBean toQuartzBean(SchTask schTask);

    SchTask toSchTaskFromUpdate(UpdateTaskDTO dto);
}
