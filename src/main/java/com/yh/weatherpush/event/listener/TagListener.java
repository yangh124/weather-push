package com.yh.weatherpush.event.listener;

import com.yh.weatherpush.event.TagDeleteEvent;
import com.yh.weatherpush.manager.QywxManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TagListener {

    @Autowired
    private QywxManager qywxManager;

    /**
     * 删除企业微信标签
     */
    @EventListener
    public void tagDelete(TagDeleteEvent event) {
        Integer tagId = event.getTagId();
        if (null != tagId) {
            qywxManager.deleteTag(tagId);
        }
    }

}
