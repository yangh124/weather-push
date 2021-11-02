package com.yh.weatherpush.config;

import com.alibaba.fastjson.JSONArray;
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

    private List<TagLocation> list;

    @PostConstruct
    public void init() {
        String path = "/static/tag-location.json";
        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("读取文件失败");
        } else {
            JSONArray json = null;
            try {
                json = JSONArray.parseObject(inputStream, JSONArray.class);
                list = json.toJavaList(TagLocation.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
