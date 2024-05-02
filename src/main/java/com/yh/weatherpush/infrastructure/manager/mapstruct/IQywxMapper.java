package com.yh.weatherpush.infrastructure.manager.mapstruct;

import com.yh.weatherpush.interfaces.dto.qywx.request.TagUsersReqDTO;
import com.yh.weatherpush.interfaces.dto.tag.TagMembersParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IQywxMapper {

    IQywxMapper INSTANCE = Mappers.getMapper(IQywxMapper.class);


    TagUsersReqDTO toTagUsersReqDTO(TagMembersParam param);
}
