package pl.bzawadka.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * event that carries the data
 */
public class MyEvent {
    private int value;

    public final static EventFactory FACTORY = () -> new MyEvent();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
