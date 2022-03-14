package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.entity.Member;
import com.yh.weatherpush.mapper.MemberMapper;
import com.yh.weatherpush.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 成员表 服务实现类
 * </p>
 *
 * @author yh
 * @since 2022-03-14
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

}
