package pl.bzawadka.disruptor;

import com.lmax.disruptor.EventHandler;

import java.util.logging.Logger;

/**
 * consumer reading data from the ring buffer
 */
public class PrintConsumer {
    Logger logger = Logger.getGlobal();

    public EventHandler<MyEvent> getEventHandler() {
        EventHandler<MyEvent> eventHandler
                = (event, sequence, endOfBatch)
                -> print(event.getValue(), sequence);
        return eventHandler;
    }

    private void print(int id, long sequenceId) {
        logger.info("Id is " + id
                + " sequence id that was used is " + sequenceId);
    }
}
