package com.yh.weatherpush.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 企业微信标签
 * </p>
 *
 * @author yh
 * @since 2022-02-19
 */
@Getter
@Setter
@TableName("sys_tag")
@ApiModel(value = "Tag对象", description = "企业微信标签")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(serializeUsing = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("企业微信tag_id（城市id）")
    private Integer tagId;

    @ApiModelProperty("企业微信tag_name（城市name）")
    private String tagName;

    @ApiModelProperty("地区编码")
    private String code;

    @ApiModelProperty("创建时间")
    private LocalDateTime ctime;

    @ApiModelProperty("修改时间")
    private LocalDateTime utime;
}
