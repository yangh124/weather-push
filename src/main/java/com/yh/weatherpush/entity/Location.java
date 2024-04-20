package com.yh.weatherpush.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 企业微信标签
 * </p>
 *
 * @author yh
 * @since 2023-09-09
 */
@Getter
@Setter
@TableName("sys_location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业微信tag_name（城市name）
     */
    private String locationName;

    /**
     * 地区编码
     */
    private String code;

    /**
     * 企业微信标签id
     */
    private Integer tagId;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 修改时间
     */
    private LocalDateTime utime;


    @TableLogic
    private Integer isDelete;
}
