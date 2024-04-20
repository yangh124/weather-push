package com.yh.weatherpush.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class QuartzStopEvent extends ApplicationEvent {

    private String id;

    public QuartzStopEvent(String id) {
        super(id);
    }

}
