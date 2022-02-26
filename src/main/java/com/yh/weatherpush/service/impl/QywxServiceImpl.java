package com.yh.weatherpush.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.config.QywxConfig;
import com.yh.weatherpush.dto.qywx.*;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.service.QywxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : yh
 * @date : 2021/10/31 13:14
 */
@Service
public class QywxServiceImpl implements QywxService {

    @Autowired
    private QywxConfig qywxConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void pushWeatherMsg(String token, Map<Integer, String> weatherMap) {
        for (Integer tagid : weatherMap.keySet()) {
            String msg = weatherMap.get(tagid);
            String sendUrl = qywxConfig.getSendUrl();
            sendUrl = sendUrl.replace("ACCESS_TOKEN", token);
            TextMsgReq textMsgReq = TextMsgReq.builder().agentid(qywxConfig.getAgentid()).totag(tagid).msgtype("text")
                .text(new Text(msg)).build();
            restTemplate.postForEntity(sendUrl, textMsgReq, MsgResp.class);
        }
    }

    @Override
    public Tag createTag(Integer tagId, String tagName) {
        String createUrl = qywxConfig.getLabel().getCreateUrl();
        String token = getToken();
        createUrl = createUrl.replace("ACCESS_TOKEN", token);
        JSONObject param = new JSONObject();
        if (null != tagId) {
            param.put("tagid", tagId);
        }
        param.put("tagname", tagName);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(createUrl, param, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("创建标签失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("创建标签失败! -> " + body.getString("errmsg"));
        }
        tagId = body.getInteger("tagid");
        Tag tag = new Tag();
        tag.setTagId(tagId);
        tag.setTagName(tagName);
        return tag;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Tag> getAllTags() {
        String labelUrl = qywxConfig.getLabel().getListUrl();
        String token = getToken();
        labelUrl = labelUrl.replace("ACCESS_TOKEN", token);
        ResponseEntity<TabResp> response = restTemplate.getForEntity(labelUrl, TabResp.class);
        TabResp body = response.getBody();
        if (null == body) {
            throw new ApiException("获取标签失败!");
        }
        Integer errcode = body.getErrcode();
        if (!errcode.equals(0)) {
            throw new ApiException("获取token失败! -> " + body);
        }
        return body.getTaglist();
    }

    /**
     * 1.access_token的有效期通过返回的expires_in来传达，正常情况下为7200秒（2小时），有效期内重复获取返回相同结果，过期后获取会返回新的access_token。
     * 2.由于企业微信每个应用的access_token是彼此独立的，所以进行缓存时需要区分应用来进行存储。 3.access_token至少保留512字节的存储空间。
     * 4.企业微信可能会出于运营需要，提前使access_token失效，开发者应实现access_token失效时重新获取的逻辑。
     *
     * @return access_token
     */
    @Override
    public String getToken() {
        Boolean aBoolean = redisTemplate.hasKey("access_token");
        if (BooleanUtil.isTrue(aBoolean)) {
            return (String)redisTemplate.opsForValue().get("access_token");
        }
        ResponseEntity<TokenResp> response = restTemplate.getForEntity(qywxConfig.getTokenUrl(), TokenResp.class);
        TokenResp body = response.getBody();
        if (null == body) {
            throw new ApiException("获取token失败!");
        }
        String errcode = body.getErrcode();
        if (!"0".equals(errcode)) {
            throw new ApiException("获取token失败! -> " + body);
        }
        String access_token = body.getAccess_token();
        redisTemplate.opsForValue().set("access_token", access_token, 1, TimeUnit.HOURS);
        return access_token;
    }
}
