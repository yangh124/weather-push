package com.yh.weatherpush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.dto.qywx.MemberDTO;
import com.yh.weatherpush.entity.Member;
import com.yh.weatherpush.manager.QywxManager;
import com.yh.weatherpush.mapper.MemberMapper;
import com.yh.weatherpush.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private QywxManager qywxManager;

    @Override
    public List<MemberDTO> memberList(Integer id) {
        if (null == id) {
            return null;
        }
        return qywxManager.memberListByTag(id);
    }
}
