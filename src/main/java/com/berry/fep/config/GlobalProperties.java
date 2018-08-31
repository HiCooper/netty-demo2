package com.berry.fep.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Berry_Cooper
 * @date 2018/1/22.
 */


@Configuration
@ConfigurationProperties(prefix = "netty.server")
public class GlobalProperties {

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
