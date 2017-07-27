package com.pnj.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 *
 * @author nanjing
 * @date 17-5-19:上午11:38
 */
public interface DisruptorEventHandler<V> extends EventHandler<EventWrap<V>> {
}
