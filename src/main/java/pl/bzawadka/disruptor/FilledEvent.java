package pl.bzawadka.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * event that carries the data
 */
public class FilledEvent {
    private long filledAmount;
    private long cancelledAmount;

    public final static EventFactory FACTORY = () -> new FilledEvent();

    public long getFilledAmount() {
        return filledAmount;
    }

    public void setFilledAmount(long filledAmount) {
        this.filledAmount = filledAmount;
    }

    public long getCancelledAmount() {
        return cancelledAmount;
    }

    public void setCancelledAmount(long cancelledAmount) {
        this.cancelledAmount = cancelledAmount;
    }
}
