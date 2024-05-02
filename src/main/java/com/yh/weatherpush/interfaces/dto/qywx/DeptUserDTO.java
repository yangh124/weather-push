package com.yh.weatherpush.interfaces.dto.qywx;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeptUserDTO implements Serializable {

    private static final long serialVersionUID = -7533700638346181616L;

    @JSONField(name = "userid")
    private String userId;

    private Integer department;

}
