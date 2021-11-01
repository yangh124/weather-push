package com.yh.weatherpush.dto.qxwx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 17:54
 */
@Data
public class Tag implements Serializable {
    private static final long serialVersionUID = -8386641787283081838L;

    private Integer tagid;

    private String tagname;
}
