package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.dto.qywx.MemberResp;
import com.yh.weatherpush.entity.Member;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.mapper.MemberMapper;
import com.yh.weatherpush.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yh.weatherpush.service.QywxService;
import com.yh.weatherpush.service.TagService;
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
    private QywxService qywxService;
    @Autowired
    private TagService tagService;

    @Override
    public List<MemberResp> memberList(Long id) {
        if (null == id) {
            return qywxService.memberListByDept();
        }
        Tag tag = tagService.getById(id);
        if (null != tag) {
            return qywxService.memberListByTag(tag.getTagId());
        }
        return null;
    }
}
