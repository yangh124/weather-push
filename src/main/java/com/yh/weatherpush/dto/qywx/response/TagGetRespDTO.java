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
public class TagGetRespDTO extends QywxBaseRespDTO implements Serializable {

    private static final long serialVersionUID = 6965548374951757640L;

    @JSONField(name = "tagname")
    private String tagName;

    @JSONField(name = "userlist")
    private List<MemberDTO> userList;
}
