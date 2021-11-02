package com.yh.weatherpush.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/11/2 15:31
 */
@Data
public class TagLocation implements Serializable {

    private static final long serialVersionUID = 8933388270696580369L;

    private Integer tagid;

    private String tagname;

    private String code;
}
