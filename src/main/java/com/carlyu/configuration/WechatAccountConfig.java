package com.carlyu.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    /**
     * 公众平台id
     * <p>
     * 公众平台密钥
     */

    private String mpAppId;

    /**
     * 公众平台密钥
     */

    private String mpAppSecret;
}
