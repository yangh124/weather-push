package com.yh.weatherpush.manager.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.config.property.QywxConfigProperties;
import com.yh.weatherpush.dto.qywx.MemberResp;
import com.yh.weatherpush.dto.qywx.MsgResp;
import com.yh.weatherpush.dto.qywx.QywxTag;
import com.yh.weatherpush.dto.qywx.TabResp;
import com.yh.weatherpush.dto.qywx.Text;
import com.yh.weatherpush.dto.qywx.TextMsgReq;
import com.yh.weatherpush.dto.qywx.TokenResp;
import com.yh.weatherpush.dto.tag.TagMembersParam;
import com.yh.weatherpush.exception.ApiException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QywxManager {

    @Autowired
    private QywxConfigProperties qywxConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedissonClient redissonClient;


    /**
     * 发送文本消息
     *
     * @param token     token
     * @param tagMsgMap 标签id -> 消息内容
     */
    public void pushWeatherMsg(String token, Map<Integer, String> tagMsgMap) {
        for (Integer tagid : tagMsgMap.keySet()) {
            String msg = tagMsgMap.get(tagid);
            String sendUrl = qywxConfig.getSendUrl();
            sendUrl = sendUrl.replace("ACCESS_TOKEN", token);
            TextMsgReq textMsgReq = TextMsgReq.builder().agentid(qywxConfig.getAgentid()).totag(tagid).msgtype("text")
                    .text(new Text(msg)).build();
            restTemplate.postForEntity(sendUrl, textMsgReq, MsgResp.class);
        }
    }

    /**
     * 创建标签
     *
     * @param tagId   标签id
     * @param tagName 标签名称
     * @return 标签id
     */
    public Integer createTag(Integer tagId, String tagName) {
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
            throw new ApiException("创建标签失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("创建标签失败! -> " + body.getString("errmsg"));
        }
        tagId = body.getInteger("tagid");
        return tagId;
    }

    /**
     * 删除标签
     *
     * @param tagId 标签id
     */
    public void deleteTag(Integer tagId) {
        String deleteUrl = qywxConfig.getTag().getDeleteUrl();
        String token = getOtherToken();
        deleteUrl = deleteUrl.replace("ACCESS_TOKEN", token).replace("TAG_ID", String.valueOf(tagId));
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(deleteUrl, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("删除标签失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("删除标签失败! -> " + body.getString("errmsg"));
        }
    }


    /**
     * 获取所有标签
     *
     * @return 标签
     */
    public List<QywxTag> getAllTags() {
        String labelUrl = qywxConfig.getTag().getListUrl();
        String token = getOtherToken();
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
     * </p>
     * 2.由于企业微信每个应用的access_token是彼此独立的，所以进行缓存时需要区分应用来进行存储。
     * </p>
     * 3.access_token至少保留512字节的存储空间。
     * </p>
     * 4.企业微信可能会出于运营需要，提前使access_token失效，开发者应实现access_token失效时重新获取的逻辑。
     * <p>
     * 推送消息token
     *
     * @return access_token
     */
    public String getPushToken() {
        RBucket<String> accessToken = redissonClient.getBucket("push_access_token");
        boolean exists = accessToken.isExists();
        if (exists) {
            return accessToken.get();
        }
        String tokenUrl = qywxConfig.getTokenUrl();
        tokenUrl = tokenUrl.replace("SECRET", qywxConfig.getPushSecret());
        String access_token = getToken(tokenUrl);
        accessToken.set(access_token, 1, TimeUnit.HOURS);
        return access_token;
    }

    /**
     * 通讯录管理token
     *
     * @return token
     */
    public String getOtherToken() {
        RBucket<String> accessToken = redissonClient.getBucket("other_access_token");
        boolean exists = accessToken.isExists();
        if (exists) {
            return accessToken.get();
        }
        String tokenUrl = qywxConfig.getTokenUrl();
        tokenUrl = tokenUrl.replace("SECRET", qywxConfig.getOtherSecret());
        String access_token = getToken(tokenUrl);
        accessToken.set(access_token, 1, TimeUnit.HOURS);
        return access_token;
    }

    /**
     * 获取token
     *
     * @param tokenUrl 请求地址
     * @return token
     */
    private String getToken(String tokenUrl) {
        ResponseEntity<TokenResp> response = restTemplate.getForEntity(tokenUrl, TokenResp.class);
        TokenResp body = response.getBody();
        if (null == body) {
            throw new ApiException("获取token失败!");
        }
        String errcode = body.getErrcode();
        if (!"0".equals(errcode)) {
            throw new ApiException("获取token失败! -> " + body);
        }
        return body.getAccess_token();
    }

    /**
     * 获取二维码
     *
     * @return 二维码
     */
    public String getJoinQrCode() {
        String joinQrcodeUrl = qywxConfig.getMember().getJoinQrcodeUrl();
        String token = getOtherToken();
        joinQrcodeUrl = joinQrcodeUrl.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(joinQrcodeUrl, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("获取失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("获取失败! -> " + body.getString("errmsg"));
        }
        return body.getString("join_qrcode");
    }

    /**
     * 获取部门成员
     *
     * @return 部门成员
     */
    public List<MemberResp> memberListByDept() {
        String simpleListUrl = qywxConfig.getMember().getSimpleListUrl();
        String token = getOtherToken();
        simpleListUrl = simpleListUrl.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(simpleListUrl, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("获取失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("获取失败! -> " + body.getString("errmsg"));
        }
        JSONArray jsonArray = body.getJSONArray("userlist");
        List<MemberResp> memberResps = JSONArray.parseArray(jsonArray.toJSONString(), MemberResp.class);
        return memberResps;
    }

    /**
     * 获取标签成员
     *
     * @param tagId 标签id
     * @return 标签成员
     */
    public List<MemberResp> memberListByTag(Integer tagId) {
        String url = qywxConfig.getMember().getTagMemberList();
        String token = getOtherToken();
        url = url.replace("ACCESS_TOKEN", token).replace("TAGID", String.valueOf(tagId));
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("获取失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("获取失败! -> " + body.getString("errmsg"));
        }
        JSONArray jsonArray = body.getJSONArray("userlist");
        List<MemberResp> memberResps = JSONArray.parseArray(jsonArray.toJSONString(), MemberResp.class);
        return memberResps;
    }

    /**
     * 添加标签成员
     *
     * @param param 参数
     */
    public void addTagMembers(TagMembersParam param) {
        String url = qywxConfig.getTag().getAddTagUserUrl();
        String token = getOtherToken();
        url = url.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(url, param, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("添加失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("添加失败! -> " + body.getString("errmsg"));
        }
    }

    /**
     * 删除标签成员
     *
     * @param param 参数
     */
    public void delTagMembers(TagMembersParam param) {
        String url = qywxConfig.getTag().getDelTagUserUrl();
        String token = getOtherToken();
        url = url.replace("ACCESS_TOKEN", token);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(url, param, JSONObject.class);
        JSONObject body = response.getBody();
        if (null == body) {
            throw new ApiException("删除失败!");
        }
        Integer errcode = body.getInteger("errcode");
        if (!errcode.equals(0)) {
            throw new ApiException("删除失败! -> " + body.getString("errmsg"));
        }
    }
}
