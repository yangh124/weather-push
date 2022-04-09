package com.yh.weatherpush.service;

import com.yh.weatherpush.dto.qywx.MemberResp;
import com.yh.weatherpush.dto.tag.TagMembersParam;
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
     * 删除tag
     *
     * @param tagId
     */
    void deleteTag(Integer tagId);

    /**
     * 获取用户标签
     * 
     */
    List<Tag> getAllTags();

    /**
     * 获取天气推送token
     * 
     * @return
     */
    String getPushToken();

    /**
     * 获取成员管理token
     *
     * @return
     */
    String getOtherToken();

    /**
     * 获取邀请二维码
     * 
     * @return
     */
    String getJoinQrCode();

    /**
     * 获取部门成员
     * 
     * @return
     */
    List<MemberResp> memberListByDept();

    /**
     * 获取标签成员
     * 
     * @return
     */
    List<MemberResp> memberListByTag(Integer tagId);

    /**
     * 添加标签成员
     * 
     * @param param
     */
    void addTagMembers(TagMembersParam param);

    /**
     * 删除标签成员
     * 
     * @param param
     */
    void delTagMembers(TagMembersParam param);
}
