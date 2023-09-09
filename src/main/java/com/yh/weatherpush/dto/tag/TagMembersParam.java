package com.yh.weatherpush.dto.tag;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author : yh
 * @date : 2022/4/9 11:51
 */
@Data
public class TagMembersParam implements Serializable {
    private static final long serialVersionUID = -7506128416856466716L;

    @NotNull(message = "id不能为空")
    @JSONField(name = "tagid")
    private Long tagId;

    @NotEmpty
    @JSONField(name = "userlist")
    private List<String> userList;
}
