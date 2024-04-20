package com.yh.weatherpush.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class TagDeleteEvent extends ApplicationEvent {

    private Integer tagId;

    public TagDeleteEvent(Integer tagId) {
        super(tagId);
    }

}
