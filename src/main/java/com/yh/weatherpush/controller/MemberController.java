package com.yh.weatherpush.controller;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.qywx.MemberDTO;
import com.yh.weatherpush.dto.qywx.QywxAgentConfigDTO;
import com.yh.weatherpush.manager.QywxManager;
import com.yh.weatherpush.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private QywxManager qywxManager;
    @Autowired
    private MemberService memberService;

    @ApiOperation("获取加入企业二维码")
    @GetMapping("/qr_code")
    public Result<String> getJoinQrCode() {
        String joinQrCode = qywxManager.getJoinQrCode();
        return Result.success(joinQrCode);
    }

    @ApiOperation("获取地区成员")
    @GetMapping()
    public Result<List<MemberDTO>> userList(@RequestParam Integer id) {
        List<MemberDTO> memberResps = memberService.userList(id);
        return Result.success(memberResps);
    }

    @ApiOperation("获取所有成员")
    @GetMapping("/ids")
    public Result<List<String>> userIdList() {
        List<String> ids = memberService.userIdList();
        return Result.success(ids);
    }

    @ApiOperation("获取企业微信配置（用于通讯录展示）")
    @GetMapping("/agentConfig")
    public Result<QywxAgentConfigDTO> getQywxAgentConfig() {
        QywxAgentConfigDTO dto = qywxManager.getQywxAgentConfig();
        return Result.success(dto);
    }
}
