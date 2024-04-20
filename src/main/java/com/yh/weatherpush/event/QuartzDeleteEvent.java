package com.yh.weatherpush.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class QuartzDeleteEvent extends ApplicationEvent {

    private String id;

    public QuartzDeleteEvent(String id) {
        super(id);
    }

}
