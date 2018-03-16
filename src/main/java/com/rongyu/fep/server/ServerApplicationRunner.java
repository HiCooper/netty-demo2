package com.rongyu.fep.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Berry_Cooper
 * @date 2018/1/16.
 * Spring Boot主程序启动后，执行该run方法
 */
@Component
public class ServerApplicationRunner implements ApplicationRunner {

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        nettyServer.run();
    }
}
