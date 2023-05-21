package com.yh.weatherpush.dto.qywx.response;

import com.yh.weatherpush.dto.qywx.QywxBaseRespDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSimpleListRespDTO extends QywxBaseRespDTO implements Serializable {

    private static final long serialVersionUID = 5143568771873730136L;

    /**
     * 分页游标，下次请求时填写以获取之后分页的记录。如果该字段返回空则表示已没有更多数据
     */
    private String nextCursor;


}
