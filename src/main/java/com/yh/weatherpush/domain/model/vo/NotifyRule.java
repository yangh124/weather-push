package com.yh.weatherpush.domain.model.vo;

import com.yh.weatherpush.interfaces.enums.NotifyTypeEnum;
import lombok.Builder;
import org.quartz.CronExpression;

@Builder
public class NotifyRule {

    /**
     * 通知类型
     */
    private final NotifyTypeEnum notifyTypeEnum;

    /**
     * 通知时间cron表达式
     */
    private String cronExpression;

    /**
     * 校验表达式是否有效
     *
     * @param cronExpression cron表达式
     * @return 是否为有效的格式
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }
}
