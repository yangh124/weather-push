package com.yh.weatherpush.event;

import com.yh.weatherpush.dto.QuartzBean;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class QuartzCreateEvent extends ApplicationEvent {

    private QuartzBean quartzBean;

    public QuartzCreateEvent(QuartzBean quartzBean) {
        super(quartzBean);
    }

}
