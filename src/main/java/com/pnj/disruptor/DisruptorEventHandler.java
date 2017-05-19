package com.pnj.disruptor;

import com.lmax.disruptor.EventHandler;
import java.util.Objects;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-19:上午11:38
 */
public interface DisruptorEventHandler<V> extends EventHandler<EventWrap<V>> {
}
