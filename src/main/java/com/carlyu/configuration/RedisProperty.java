package com.carlyu.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisProperty {
    private String host;
    private int port;
    private String pass;
    private String maxIdle;
    private String maxActive;
    private String maxWait;
    private String testOnBorrow;
    private String testOnReturn;
    private int timeout;
    private String namespace;
    private String parentDomainName;
    private String cookieName;
}