package com.yh.weatherpush.controller;

import com.yh.weatherpush.dto.Result;
import com.yh.weatherpush.dto.qywx.MemberDTO;
import com.yh.weatherpush.dto.qywx.QywxAgentConfigDTO;
import com.yh.weatherpush.manager.QywxManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yh
 * @date : 2022/3/12 11:19
 */
@AllArgsConstructor
@Tag(name = "成员管理")
@RestController
@RequestMapping("/members")
public class MemberController {

    private final QywxManager qywxManager;

    @Operation(summary = "获取加入企业二维码")
    @GetMapping("/qr_code")
    public Result<String> getJoinQrCode() {
        String joinQrCode = qywxManager.getJoinQrCode();
        return Result.success(joinQrCode);
    }

    @Operation(summary = "获取地区成员")
    @GetMapping()
    public Result<List<MemberDTO>> userList(@RequestParam Long id) {
        List<MemberDTO> memberResps = qywxManager.userListByTag(id);
        return Result.success(memberResps);
    }

    @Operation(summary = "获取所有成员")
    @GetMapping("/ids")
    public Result<List<String>> userIdList() {
        List<String> ids = qywxManager.userIdList();
        return Result.success(ids);
    }

    @Operation(summary = "获取企业微信配置（用于通讯录展示）")
    @GetMapping("/agentConfig")
    public Result<QywxAgentConfigDTO> getQywxAgentConfig() {
        QywxAgentConfigDTO dto = qywxManager.getQywxAgentConfig();
        return Result.success(dto);
    }
}
