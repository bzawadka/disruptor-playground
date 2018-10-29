package pl.bzawadka.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * event that carries the data
 */
public class OrderEvent {
    private long amount;

    public final static EventFactory FACTORY = () -> new OrderEvent();

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
