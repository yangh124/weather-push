package com.yh.weatherpush.enums;

import cn.hutool.core.util.StrUtil;

/**
 * @author : yh
 * @date : 2022/5/1 16:58
 */
public enum TaskEnum {

    WEATHER_TODAY("weatherTodayJob", "今日天气"),

    WEATHER_TOMORROW("weatherTomorrowJob", "明日天气");

    private final String name;

    private final String desc;

    TaskEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByName(String name) {
        if (StrUtil.isNotBlank(name)) {
            for (TaskEnum value : TaskEnum.values()) {
                if (value.getName().equals(name)) {
                    return value.getDesc();
                }
            }
        }
        return "";
    }
}
