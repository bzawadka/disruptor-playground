package pl.bzawadka.disruptor;

import com.lmax.disruptor.EventHandler;

import java.util.logging.Logger;

/**
 * consumer reading data from the ring buffer
 */
public class Consumers {
    private static Logger logger = Logger.getGlobal();

    public EventHandler<OrderEvent> persistHandler() {
        EventHandler<OrderEvent> handler
                = ((event, sequence, endOfBatch)
                -> persist(sequence));
        return handler;
    }

    public EventHandler<OrderEvent> unmarshallHandler() {
        return (event, sequence, endOfBatch) -> unmarshall(sequence);
    }

    public static EventHandler<OrderEvent> orderEventHandler() {
        return (event, sequence, endOfBatch) -> processOrder(event.getAmount(), sequence);
    }

    public EventHandler<FilledEvent> getFilledEventHandler() {
        EventHandler<FilledEvent> eventHandler
                = ((event, sequence, endOfBatch)
                -> processOrder(event.getFilledAmount(), sequence));
        return eventHandler;
    }

    private static void persist(long sequenceId) {
        logger.info(String.format("Persisting sequence id %s", sequenceId));
    }

    private static void unmarshall(long sequenceId) {
        logger.info(String.format("Unmarshalling sequence id %s", sequenceId));
    }

    private static void processOrder(long amount, long sequenceId) {
        logger.info(String.format("Processing order amount %s; sequence id: %s", amount, sequenceId));
    }
}
