package com.yh.weatherpush.domain.model.aggregates;

import com.yh.weatherpush.domain.model.User;
import com.yh.weatherpush.domain.model.vo.Location;
import lombok.Builder;

@Builder
public class Notification {

    private Long notificationId;

    private User user;

    private Location location;

    private String deliveryCronExpression;

    private boolean isEnable;

    public void enable() {
        this.isEnable = true;
    }

    public void disable() {
        this.isEnable = false;
    }
}
