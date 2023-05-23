package com.yh.weatherpush.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.yh.weatherpush.config.property.QywxConfigProperties;
import com.yh.weatherpush.dto.qywx.*;
import com.yh.weatherpush.dto.qywx.request.TagCreateReqDTO;
import com.yh.weatherpush.dto.qywx.request.TagUsersReqDTO;
import com.yh.weatherpush.dto.qywx.request.TextMsgReqDTO;
import com.yh.weatherpush.dto.qywx.response.*;
import com.yh.weatherpush.dto.tag.TagMembersParam;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.manager.api.QywxApiClient;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class QywxManager {

    @Autowired
    private QywxConfigProperties qywxConfig;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private QywxApiClient qywxApiClient;


    /**
     * 发送文本消息
     *
     * @param tagMsgMap 标签id -> 消息内容
     */
    public void pushWeatherMsg(Map<Integer, String> tagMsgMap) {
        String pushToken = getPushToken();
        String agentid = qywxConfig.getAgentid();
        for (Integer tagId : tagMsgMap.keySet()) {
            String msg = tagMsgMap.get(tagId);
            TextDTO text = new TextDTO(msg);
            TextMsgReqDTO reqDTO = TextMsgReqDTO.builder().msgType("text").toTag(tagId).agentId(agentid).text(text).build();
            qywxApiClient.messageSend(pushToken, reqDTO);
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
        String token = getOtherToken();
        TagCreateReqDTO reqDTO = new TagCreateReqDTO();
        reqDTO.setTagName(tagName);
        if (null != tagId) {
            reqDTO.setTagId(tagId);
        }
        TagCreateRespDTO respDTO = qywxApiClient.createTag(token, reqDTO);
        if (null == respDTO) {
            throw new ApiException("创建标签失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("创建标签失败! -> " + respDTO.getErrMsg());
        }
        tagId = respDTO.getTagId();
        return tagId;
    }

    /**
     * 删除标签
     *
     * @param tagId 标签id
     */
    public void deleteTag(Integer tagId) {
        String token = getOtherToken();
        QywxBaseRespDTO respDTO = qywxApiClient.deleteTag(token, tagId);
        if (null == respDTO) {
            throw new ApiException("删除标签失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("删除标签失败! -> " + respDTO.getErrMsg());
        }
    }


    /**
     * 获取所有标签
     *
     * @return 标签
     */
    public List<QywxTagDTO> getAllTags() {
        String token = getOtherToken();
        TagListRespDTO respDTO = qywxApiClient.tagList(token);
        if (null == respDTO) {
            throw new ApiException("获取标签失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("获取token失败! -> " + respDTO.getErrMsg());
        }
        return respDTO.getTagList();
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
    private String getPushToken() {
        String token;
        RLock lock = redissonClient.getLock("push_access_token:lock");
        lock.lock();
        try {
            RBucket<String> accessToken = redissonClient.getBucket("push_access_token");
            boolean exists = accessToken.isExists();
            if (exists) {
                token = accessToken.get();
            } else {
                GetTokenRespDTO respDTO = qywxApiClient.getToken(qywxConfig.getCorpid(), qywxConfig.getPushSecret());
                token = getTokenResult(accessToken, respDTO);
            }
        } finally {
            lock.unlock();
        }
        return token;
    }

    /**
     * 通讯录管理token
     *
     * @return token
     */
    private String getOtherToken() {
        String token;
        RLock lock = redissonClient.getLock("other_access_token:lock");
        lock.lock();
        try {
            RBucket<String> accessToken = redissonClient.getBucket("other_access_token");
            boolean exists = accessToken.isExists();
            if (exists) {
                token = accessToken.get();
            } else {
                GetTokenRespDTO respDTO = qywxApiClient.getToken(qywxConfig.getCorpid(), qywxConfig.getOtherSecret());
                token = getTokenResult(accessToken, respDTO);
            }
        } finally {
            lock.unlock();
        }
        return token;
    }

    private String getTokenResult(RBucket<String> accessToken, GetTokenRespDTO respDTO) {
        String token;
        if (null == respDTO) {
            throw new ApiException("获取token失败!");
        }
        if (0 != respDTO.getErrCode()) {
            throw new ApiException("获取token失败! -> " + respDTO.getErrMsg());
        }
        token = respDTO.getAccessToken();
        accessToken.set(token, 1, TimeUnit.HOURS);
        return token;
    }

    /**
     * 获取应用的jsapi_ticket
     *
     * @param accessToken token
     * @return
     */
    private String getJsApiTicket(String accessToken) {
        String ticket;
        RLock lock = redissonClient.getLock("jsapi_ticket:lock");
        lock.lock();
        try {
            RBucket<String> jsapiTicket = redissonClient.getBucket("jsapi_ticket");
            boolean exists = jsapiTicket.isExists();
            if (exists) {
                ticket = jsapiTicket.get();
            } else {
                JsApiTicketRespDTO respDTO = qywxApiClient.getAgentConfig(accessToken, "agent_config");
                if (null == respDTO) {
                    throw new ApiException("获取jsapi_ticket失败!");
                }
                if (0 != respDTO.getErrCode()) {
                    throw new ApiException("获取jsapi_ticket失败! -> " + respDTO.getErrMsg());
                }
                ticket = respDTO.getTicket();
                Long expiresIn = respDTO.getExpiresIn();
                jsapiTicket.set(ticket, expiresIn, TimeUnit.SECONDS);
            }
        } finally {
            lock.unlock();
        }
        return ticket;
    }

    /**
     * 获取二维码
     *
     * @return 二维码
     */
    public String getJoinQrCode() {
        String token = getOtherToken();
        GetJoinQrCodeRespDTO respDTO = qywxApiClient.getJoinQrCode(token, null);
        if (null == respDTO) {
            throw new ApiException("获取失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("获取失败! -> " + respDTO.getErrMsg());
        }
        return respDTO.getJoinQrcode();
    }

    public List<String> userIdList() {
        String token = getOtherToken();
        UserSimpleListRespDTO respDTO = qywxApiClient.userSimpList(token);
        if (null == respDTO) {
            throw new ApiException("获取失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("获取失败! -> " + respDTO.getErrMsg());
        }
        List<DeptUserDTO> deptUserList = respDTO.getDeptUserList();
        if (CollUtil.isNotEmpty(deptUserList)) {
            return deptUserList.stream().map(DeptUserDTO::getUserId).collect(Collectors.toList());
        } else {
            return CollUtil.newArrayList();
        }
    }

    /**
     * 获取标签成员
     *
     * @param tagId 标签id
     * @return 标签成员
     */
    public List<MemberDTO> userListByTag(Integer tagId) {
        String token = getOtherToken();
        TagGetRespDTO respDTO = qywxApiClient.tagGet(token, tagId);
        if (null == respDTO) {
            throw new ApiException("获取失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("获取失败! -> " + respDTO.getErrMsg());
        }
        return respDTO.getUserList();
    }

    /**
     * 添加标签成员
     *
     * @param param 参数
     */
    public void addTagMembers(TagMembersParam param) {
        String token = getOtherToken();
        TagUsersReqDTO tagUsersReqDTO = new TagUsersReqDTO();
        BeanUtil.copyProperties(param, tagUsersReqDTO);
        QywxBaseRespDTO respDTO = qywxApiClient.addTagUsers(token, tagUsersReqDTO);
        if (null == respDTO) {
            throw new ApiException("添加失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("添加失败! -> " + respDTO.getErrMsg());
        }
    }

    /**
     * 删除标签成员
     *
     * @param param 参数
     */
    public void delTagMembers(TagMembersParam param) {
        String token = getOtherToken();
        TagUsersReqDTO reqDTO = new TagUsersReqDTO();
        BeanUtil.copyProperties(param, reqDTO);
        QywxBaseRespDTO respDTO = qywxApiClient.delTagUsers(token, reqDTO);
        if (null == respDTO) {
            throw new ApiException("删除失败!");
        }
        Integer errCode = respDTO.getErrCode();
        if (!errCode.equals(0)) {
            throw new ApiException("删除失败! -> " + respDTO.getErrMsg());
        }
    }

    public QywxAgentConfigDTO getQywxAgentConfig() {
        String corpid = qywxConfig.getCorpid();
        String agentid = qywxConfig.getAgentid();
        long timestamp = System.currentTimeMillis();
        String token = getPushToken();
        String jsApiTicket = getJsApiTicket(token);
        String memberUrl = qywxConfig.getMemberUrl();
        String uuid = IdUtil.fastSimpleUUID();
        String str = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + uuid + "&timestamp=" + timestamp + "&url=" + memberUrl;
        Digester digester = new Digester(DigestAlgorithm.SHA1);
        String signature = digester.digestHex(str);
        QywxAgentConfigDTO dto = new QywxAgentConfigDTO();
        dto.setCorpid(corpid);
        dto.setAgentid(agentid);
        dto.setTimestamp(timestamp);
        dto.setNonceStr(uuid);
        dto.setSignature(signature);
        return dto;
    }
}
