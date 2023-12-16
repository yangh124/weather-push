package com.yh.weatherpush.dto.qywx.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2022/4/9 11:51
 */
@Data
public class TagUsersReqDTO implements Serializable {
    private static final long serialVersionUID = -7506128416856466716L;

    /**
     * 标签ID
     */
    @JSONField(name = "tagid")
    private Integer tagId;

    /**
     * 企业成员ID列表
     */
    @JSONField(name = "userlist")
    private List<String> userList;

    /**
     * 企业部门ID列表
     */
    @JSONField(name = "partylist")
    private List<Integer> partyList;
}
