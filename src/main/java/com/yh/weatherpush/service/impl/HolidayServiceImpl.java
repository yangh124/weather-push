package com.yh.weatherpush.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.config.HolidayConfig;
import com.yh.weatherpush.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : yh
 * @date : 2021/11/21 14:34
 */
@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HolidayConfig holidayConfig;

    @Override
    public boolean isHoliday(LocalDate date) {
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (null == date) {
            date = LocalDate.now();
        }
        String dateStr = yyyyMMdd.format(date);
        String url = holidayConfig.getDateUrl().replace("dateStr", dateStr);
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject jsonObject = response.getBody();
        if (null != jsonObject) {
            JSONObject data = jsonObject.getJSONObject("data");
            if (null != data) {
                JSONArray list = data.getJSONArray("list");
                if (null != list && list.size() > 0) {
                    Integer workday = list.getJSONObject(0).getInteger("workday");
                    return 2 == workday;
                }
            }
        }
        return false;
    }

}
