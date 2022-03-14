package com.yh.weatherpush.dto.qywx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2022/3/14 21:34
 */
@Data
public class MemberResp implements Serializable {

    private static final long serialVersionUID = 5767043043513141435L;

    @JSONField(name = "userid")
    private String userId;

    private String name;

    private List<Integer> department;

    @JSONField(name = "open_userid")
    private String openUserId;
}
