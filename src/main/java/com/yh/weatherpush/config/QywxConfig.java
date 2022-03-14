package com.yh.weatherpush.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author : yh
 * @date : 2021/10/31 13:09
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "qywx")
public class QywxConfig {

    private String corpid;

    private String pushSecret;

    private String otherSecret;

    private String tokenUrl;

    private String sendUrl;

    private String agentid;

    @NestedConfigurationProperty
    private LabelConfig label;

    @NestedConfigurationProperty
    private MemberConfig member;

    @Getter
    @Setter
    public static class LabelConfig implements Serializable {

        private static final long serialVersionUID = 6594782376511318128L;

        private String createUrl;

        private String listUrl;

        private String deleteUrl;
    }

    @Getter
    @Setter
    public static class MemberConfig implements Serializable {

        private static final long serialVersionUID = -7283609725180969658L;

        /**
         * 邀请加入二维码
         */
        private String joinQrcodeUrl;

        /**
         * 获取部门成员
         */
        private String simpleListUrl;

    }
}
