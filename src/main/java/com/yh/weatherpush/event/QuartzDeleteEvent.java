package com.yh.weatherpush.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class QuartzDeleteEvent extends ApplicationEvent {

    private String id;

    public QuartzDeleteEvent(Object source, String id) {
        super(source);
        this.id = id;
    }
}
