package com.yh.weatherpush.infrastructure.manager.http;

import com.yh.weatherpush.interfaces.dto.qywx.QywxRespDTO;
import com.yh.weatherpush.interfaces.dto.qywx.request.TagCreateReqDTO;
import com.yh.weatherpush.interfaces.dto.qywx.request.TagUsersReqDTO;
import com.yh.weatherpush.interfaces.dto.qywx.request.TextMsgReqDTO;
import com.yh.weatherpush.interfaces.dto.qywx.response.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface QywxApi {

    /**
     * 获取access_token
     *
     * @param corpId     企业ID
     * @param corpSecret 应用的凭证密钥，注意应用需要是启用状态
     * @return access_token
     */
    @GetExchange("/gettoken")
    GetTokenRespDTO getToken(@RequestParam("corpid") String corpId, @RequestParam("corpsecret") String corpSecret);

    /**
     * 发送文本消息
     *
     * @param accessToken accessToken
     * @param reqDTO      标签id、消息内容
     */
    @PostExchange("/message/send")
    MessageSendRespDTO messageSend(@RequestParam("access_token") String accessToken, @RequestBody TextMsgReqDTO reqDTO);

    /**
     * 创建标签
     *
     * @param accessToken accessToken
     * @param reqDTO      标签id 标签名称
     * @return 标签id
     */
    @PostExchange("/tag/create")
    TagCreateRespDTO createTag(@RequestParam("access_token") String accessToken, @RequestBody TagCreateReqDTO reqDTO);

    /**
     * 删除标签
     *
     * @param accessToken accessToken
     * @param tagId       标签id
     */
    @PostExchange("/tag/delete")
    QywxRespDTO deleteTag(@RequestParam("access_token") String accessToken, @RequestParam Integer tagId);

    /**
     * 获取标签列表
     *
     * @param accessToken accessToken
     */
    @GetExchange("/tag/list")
    TagListRespDTO tagList(@RequestParam("access_token") String accessToken);

    /**
     * 获取加入企业二维码
     *
     * @param accessToken accessToken
     * @param sizeType    qrcode尺寸类型，1: 171 x 171; 2: 399 x 399; 3: 741 x 741; 4: 2052 x 2052
     * @return
     */
    @GetExchange("/corp/get_join_qrcode")
    GetJoinQrCodeRespDTO getJoinQrCode(@RequestParam("access_token") String accessToken,
                                       @RequestParam("size_type") String sizeType);

    /**
     * 获取部门成员
     *
     * @param accessToken accessToken
     * @return
     */
    @PostExchange("/user/list_id")
    UserSimpleListRespDTO userSimpList(@RequestParam("access_token") String accessToken);

    /**
     * 获取标签成员
     *
     * @param accessToken accessToken
     * @param tagId       标签ID
     * @return 标签成员
     */
    @GetExchange("/tag/get")
    TagGetRespDTO tagGet(@RequestParam("access_token") String accessToken, @RequestParam("tagid") Long tagId);

    /**
     * 增加标签成员
     *
     * @param accessToken accessToken
     * @param reqDTO      参数
     */
    @PostExchange("/tag/addtagusers")
    QywxRespDTO addTagUsers(@RequestParam("access_token") String accessToken, @RequestBody TagUsersReqDTO reqDTO);

    /**
     * 删除标签成员
     *
     * @param accessToken accessToken
     * @param reqDTO      参数
     */
    @PostExchange("/tag/deltagusers")
    QywxRespDTO delTagUsers(@RequestParam("access_token") String accessToken, @RequestBody TagUsersReqDTO reqDTO);


    /**
     * 获取应用的jsapi_ticket
     *
     * @param accessToken accessToken
     * @param type        类型 agent_config
     */
    @GetExchange("/ticket/get")
    JsApiTicketRespDTO getAgentConfig(@RequestParam("access_token") String accessToken,
                                      @RequestParam("type") String type);
}
