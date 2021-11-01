package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.config.QywxConfig;
import com.yh.weatherpush.dto.qxwx.*;
import com.yh.weatherpush.service.GetWeatherService;
import com.yh.weatherpush.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author : yh
 * @date : 2021/10/31 13:14
 */
@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private QywxConfig qywxConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GetWeatherService getWeatherService;


    @Override
    public void pushMsg(String token, List<Tag> tags) {
        Map<Integer, String> locations = getWeatherService.getLocations(tags);
        Map<Integer, List<Tag>> tagMap = tags.stream().collect(Collectors.groupingBy(Tag::getTagid));
        Map<Integer, String> weatherMap = getWeatherService.getWeather(locations, tagMap);
        for (Integer tagid : weatherMap.keySet()) {
            String msg = weatherMap.get(tagid);
            String sendUrl = qywxConfig.getSendUrl();
            sendUrl = sendUrl.replace("ACCESS_TOKEN", token);
            TextMsgReq textMsgReq = TextMsgReq.builder()
                    .agentid(qywxConfig.getAgentid())
                    .totag(tagid)
                    .msgtype("text")
                    .text(new Text(msg)).build();
            restTemplate.postForEntity(sendUrl, textMsgReq, MsgResp.class);
        }
    }

    @Override
    public List<Tag> getTags(String token) {
        String labelUrl = qywxConfig.getLabelUrl();
        labelUrl = labelUrl.replace("ACCESS_TOKEN", token);
        ResponseEntity<TabResp> response = restTemplate.getForEntity(labelUrl, TabResp.class);
        TabResp body = response.getBody();
        if (null == body) {
            throw new RuntimeException("获取标签失败!");
        }
        Integer errcode = body.getErrcode();
        if (!errcode.equals(0)) {
            throw new RuntimeException("获取token失败! -> " + body);
        }
        return body.getTaglist();
    }

    /**
     * 1.access_token的有效期通过返回的expires_in来传达，正常情况下为7200秒（2小时），有效期内重复获取返回相同结果，过期后获取会返回新的access_token。
     * 2.由于企业微信每个应用的access_token是彼此独立的，所以进行缓存时需要区分应用来进行存储。
     * 3.access_token至少保留512字节的存储空间。
     * 4.企业微信可能会出于运营需要，提前使access_token失效，开发者应实现access_token失效时重新获取的逻辑。
     *
     * @return access_token
     */
    @Override
    public String getToken() {
        ResponseEntity<TokenResp> response = restTemplate.getForEntity(qywxConfig.getTokenUrl(), TokenResp.class);
        TokenResp body = response.getBody();
        if (null == body) {
            throw new RuntimeException("获取token失败!");
        }
        String errcode = body.getErrcode();
        if (!"0".equals(errcode)) {
            throw new RuntimeException("获取token失败! -> " + body);
        }
        return body.getAccess_token();
    }
}
