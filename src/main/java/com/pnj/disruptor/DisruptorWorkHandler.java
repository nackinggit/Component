package com.pnj.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-19:下午4:19
 */
public interface DisruptorWorkHandler<V> extends WorkHandler<EventWrap<V>> {
}
