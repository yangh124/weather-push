package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.config.HfConfig;
import com.yh.weatherpush.dto.hfweather.HfCityResp;
import com.yh.weatherpush.dto.hfweather.HfWeatherResp;
import com.yh.weatherpush.dto.hfweather.WeatherNow;
import com.yh.weatherpush.dto.qxwx.Tag;
import com.yh.weatherpush.service.GetWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yh
 * @date : 2021/10/31 14:29
 */
@Service
public class GetWeatherServiceImpl implements GetWeatherService {

    @Autowired
    private HfConfig hfConfig;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<Integer, String> getWeather(Map<Integer, String> map, Map<Integer, List<Tag>> tagMap) {
        Map<Integer, String> res = new HashMap<>();
        for (Integer tagid : map.keySet()) {
            String code = map.get(tagid);
            String getUrl = hfConfig.getGetUrl();
            getUrl = getUrl.replace("code", code);
            ResponseEntity<HfWeatherResp> response = restTemplate.getForEntity(getUrl, HfWeatherResp.class);
            HfWeatherResp body = response.getBody();
            if (null == body) {
                throw new RuntimeException("获取天气异常！");
            }
            WeatherNow now = body.getNow();
            LocalDate date = LocalDate.now();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            int value = dayOfWeek.getValue();
            String[] valueArr = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
            String dateString = date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
            Tag tag = tagMap.get(tagid).get(0);
            StringBuilder builder = new StringBuilder("【天气预报】【" + tag.getTagname() + "】\n");
            builder.append(dateString).append("  ").append(valueArr[value - 1]).append("\n\n");
            builder.append(now.getText()).append("\n");
            builder.append("气温：").append(now.getTemp()).append("度\n");
            builder.append("体感温度：").append(now.getFeelsLike()).append("度\n");
            builder.append("风力等级：").append(now.getWindScale()).append("级\n");
            builder.append("相对湿度：").append(now.getHumidity()).append("%\n");
            builder.append("大气压强：").append(now.getPressure()).append("百帕\n");
            String pluginUrl = hfConfig.getPluginUrl();
            builder.append("详细天气请查看 -> ").append("<a href=\"").append(pluginUrl).append("\">和风天气</a>");
            res.put(tagid, builder.toString());
        }

        return res;
    }

    @Override
    public Map<Integer, String> getLocations(List<Tag> cityList) {
        Map<Integer, String> map = new HashMap<>();
        for (Tag tag : cityList) {
            String cityUrl = hfConfig.getCityUrl();
            cityUrl = cityUrl.replace("cityName", tag.getTagname());
            ResponseEntity<HfCityResp> response = restTemplate.getForEntity(cityUrl, HfCityResp.class);
            HfCityResp body = response.getBody();
            if (null == body) {
                throw new RuntimeException("获取标签失败!");
            }
            String code = body.getCode();
            if (!"200".equals(code)) {
                throw new RuntimeException("获取token失败! -> " + body);
            }
            String id = body.getLocation().get(0).getId();
            map.put(tag.getTagid(), id);
        }
        return map;
    }
}
