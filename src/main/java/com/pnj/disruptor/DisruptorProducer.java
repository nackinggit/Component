package com.pnj.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ThreadFactory;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-19:上午10:24
 */
public class DisruptorProducer<V> {
    private int bufferSize = 1024 * 1024;
    private RingBuffer<EventWrap<V>> ringBuffer;
    private Disruptor<EventWrap<V>> disruptor;
    private EventFactory<EventWrap<V>> eventFactory;
    private ThreadFactory threadFactory;
    private ProducerType producerType;
    private WaitStrategy waitStrategy;


    public void send(V v) {
        long seq = ringBuffer.next();
        try {
            EventWrap eventWrap = ringBuffer.get(seq);
            eventWrap.setV(v);
        } finally {
            ringBuffer.publish(seq);
        }
    }

    public void addHandler(EventHandler... handlers) {
        disruptor.handleEventsWith(handlers);
    }

    public void init() {
        this.disruptor = new Disruptor<EventWrap<V>>(eventFactory, bufferSize, threadFactory, producerType, waitStrategy);
    }

    public void start() {
        this.ringBuffer = disruptor.start();
    }

    public void shutdown() {
        disruptor.shutdown();
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public EventFactory<EventWrap<V>> getEventFactory() {
        return eventFactory;
    }

    public void setEventFactory(EventFactory<EventWrap<V>> eventFactory) {
        this.eventFactory = eventFactory;
    }

    public Disruptor<EventWrap<V>> getDisruptor() {
        return this.disruptor;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }


    public ProducerType getProducerType() {
        return producerType;
    }

    public void setProducerType(ProducerType producerType) {
        this.producerType = producerType;
    }

    public WaitStrategy getWaitStrategy() {
        return waitStrategy;
    }

    public void setWaitStrategy(WaitStrategy waitStrategy) {
        this.waitStrategy = waitStrategy;
    }

}
