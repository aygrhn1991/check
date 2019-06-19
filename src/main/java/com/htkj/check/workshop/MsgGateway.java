package com.htkj.check.workshop;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 *
 * @author jineral
 * @date 2018-12-12
 */
@MessagingGateway
public interface MsgGateway {

    @Gateway(requestChannel = "zmq_dtu_msg_c")
    public void echoZMQMsg_g6(String json);
}
