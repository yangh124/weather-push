package com.yh.weatherpush.service;

import com.yh.weatherpush.dto.qxwx.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2021/10/31 13:13
 */
public interface PushService {

    /**
     * 发送文本消息
     */
    void pushWeatherMsg(String token, Map<Integer, String> weatherMap);

    /**
     * 获取用户标签
     * @param token
     * @return
     */
    List<Tag> getTags(String token);

    /**
     * 获取token
     * @return
     */
    String getToken();
}
