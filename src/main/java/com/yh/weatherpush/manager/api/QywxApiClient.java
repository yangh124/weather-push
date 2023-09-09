package com.yh.weatherpush.manager.api;

import com.yh.weatherpush.dto.qywx.QywxBaseRespDTO;
import com.yh.weatherpush.dto.qywx.request.TagCreateReqDTO;
import com.yh.weatherpush.dto.qywx.request.TagUsersReqDTO;
import com.yh.weatherpush.dto.qywx.request.TextMsgReqDTO;
import com.yh.weatherpush.dto.qywx.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yangh
 */
@FeignClient(name = "qywx-api", url = "${qywx.base-url}")
public interface QywxApiClient {


    /**
     * 获取access_token
     *
     * @param corpId     企业ID
     * @param corpSecret 应用的凭证密钥，注意应用需要是启用状态
     * @return access_token
     */
    @GetMapping("/gettoken")
    GetTokenRespDTO getToken(@RequestParam("corpid") String corpId, @RequestParam("corpsecret") String corpSecret);

    /**
     * 发送文本消息
     *
     * @param accessToken accessToken
     * @param reqDTO      标签id、消息内容
     */
    @PostMapping("/message/send")
    MessageSendRespDTO messageSend(@RequestParam("access_token") String accessToken, @RequestBody TextMsgReqDTO reqDTO);

    /**
     * 创建标签
     *
     * @param accessToken accessToken
     * @param reqDTO      标签id 标签名称
     * @return 标签id
     */
    @PostMapping("/tag/create")
    TagCreateRespDTO createTag(@RequestParam("access_token") String accessToken, @RequestBody TagCreateReqDTO reqDTO);

    /**
     * 删除标签
     *
     * @param accessToken accessToken
     * @param tagId       标签id
     */
    @GetMapping("/tag/delete")
    QywxBaseRespDTO deleteTag(@RequestParam("access_token") String accessToken, @RequestParam Integer tagId);

    /**
     * 获取标签列表
     *
     * @param accessToken accessToken
     */
    @GetMapping("/tag/list")
    TagListRespDTO tagList(@RequestParam("access_token") String accessToken);

    /**
     * 获取加入企业二维码
     *
     * @param accessToken accessToken
     * @param sizeType    qrcode尺寸类型，1: 171 x 171; 2: 399 x 399; 3: 741 x 741; 4: 2052 x 2052
     * @return
     */
    @GetMapping("/corp/get_join_qrcode")
    GetJoinQrCodeRespDTO getJoinQrCode(@RequestParam("access_token") String accessToken, @RequestParam("size_type") String sizeType);

    /**
     * 获取部门成员
     *
     * @param accessToken accessToken
     * @return
     */
    @PostMapping("/user/list_id")
    UserSimpleListRespDTO userSimpList(@RequestParam("access_token") String accessToken);

    /**
     * 获取标签成员
     *
     * @param accessToken accessToken
     * @param tagId       标签ID
     * @return 标签成员
     */
    @GetMapping("/tag/get")
    TagGetRespDTO tagGet(@RequestParam("access_token") String accessToken, @RequestParam("tagid") Long tagId);

    /**
     * 增加标签成员
     *
     * @param accessToken accessToken
     * @param reqDTO      参数
     */
    @PostMapping("/tag/addtagusers")
    QywxBaseRespDTO addTagUsers(@RequestParam("access_token") String accessToken, @RequestBody TagUsersReqDTO reqDTO);

    /**
     * 删除标签成员
     *
     * @param accessToken accessToken
     * @param reqDTO      参数
     */
    @PostMapping("/tag/deltagusers")
    QywxBaseRespDTO delTagUsers(@RequestParam("access_token") String accessToken, @RequestBody TagUsersReqDTO reqDTO);


    /**
     * 获取应用的jsapi_ticket
     *
     * @param accessToken accessToken
     * @param type        类型 agent_config
     */
    @GetMapping("/ticket/get")
    JsApiTicketRespDTO getAgentConfig(@RequestParam("access_token") String accessToken, @RequestParam("type") String type);
}
