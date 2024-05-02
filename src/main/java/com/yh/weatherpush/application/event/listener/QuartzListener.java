package com.yh.weatherpush.application.event.listener;

import com.yh.weatherpush.application.event.*;
import com.yh.weatherpush.infrastructure.manager.QuartzManager;
import com.yh.weatherpush.interfaces.dto.QuartzBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class QuartzListener {

    @Autowired
    private QuartzManager quartzManager;


    /**
     * 新
     */
    @EventListener
    private void quartzCreate(QuartzCreateEvent event) {
        QuartzBean quartzBean = event.getQuartzBean();
        quartzManager.create(quartzBean);
    }

    /**
     * 删除
     */
    @EventListener
    private void quartzDelete(QuartzDeleteEvent event) {
        String id = event.getId();
        quartzManager.delete(id);
    }

    /**
     * 修改
     */
    @EventListener
    private void quartzUpdate(QuartzUpdateEvent event) {
        QuartzBean quartzBean = event.getQuartzBean();
        quartzManager.update(quartzBean);
    }

    /**
     * 启动
     */
    @EventListener
    private void quartzStart(QuartzStartEvent event) {
        String id = event.getId();
        quartzManager.start(id);
    }

    /**
     * 停止
     */
    @EventListener
    private void quartzStop(QuartzStopEvent event) {
        String id = event.getId();
        quartzManager.stop(id);
    }
}
