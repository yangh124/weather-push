package com.yh.weatherpush.service;

import com.yh.weatherpush.dto.qywx.MemberResp;
import com.yh.weatherpush.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 成员表 服务类
 * </p>
 *
 * @author yh
 * @since 2022-03-14
 */
public interface MemberService extends IService<Member> {


    List<MemberResp> memberList(Long id);
}
