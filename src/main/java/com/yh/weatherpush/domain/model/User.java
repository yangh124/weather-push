package com.yh.weatherpush.domain.model;


import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long userId;

    private String name;

    /**
     * 用户标签集合，例如 "杭州", "早班员工"
     */
    private Set<String> tags = new HashSet<>();

    /**
     * 添加标签
     */
    public void addTag(String tag) {
        this.tags.add(tag);
    }

    /**
     * 移除标签
     */
    public void removeTag(String tag) {
        this.tags.remove(tag);
    }
}
