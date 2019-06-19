package com.htkj.check.zmq;

import com.htkj.check.workshop.MsgGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * zmq配置
 *
 * @author jineral
 * @date 2018-12-13
 */
@Configuration
@EnableScheduling
public class ZMQConfiger {

    @Autowired
    MsgGateway mgw;
    @Autowired
    FlowCounter counter;

    @Bean(initMethod = "initialize", destroyMethod = "destroy") //1
    ZmsConsumerContainer zmsConsumerContainer() {
        return new ZmsConsumerContainer((String msg) -> {
            int n = counter.zmq.incrementAndGet();
//            System.out.println(String.format("[ZmsConsumerContainer][%d]%s", n, msg));
            mgw.echoZMQMsg_g6(msg);
        });
    }

}
