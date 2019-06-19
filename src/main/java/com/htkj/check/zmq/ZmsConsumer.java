package com.htkj.check.zmq;

import java.util.Date;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.zeromq.ZMQ;

/**
 *
 * @author jineral
 * @date 2018-12-13
 */
public class ZmsConsumer {

    ZMQ.Socket session;
    ThreadPoolTaskExecutor executor;
    ZmsTextMsgHandler msgHandler;
    FlowCounter counter;

    public ZmsConsumer(ZMQ.Socket session, ThreadPoolTaskExecutor executor, ZmsTextMsgHandler msgHandler) {
        this.session = session;
        this.executor = executor;
        this.msgHandler = msgHandler;
    }

    public void receiveStr() {
        executor.execute(() -> {
            String str = null;
            try {
                str = session.recvStr(1);
                if (str == null) {
                    counter.zmqTimes.incrementAndGet();
                }
                while (str != null) {
                    if (str.length() > 5) {
                        msgHandler.handleMsg(str);
                    }
                    str = session.recvStr(1);
                    if (str == null) {
                        counter.zmqTimes.incrementAndGet();
                    }
                }
            } catch (Exception e) {
                System.out.println("[ZmsConsumer.receiveStr,err][" + (new Date()).toString() + "]" + e.getMessage());
                System.out.println("[ZmsConsumer.receiveStr,session][" + (new Date()).toString() + "]" + session);
                System.out.println("[ZmsConsumer.receiveStr,msgHandler][" + (new Date()).toString() + "]" + msgHandler);
                System.out.println("[ZmsConsumer.receiveStr,str][" + (new Date()).toString() + "]" + str);
                e.printStackTrace();
            }
        });
    }

    public void close() {
        this.session.close();
    }

}
