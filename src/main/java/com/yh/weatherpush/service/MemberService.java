package com.yh.weatherpush.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yh.weatherpush.dto.qywx.MemberDTO;
import com.yh.weatherpush.entity.Member;

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


    List<MemberDTO> userList(Integer id);

    List<String> userIdList();
}
