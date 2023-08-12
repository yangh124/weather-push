package com.yh.weatherpush.manager.mapstruct;

import com.yh.weatherpush.dto.QuartzBean;
import com.yh.weatherpush.dto.schtask.AddTaskParam;
import com.yh.weatherpush.dto.schtask.SchTaskPageDTO;
import com.yh.weatherpush.dto.schtask.UpdateTaskDTO;
import com.yh.weatherpush.entity.SchTask;
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
