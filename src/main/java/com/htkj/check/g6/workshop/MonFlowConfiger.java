package com.htkj.check.g6.workshop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;

/**
 * 监控流程 配置
 *
 * @author jineral
 * @date 2019-2-28
 */
@Configuration
@EnableIntegration
public class MonFlowConfiger {
    
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(500);
    }

    /**
     * 终端上传的各类消息，都以json的格式存入本channel中
     *
     * @return
     */
    @Bean(name = "zmq_dtu_msg_c")
    public MessageChannel accZmqMsgChannel_vnu() {
        return MessageChannels.queue(20000).get();
    }

    /**
     * 接收终端消息处理、路由流程、全量发送至页面流程
     *
     * @return
     */
    @Bean
    public IntegrationFlow dtuMsgHandleFlow() {
        return IntegrationFlows
                .from("zmq_dtu_msg_c")
                //.channel(c -> c.executor(taskExecutor()))
                //将终端传入的json信息转换为数据包
                .split("dtuMsgTransform", "toMetaMsg")
                .channel(c -> c.queue(20000))
                .handle("dtuMsgHandle","go")                
                .get();
    }
    
}
