package com.yh.weatherpush.dto.qywx.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserListIdReqDTO implements Serializable {

    private static final long serialVersionUID = -4420845228624008393L;

    /**
     * 用于分页查询的游标，字符串类型，由上一次调用返回，首次调用不填
     */
    private String cursor;

    /**
     * 分页，预期请求的数据量，取值范围 1 ~ 10000
     */
    private Integer limit;
}
