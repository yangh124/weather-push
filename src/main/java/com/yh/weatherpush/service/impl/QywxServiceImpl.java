package com.yh.weatherpush.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.config.property.QywxConfigProperties;
import com.yh.weatherpush.dto.qywx.*;
import com.yh.weatherpush.dto.tag.TagMembersParam;
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
    private QywxConfigProperties qywxConfig;
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
        String createUrl = qywxConfig.getTag().getCreateUrl();
        String token = getOtherToken();
        createUrl = createUrl.replace("ACCESS_TOKEN", token);
        JSONObject param = new JSONObject();
        if (null != tagId) {
            param.put("tagid", tagId);
        }
        param.put("tagname", tagName);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(createUrl, param, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("??????????????????!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("??????????????????! -> " + body.getString("errmsg"));
        }
        tagId = body.getInteger("tagid");
        Tag tag = new Tag();
        tag.setTagId(tagId);
        tag.setTagName(tagName);
        return tag;
    }

    @Override
    public void deleteTag(Integer tagId) {
        String deleteUrl = qywxConfig.getTag().getDeleteUrl();
        String token = getOtherToken();
        deleteUrl = deleteUrl.replace("ACCESS_TOKEN", token).replace("TAG_ID", String.valueOf(tagId));
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(deleteUrl, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("??????????????????!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("??????????????????! -> " + body.getString("errmsg"));
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<Tag> getAllTags() {
        String labelUrl = qywxConfig.getTag().getListUrl();
        String token = getOtherToken();
        labelUrl = labelUrl.replace("ACCESS_TOKEN", token);
        ResponseEntity<TabResp> response = restTemplate.getForEntity(labelUrl, TabResp.class);
        TabResp body = response.getBody();
        if (null == body) {
            throw new ApiException("??????????????????!");
        }
        Integer errcode = body.getErrcode();
        if (!errcode.equals(0)) {
            throw new ApiException("??????token??????! -> " + body);
        }
        return body.getTaglist();
    }

    /**
     * 1.access_token???????????????????????????expires_in??????????????????????????????7200??????2???????????????????????????????????????????????????????????????????????????????????????access_token???
     * 2.?????????????????????????????????access_token?????????????????????????????????????????????????????????????????????????????? 3.access_token????????????512????????????????????????
     * 4.???????????????????????????????????????????????????access_token???????????????????????????access_token?????????????????????????????????
     *
     * @return access_token
     */
    @Override
    public String getPushToken() {
        Boolean aBoolean = redisTemplate.hasKey("push_access_token");
        if (BooleanUtil.isTrue(aBoolean)) {
            return (String)redisTemplate.opsForValue().get("push_access_token");
        }
        String tokenUrl = qywxConfig.getTokenUrl();
        tokenUrl = tokenUrl.replace("SECRET", qywxConfig.getPushSecret());
        ResponseEntity<TokenResp> response = restTemplate.getForEntity(tokenUrl, TokenResp.class);
        TokenResp body = response.getBody();
        if (null == body) {
            throw new ApiException("??????token??????!");
        }
        String errcode = body.getErrcode();
        if (!"0".equals(errcode)) {
            throw new ApiException("??????token??????! -> " + body);
        }
        String access_token = body.getAccess_token();
        redisTemplate.opsForValue().set("push_access_token", access_token, 1, TimeUnit.HOURS);
        return access_token;
    }

    @Override
    public String getOtherToken() {
        Boolean aBoolean = redisTemplate.hasKey("other_access_token");
        if (BooleanUtil.isTrue(aBoolean)) {
            return (String)redisTemplate.opsForValue().get("other_access_token");
        }
        String tokenUrl = qywxConfig.getTokenUrl();
        tokenUrl = tokenUrl.replace("SECRET", qywxConfig.getOtherSecret());
        ResponseEntity<TokenResp> response = restTemplate.getForEntity(tokenUrl, TokenResp.class);
        TokenResp body = response.getBody();
        if (null == body) {
            throw new ApiException("??????token??????!");
        }
        String errcode = body.getErrcode();
        if (!"0".equals(errcode)) {
            throw new ApiException("??????token??????! -> " + body);
        }
        String access_token = body.getAccess_token();
        redisTemplate.opsForValue().set("other_access_token", access_token, 1, TimeUnit.HOURS);
        return access_token;
    }

    @Override
    public String getJoinQrCode() {
        String joinQrcodeUrl = qywxConfig.getMember().getJoinQrcodeUrl();
        String token = getOtherToken();
        joinQrcodeUrl = joinQrcodeUrl.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(joinQrcodeUrl, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("????????????!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("????????????! -> " + body.getString("errmsg"));
        }
        return body.getString("join_qrcode");
    }

    @Override
    public List<MemberResp> memberListByDept() {
        String simpleListUrl = qywxConfig.getMember().getSimpleListUrl();
        String token = getOtherToken();
        simpleListUrl = simpleListUrl.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(simpleListUrl, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("????????????!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("????????????! -> " + body.getString("errmsg"));
        }
        JSONArray jsonArray = body.getJSONArray("userlist");
        List<MemberResp> memberResps = JSONArray.parseArray(jsonArray.toJSONString(), MemberResp.class);
        return memberResps;
    }

    @Override
    public List<MemberResp> memberListByTag(Integer tagId) {
        String url = qywxConfig.getMember().getTagMemberList();
        String token = getOtherToken();
        url = url.replace("ACCESS_TOKEN", token).replace("TAGID", String.valueOf(tagId));
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("????????????!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("????????????! -> " + body.getString("errmsg"));
        }
        JSONArray jsonArray = body.getJSONArray("userlist");
        List<MemberResp> memberResps = JSONArray.parseArray(jsonArray.toJSONString(), MemberResp.class);
        return memberResps;
    }

    @Override
    public void addTagMembers(TagMembersParam param) {
        String url = qywxConfig.getTag().getAddTagUserUrl();
        String token = getOtherToken();
        url = url.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(url, param, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("????????????!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("????????????! -> " + body.getString("errmsg"));
        }
    }

    @Override
    public void delTagMembers(TagMembersParam param) {
        String url = qywxConfig.getTag().getDelTagUserUrl();
        String token = getOtherToken();
        url = url.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(url, param, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("????????????!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("????????????! -> " + body.getString("errmsg"));
        }
    }
}
