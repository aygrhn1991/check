package com.htkj.check.zmq;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 数据流程 计数器
 *
 * @author jineral
 * @date 2019-3-18
 */
@Component
public class FlowCounter {

    public AtomicInteger zmq = new AtomicInteger();
    public AtomicInteger toMetaMsg = new AtomicInteger();
    public AtomicInteger vehCacheService = new AtomicInteger();
    public AtomicInteger fullMetaMsg2PageMsg = new AtomicInteger();
    public AtomicInteger pageMsgSend = new AtomicInteger();
    public AtomicInteger vehEncMixer = new AtomicInteger();
    public AtomicInteger enc_validateService = new AtomicInteger();
    
    public AtomicInteger zmqTimes = new AtomicInteger();
    

    @Scheduled(fixedDelay = 10000)
    public void doLog() {
        Logger.getLogger(FlowCounter.class.getName()).log(Level.INFO, "**************************************************************************");
        Logger.getLogger(FlowCounter.class.getName()).log(Level.INFO, "接收zmq数据条数[{0}]", this.zmq.get());
        Logger.getLogger(FlowCounter.class.getName()).log(Level.INFO, "[DtuMsgTransform.toMetaMsg]接收数据条数[{0}]", this.toMetaMsg.get());
        Logger.getLogger(FlowCounter.class.getName()).log(Level.INFO, "[zmq执行次数[{0}]", this.zmqTimes.get());
    }
}
