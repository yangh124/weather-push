package com.yh.weatherpush.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yh.weatherpush.config.property.HfConfigPrProperties;
import com.yh.weatherpush.dto.hfweather.*;
import com.yh.weatherpush.entity.Tag;
import com.yh.weatherpush.exception.ApiException;
import com.yh.weatherpush.manager.api.HfWeatherApiClient;
import com.yh.weatherpush.service.WeatherService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
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
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private HfWeatherApiClient hfWeatherApiClient;
    @Autowired
    private HfConfigPrProperties hfConfigPrProperties;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<Integer, String> getTodayWeather(List<Tag> tags) {
        Map<Integer, String> res = new HashMap<>(tags.size());
        for (Tag tag : tags) {
            Integer tagid = tag.getTagId();
            String tagname = tag.getTagName();
            String code = tag.getCode();
            // 实时天气
            HfWeatherResp realTimeWeather = hfWeatherApiClient.getRealTimeWeather(code, hfConfigPrProperties.getKey());
            if (null == realTimeWeather) {
                throw new ApiException("获取天气异常！");
            }
            // 天气指数
            HfWeatherIndexResp weatherIndex = hfWeatherApiClient.getWeatherIndex(3, code, hfConfigPrProperties.getKey());
            if (null == weatherIndex) {
                throw new ApiException("获取天气指数异常！");
            }
            // 24小时天气
            HfWeatherHourResp hourWeather = hfWeatherApiClient.getHourWeather(code, hfConfigPrProperties.getKey());
            if (null == hourWeather) {
                throw new ApiException("获取天气指数异常！");
            }
            String s = covertWeatherData(tagname, realTimeWeather.getNow(), weatherIndex.getDaily(), hourWeather.getHourly());
            res.put(tagid, s);
        }
        return res;
    }

    @Override
    public Map<Integer, String> getTomWeather(List<Tag> tags) {
        Map<Integer, String> res = new HashMap<>(tags.size());
        for (Tag tag : tags) {
            Integer tagid = tag.getTagId();
            String tagname = tag.getTagName();
            String code = tag.getCode();
            // 明日天气
            HfWeatherDayResp dayWeather = hfWeatherApiClient.getDayWeather(code, hfConfigPrProperties.getKey());
            if (null == dayWeather) {
                throw new ApiException("获取3天天气异常！");
            }
            WeatherDay weatherDay = dayWeather.getDaily().get(1);
            String str = "【明日天气】【" + tagname + "】\n\n" + weatherDay.getTextDay() + "\n"
                    + "气温：" + weatherDay.getTempMin() + "~" + weatherDay.getTempMax()
                    + "度\n\n"
                    + "相对湿度：" + weatherDay.getHumidity() + "%\n"
                    + "降水量：" + weatherDay.getPrecip() + " mm\n"
                    + weatherDay.getWindDirDay() + " " + weatherDay.getWindScaleDay() + "级"
                    + "\n";
            res.put(tagid, str);
        }
        return res;
    }

    @Override
    public Map<Integer, String> getRedisWeather(List<Tag> tags) {
        Map<Integer, String> res = new HashMap<>();
        LocalDate now = LocalDate.now();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        StringCodec stringCodec = new StringCodec();
        for (Tag tag : tags) {
            Integer tagid = tag.getTagId();
            String key = format + ":" + tagid;
            RBucket<String> bucket = redissonClient.getBucket(key, stringCodec);
            String s = bucket.get();
            if (null != s) {
                res.put(tagid, s);
            }
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
    private String covertWeatherData(String tagName, WeatherNow now, List<WeatherIndex> indexList,
                                     List<WeatherHour> hourList) {

        // 降雨预报 未来一小时降雨
        WeatherHour weatherHour = hourList.get(0);
        String pop = weatherHour.getPop();
        if (StrUtil.isNotBlank(pop)) {
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
        if ("0".equals(pop)) {
            builder.append("不会下雨").append("\n\n");
        } else {
            builder.append(pop).append("\n\n");
        }
        builder.append("气温：").append(now.getTemp()).append("度\n");
        builder.append("体感温度：").append(now.getFeelsLike()).append("度\n");
        builder.append("风力等级：").append(now.getWindScale()).append("级\n");
        builder.append("相对湿度：").append(now.getHumidity()).append("%\n\n");
        // 天气指数
        for (WeatherIndex index : indexList) {
            builder.append(index.getName()).append("：").append(index.getCategory()).append("\n");
            builder.append(index.getText()).append("\n\n");
        }

        return builder.toString();
    }

    @Override
    public String getLocation(String name) {
        String cityUrl = hfConfigPrProperties.getCityUrl();
        cityUrl = cityUrl.replace("cityName", name);
        ResponseEntity<HfCityResp> response = restTemplate.getForEntity(cityUrl, HfCityResp.class);
        HfCityResp locations = response.getBody();
        if (null == locations) {
            throw new ApiException("获取地区信息失败!");
        }
        String code = locations.getCode();
        if (!"200".equals(code)) {
            throw new ApiException("获取地区信息失败! -> " + locations);
        }
        Location location = locations.getLocation().get(0);
        return location.getId();
    }
}
