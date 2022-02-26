package com.yh.weatherpush.dto.qywx;

import com.yh.weatherpush.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/10/31 18:00
 */
@Data
public class TabResp implements Serializable {

    private static final long serialVersionUID = -664797287490077925L;
    /**
     * 返回码
     */
    private Integer errcode;

    /**
     * 对返回码的文本描述内容
     */
    private String errmsg;

    /**
     * 标签
     */
    private List<Tag> taglist;
}
