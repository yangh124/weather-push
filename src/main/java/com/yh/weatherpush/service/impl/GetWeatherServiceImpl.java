package com.yh.weatherpush.service.impl;

import com.yh.weatherpush.config.HfConfig;
import com.yh.weatherpush.dto.TagLocation;
import com.yh.weatherpush.dto.hfweather.*;
import com.yh.weatherpush.dto.qxwx.Tag;
import com.yh.weatherpush.service.GetWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    public Map<Integer, String> getWeather(List<TagLocation> tags) {
        Map<Integer, String> res = new HashMap<>();
        for (TagLocation tagLocation : tags) {
            Integer tagid = tagLocation.getTagid();
            String tagname = tagLocation.getTagname();
            String code = tagLocation.getCode();
            //实时天气
            String weatherUrl = hfConfig.getGetUrl();
            weatherUrl = weatherUrl.replace("code", code);
            ResponseEntity<HfWeatherResp> weatherResponse = restTemplate.getForEntity(weatherUrl, HfWeatherResp.class);
            HfWeatherResp weatherBody = weatherResponse.getBody();
            if (null == weatherBody) {
                throw new RuntimeException("获取天气异常！");
            }
            //天气指数
            String weatherIndexUrl = hfConfig.getIndexUrl();
            weatherIndexUrl = weatherIndexUrl.replace("code", code);
            ResponseEntity<HfWeatherIndexResp> weatherIndexResponse = restTemplate.getForEntity(weatherIndexUrl, HfWeatherIndexResp.class);
            HfWeatherIndexResp weatherIndexBody = weatherIndexResponse.getBody();
            if (null == weatherIndexBody) {
                throw new RuntimeException("获取天气指数异常！");
            }
            //24小时天气
            String weatherHourUrl = hfConfig.getHourUrl();
            weatherHourUrl = weatherHourUrl.replace("code", code);
            ResponseEntity<HfWeatherHourResp> weatherHourResponse = restTemplate.getForEntity(weatherHourUrl, HfWeatherHourResp.class);
            HfWeatherHourResp weatherHourBody = weatherHourResponse.getBody();
            if (null == weatherHourBody) {
                throw new RuntimeException("获取天气指数异常！");
            }

            String s = covertWeatherData(tagname, weatherBody.getNow(), weatherIndexBody.getDaily(), weatherHourBody.getHourly());
            res.put(tagid, s);
        }
        return res;
    }

    /**
     * @param tagName   城市名
     * @param now       实时天气
     * @param indexList 天气指数
     * @param hourList  逐小时天气
     * @return 天气预报
     */
    private String covertWeatherData(String tagName, WeatherNow now, List<WeatherIndex> indexList, List<WeatherHour> hourList) {

        //降雨预报  未来一小时降雨
        WeatherHour weatherHour = hourList.get(0);
        String pop = weatherHour.getPop();
        if (!StringUtils.isEmpty(pop)) {
            if (!"0".equals(pop)) {
                pop = "降水概率：" + pop + "%";
            }
        }
        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int value = dayOfWeek.getValue();
        String[] valueArr = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        String dateString = date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
        StringBuilder builder = new StringBuilder("【实时天气】【" + tagName + "】\n");
        builder.append(dateString).append("  ").append(valueArr[value - 1]).append("\n\n");
        builder.append(now.getText()).append("\n");
        builder.append(pop).append("\n\n");
        builder.append("气温：").append(now.getTemp()).append("度\n");
        builder.append("体感温度：").append(now.getFeelsLike()).append("度\n");
        builder.append("风力等级：").append(now.getWindScale()).append("级\n");
        builder.append("相对湿度：").append(now.getHumidity()).append("%\n\n");
        //天气指数
        for (WeatherIndex index : indexList) {
            builder.append(index.getName()).append("：").append(index.getCategory()).append("\n");
            builder.append(index.getText()).append("\n\n");
        }
        String pluginUrl = hfConfig.getPluginUrl();
        builder.append("详细天气请查看 -> ").append("<a href=\"").append(pluginUrl).append("\">和风天气</a>");

        return builder.toString();
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
