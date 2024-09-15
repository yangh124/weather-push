package com.yh.weatherpush.dto.qywx;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DeptUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7533700638346181616L;

    @JSONField(name = "userid")
    private String userId;

    private Integer department;

}
