package com.yh.weatherpush;

import com.alibaba.fastjson2.JSONObject;
import com.yh.weatherpush.config.property.QywxConfigProperties;
import com.yh.weatherpush.dto.qywx.response.GetTokenRespDTO;
import com.yh.weatherpush.manager.http.QywxApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("dev")
class WeatherPushApplicationTests {

    @Autowired
    private QywxApi qywxApi;
    @Autowired
    private QywxConfigProperties qywxConfig;

    @Test
    void contextLoads() {
        // 24小时天气
        GetTokenRespDTO token = qywxApi.getToken(qywxConfig.getCorpid(), qywxConfig.getOtherSecret());
        System.out.println(JSONObject.toJSONString(token));
    }
}
