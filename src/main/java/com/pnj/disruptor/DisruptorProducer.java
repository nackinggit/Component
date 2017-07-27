package com.pnj.disruptor;

import com.google.common.collect.Maps;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
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
    private EventHandler[] handlers;
    private WorkHandler[] workHandlers;


    public void send(V v) {
        long seq = ringBuffer.next();
        try {
            EventWrap eventWrap = ringBuffer.get(seq);
            eventWrap.setData(v);
        } finally {
            ringBuffer.publish(seq);
        }
    }

    /**
     * 类似广播模式, 即所有handlers都会收到消息
     *
     * @param handlers handler
     */
    public void addHandler(EventHandler... handlers) {
        disruptor.handleEventsWith(handlers);
    }

    /**
     * 类似单播模式, 所有handlers里只有一个handler会接收到消息
     *
     * @param workHandlers handler
     */
    public void addWorkHandlers(WorkHandler... workHandlers) {
        disruptor.handleEventsWithWorkerPool(workHandlers);
    }

    public void init() {
        this.disruptor = new Disruptor<EventWrap<V>>(eventFactory, bufferSize, threadFactory, producerType, waitStrategy);
        if (handlers != null) {
            addHandler(handlers);
        }

        if (workHandlers != null) {
            addWorkHandlers(workHandlers);
        }

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

    public void setHandlers(EventHandler[] handlers) {
        this.handlers = handlers;
    }

    public void setWorkHandlers(WorkHandler[] workHandlers) {
        this.workHandlers = workHandlers;
    }

    @Override
    public String toString() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("producerType", producerType);
        map.put("handlers", Arrays.asList(handlers));
        map.put("workHandlers", Arrays.asList(workHandlers));
        map.put("strategy", waitStrategy.getClass().getName());
        map.put("capacity", bufferSize);
        return map.toString();
    }
}
