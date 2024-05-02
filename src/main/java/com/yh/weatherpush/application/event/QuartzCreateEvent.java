package com.yh.weatherpush.application.event;

import com.yh.weatherpush.interfaces.dto.QuartzBean;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class QuartzCreateEvent extends ApplicationEvent {

    private QuartzBean quartzBean;

    public QuartzCreateEvent(Object source, QuartzBean quartzBean) {
        super(source);
        this.quartzBean = quartzBean;
    }
}
