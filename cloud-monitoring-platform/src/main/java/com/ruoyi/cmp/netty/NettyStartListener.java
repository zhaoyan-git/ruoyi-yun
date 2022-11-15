package com.ruoyi.cmp.netty;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听Spring容器启动完成，完成后启动Netty服务器
 * @author Gjing
 **/
@Component
public class NettyStartListener implements ApplicationRunner {
    @Resource
    private BootNettyServer socketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        this.socketServer.setPort(8088);
        this.socketServer.bind(8088);
    }
}
