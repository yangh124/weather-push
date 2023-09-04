package com.yh.weatherpush.manager.mapstruct;

import com.yh.api.dto.qywx.request.TagUsersReqDTO;
import com.yh.weatherpush.dto.tag.TagMembersParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IQywxMapper {

    IQywxMapper INSTANCE = Mappers.getMapper(IQywxMapper.class);


    TagUsersReqDTO toTagUsersReqDTO(TagMembersParam param);
}
