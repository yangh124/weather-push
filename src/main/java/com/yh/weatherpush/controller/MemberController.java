package com.yh.weatherpush.controller;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.qywx.MemberResp;
import com.yh.weatherpush.service.QywxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yh
 * @date : 2022/3/12 11:19
 */
@Api(tags = "成员管理")
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private QywxService qywxService;

    @ApiOperation("获取加入企业二维码")
    @GetMapping("/qr_code")
    public Result<String> getJoinQrCode() {
        String joinQrCode = qywxService.getJoinQrCode();
        return Result.success(joinQrCode);
    }

    @ApiOperation("获取部门成员（所有成员）")
    @GetMapping()
    public Result<List<MemberResp>> memberList() {
        List<MemberResp> memberResps = qywxService.memberListByDept();
        return Result.success(memberResps);
    }

}
