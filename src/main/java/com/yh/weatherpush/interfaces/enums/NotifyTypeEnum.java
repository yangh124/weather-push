package com.yh.weatherpush.interfaces.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : yh
 * @date : 2022/5/1 16:58
 */
@AllArgsConstructor
@Getter
public enum NotifyTypeEnum {

    WEATHER_TODAY("weatherTodayJob", "今日天气"),

    WEATHER_TOMORROW("weatherTomorrowJob", "明日天气");

    private final String name;

    private final String desc;


    public static NotifyTypeEnum getByName(String name) {
        if (StrUtil.isNotBlank(name)) {
            for (NotifyTypeEnum value : NotifyTypeEnum.values()) {
                if (value.getName().equals(name)) {
                    return value;
                }
            }
        }
        return null;
    }
}
