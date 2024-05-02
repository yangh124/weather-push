package com.yh.weatherpush.application.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class QuartzStartEvent extends ApplicationEvent {

    private String id;

    public QuartzStartEvent(Object source, String id) {
        super(source);
        this.id = id;
    }
}
