package com.yh.weatherpush.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yh.weatherpush.dto.Holiday;
import com.yh.weatherpush.dto.TagLocation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author : yh
 * @date : 2021/11/2 15:46
 */
@Component
@Getter
@Setter
public class JsonConfig {

    private List<TagLocation> tagLocationList;

    private List<Holiday> holidayList;

    @PostConstruct
    public void init() {
        initTagLocation();
        initHoliday();
    }

    private void initTagLocation() {
        String path = "/static/tag-location.json";
        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("读取文件tag-location.json失败");
        } else {
            JSONArray json = null;
            try {
                json = JSONArray.parseObject(inputStream, JSONArray.class);
                tagLocationList = json.toJavaList(TagLocation.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initHoliday() {
        String path = "/static/holiday.json";
        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("读取文件holiday.json失败");
        } else {
            try {
                JSONObject json = JSONObject.parseObject(inputStream, JSONObject.class);
                JSONArray days = json.getJSONArray("days");
                holidayList = days.toJavaList(Holiday.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
