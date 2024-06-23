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

}
