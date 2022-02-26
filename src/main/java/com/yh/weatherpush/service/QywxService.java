package com.yh.weatherpush.service;

import com.yh.weatherpush.entity.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2021/10/31 13:13
 */
public interface QywxService {

    /**
     * 发送文本消息
     */
    void pushWeatherMsg(String token, Map<Integer, String> weatherMap);

    /**
     * 创建标签
     * 
     * @param tagId 标签id
     * @param tagName 标签名字
     * @return
     */
    Tag createTag(Integer tagId, String tagName);

    /**
     * 获取用户标签
     * 
     */
    List<Tag> getAllTags();

    /**
     * 获取token
     * 
     * @return
     */
    String getToken();
}