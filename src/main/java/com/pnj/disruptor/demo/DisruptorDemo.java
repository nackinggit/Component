package com.pnj.disruptor.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.ProducerType;
import com.pnj.disruptor.DisruptorEventHandler;
import com.pnj.disruptor.DisruptorProducer;
import com.pnj.disruptor.EventWrap;
import java.util.concurrent.Executors;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-19:下午2:04
 */
public class DisruptorDemo {
    DisruptorProducer<String> disruptorProducer;

    private MessageChannel channel = new MessageChannel() {
        @Override
        public boolean send(Message<?> message) {
//            System.out.println(message.getPayload().toString());
            return true;
        }

        @Override
        public boolean send(Message<?> message, long timeout) {
//            System.out.println(message.getPayload().toString());
            return true;
        }
    };

    public void init() {
        disruptorProducer = new DisruptorProducer<>();
        disruptorProducer.setBufferSize(1024*1024);
        disruptorProducer.setEventFactory(new EventFactory<EventWrap<String>>() {
            @Override
            public EventWrap<String> newInstance() {
                return new EventWrap<String>();
            }
        });
        disruptorProducer.setThreadFactory(Executors.defaultThreadFactory());
        disruptorProducer.setProducerType(ProducerType.MULTI);
        disruptorProducer.setWaitStrategy(new BlockingWaitStrategy());
        disruptorProducer.init();
        DisruptorEventHandler<String> disruptorConsumer = new DisruptorEventHandler<String>() {

            @Override
            public void onEvent(EventWrap<String> stringEventWrap, long l, boolean b) throws Exception {
                System.out.println(stringEventWrap.getV());
            }
        };

        DisruptorEventHandler<String> eventHandler = new DisruptorEventHandler<String>() {
            @Override
            public void onEvent(EventWrap event, long sequence, boolean endOfBatch) throws Exception {
                if((sequence % 2) == 0) {
                    System.out.println("eventHandler: " + event.getV().toString() + "-" + sequence + "-" + endOfBatch);
                }
            }
        };
        disruptorProducer.addHandler(disruptorConsumer, eventHandler);
        disruptorProducer.start();
    }

    public static void main(String[] args) throws InterruptedException {
        DisruptorDemo disruptorDemo = new DisruptorDemo();
        disruptorDemo.init();
        int i = 100;
        while (i-- != 0) {
            disruptorDemo.disruptorProducer.send("hello world");
//            Thread.sleep(1000);
        }
    }


}
