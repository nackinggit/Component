package com.pnj.disruptor.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.ProducerType;
import com.pnj.disruptor.DefaultDisruptorProducer;
import com.pnj.disruptor.DisruptorEventHandler;
import com.pnj.disruptor.DisruptorProducer;
import com.pnj.disruptor.EventWrap;
import java.util.concurrent.Executors;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * <p>Description:Disruptor + Spring Message + BlockingQueue Demo</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-19:下午2:04
 */
public class DisruptorDemo {
    DisruptorProducer<Message> disruptorProducer;

    public void init() {
        disruptorProducer = new DefaultDisruptorProducer();
        disruptorProducer.setBufferSize(1024 * 1024);
        disruptorProducer.setEventFactory(new EventFactory<EventWrap<Message>>() {
            @Override
            public EventWrap<Message> newInstance() {
                return new EventWrap<Message>();
            }
        });
        disruptorProducer.setThreadFactory(Executors.defaultThreadFactory());
        disruptorProducer.setProducerType(ProducerType.MULTI);
        disruptorProducer.setWaitStrategy(new BlockingWaitStrategy());
        DisruptorEventHandler<Message> disruptorConsumer = new DisruptorEventHandler<Message>() {

            @Override
            public void onEvent(EventWrap<Message> stringEventWrap, long l, boolean b) throws Exception {
                System.out.println(stringEventWrap.getData().getPayload());
            }
        };

        DisruptorEventHandler<Message> eventHandler = new DisruptorEventHandler<Message>() {
            @Override
            public void onEvent(EventWrap<Message> event, long sequence, boolean endOfBatch) throws Exception {
                if ((sequence % 2) == 0) {
                    System.out.println("eventHandler: " + event.getData().getPayload() + "-" + sequence + "-" + endOfBatch);
                }
            }
        };
        disruptorProducer.addHandler(disruptorConsumer, eventHandler);
        disruptorProducer.init();
    }

    public static void main(String[] args) throws InterruptedException {
        DisruptorDemo disruptorDemo = new DisruptorDemo();
        disruptorDemo.init();
        int i = 100;
        while (i-- != 0) {
            String str = "Disruptor + Spring Message";
            Message<String> message = MessageBuilder.withPayload(str).build();
            disruptorDemo.disruptorProducer.send(message);
        }
    }


}
