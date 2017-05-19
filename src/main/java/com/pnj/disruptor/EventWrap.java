package com.pnj.disruptor;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-19:上午11:19
 */
public class EventWrap<V> {
    private V v;

    public void setV(V v) {
        this.v = v;
    }

    public V getV() {
        return v;
    }
}


