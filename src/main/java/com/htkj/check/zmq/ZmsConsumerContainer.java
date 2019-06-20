package com.htkj.check.zmq;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

/**
 *
 * @author jineral
 * @date 2018-12-13
 */
public class ZmsConsumerContainer {

    Set<ZmsConsumer> consumers;
    @Value("${zms.consumer.count}")
    int concurrentConsumers;
    @Value("${zms.consumer.pool.size}")
    int poolSize;
    @Value("${zms.consumer.queue.size}")
    int queueCapacity;
    @Value("${zms.mq.url}")
    String conUrl;
    ThreadPoolTaskExecutor executor;
    ZmsTextMsgHandler msgHandler;
    boolean isStop = false;
    @Autowired
    FlowCounter counter;

    public ZmsConsumerContainer(ZmsTextMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public void initialize() {
        this.initThreadPool();
        this.initializeConsumers();
//        this.go();
    }

    public void destroy() {
        for (ZmsConsumer c : this.consumers) {
            c.close();
        }
    }

    // 作废本函数
    @Scheduled(fixedDelay = 1000)
    public void doReceiveMsg() {
        for (ZmsConsumer c : this.consumers) {
            c.receiveStr();
        }
    }

    public void go() {
        this.executor.execute(() -> {
            while (!isStop) {
                for (ZmsConsumer c : this.consumers) {
                    c.receiveStr();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ZmsConsumerContainer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    protected void initializeConsumers() {
        if (this.consumers == null) {
            ZMQ.Context context = ZMQ.context(this.concurrentConsumers);
            this.consumers = new HashSet<>(this.concurrentConsumers);
            for (int i = 0; i < this.concurrentConsumers; i++) {
                Socket session = context.socket(ZMQ.PULL);
                session.connect(this.conUrl);
                ZmsConsumer consumer = new ZmsConsumer(session, this.executor, this.msgHandler);
                consumer.counter = this.counter;
                this.consumers.add(consumer);
            }
        }
    }

    private void initThreadPool() {
        this.executor = new ThreadPoolTaskExecutor();
        //如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
        this.executor.setCorePoolSize(this.poolSize);
        //连接池中保留的最大连接数。Default: 15 maxPoolSize  
        this.executor.setMaxPoolSize(this.poolSize);
        //queueCapacity 线程池所使用的缓冲队列
        this.executor.setQueueCapacity(this.queueCapacity);
        //丢弃被拒绝的任务
//        this.executor.setRejectedExecutionHandler(new DiscardPolicy());
        this.executor.initialize();
    }

    //<editor-fold defaultstate="collapsed" desc="property">
    public Set<ZmsConsumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(Set<ZmsConsumer> consumers) {
        this.consumers = consumers;
    }

    public int getConcurrentConsumers() {
        return concurrentConsumers;
    }

    public void setConcurrentConsumers(int concurrentConsumers) {
        this.concurrentConsumers = concurrentConsumers;
    }

    public String getConUrl() {
        return conUrl;
    }

    public void setConUrl(String conUrl) {
        this.conUrl = conUrl;
    }
    //</editor-fold> 

    public ZmsTextMsgHandler getMsgHandler() {
        return msgHandler;
    }

    public void setMsgHandler(ZmsTextMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

}
