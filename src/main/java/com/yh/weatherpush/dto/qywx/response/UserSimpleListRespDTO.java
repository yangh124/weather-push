package com.yh.weatherpush.dto.qywx.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.yh.weatherpush.dto.qywx.MemberDTO;
import com.yh.weatherpush.dto.qywx.QywxBaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSimpleListRespDTO extends QywxBaseRespDTO implements Serializable {

    private static final long serialVersionUID = 5143568771873730136L;

    @JSONField(name = "userlist")
    private List<MemberDTO> userList;
}
