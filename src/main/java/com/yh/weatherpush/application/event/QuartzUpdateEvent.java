package com.yh.weatherpush.application.event;

import com.yh.weatherpush.interfaces.dto.QuartzBean;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class QuartzUpdateEvent extends ApplicationEvent {

    private QuartzBean quartzBean;

    public QuartzUpdateEvent(Object source, QuartzBean quartzBean) {
        super(source);
        this.quartzBean = quartzBean;
    }
}
