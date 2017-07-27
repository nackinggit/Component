package com.pnj.disruptor;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 *
 * @author nanjing
 * @date 17-5-19:上午11:19
 */
public class EventWrap<V> {
    private V data;

    public void setData(V v) {
        this.data = v;
    }

    public V getData() {
        return data;
    }
}


