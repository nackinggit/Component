package com.pnj.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.Executors;
import org.springframework.messaging.Message;

/**
 * <p>Description: 默认数据类型为Spring-message</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 *
 * @author nanjing
 * @date 17-5-19:下午4:52
 */
public class DefaultDisruptorProducer extends DisruptorProducer<Message> {
    public DefaultDisruptorProducer() {
        this.setEventFactory(new EventFactory<EventWrap<Message>>() {
            @Override
            public EventWrap<Message> newInstance() {
                return new EventWrap<Message>();
            }
        });
        this.setWaitStrategy(new BlockingWaitStrategy());
        this.setThreadFactory(Executors.defaultThreadFactory());
        this.setProducerType(ProducerType.MULTI);
        this.setBufferSize(1024 * 1024);
    }
}
