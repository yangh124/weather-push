package com.yh.weatherpush.service;

import com.yh.weatherpush.dto.TagLocation;
import com.yh.weatherpush.dto.qxwx.Tag;

import java.util.List;

/**
 * @author : yh
 * @date : 2021/10/31 13:13
 */
public interface PushService {

    /**
     * 发送消息
     */
    void pushWeatherMsg(String token, List<TagLocation> tags);


    List<Tag> getTags(String token);

    String getToken();


    void pushWarningMsg(String token, List<TagLocation> tags);
}
