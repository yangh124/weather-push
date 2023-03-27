package com.yh.weatherpush.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.dto.qywx.MemberResp;
import com.yh.weatherpush.entity.Member;
import com.yh.weatherpush.manager.api.QywxManager;
import com.yh.weatherpush.mapper.MemberMapper;
import com.yh.weatherpush.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private QywxManager qywxManager;

    @Override
    public List<MemberResp> memberList(Integer id) {
        if (null == id) {
            return qywxManager.memberListByDept();
        }
        return qywxManager.memberListByTag(id);
    }
}
