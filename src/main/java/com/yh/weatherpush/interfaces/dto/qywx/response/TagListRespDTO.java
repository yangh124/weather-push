package com.yh.weatherpush.interfaces.dto.qywx.response;

import com.alibaba.fastjson2.annotation.JSONField;
import com.yh.weatherpush.interfaces.dto.qywx.QywxRespDTO;
import com.yh.weatherpush.interfaces.dto.qywx.QywxTagDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/10/31 18:00
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class TagListRespDTO extends QywxRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -664797287490077925L;

    /**
     * 标签
     */
    @JSONField(name = "taglist")
    private List<QywxTagDTO> tagList;
}
