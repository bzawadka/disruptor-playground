package pl.bzawadka.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.Random;

public class App {

    public static void main(String[] args) {
                /* API for setting up the disruptor pattern */
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(
                OrderEvent.FACTORY,             // a factory to create events in the ring buffer.
                16,
                DaemonThreadFactory.INSTANCE,   // a ThreadFactory to create threads for processors.
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()      // Wait Strategy determines how a consumer will wait for events to be placed into the Disruptor by a producer
        );

        /* EventHandler - an interface that is implemented by the user and represents a consumer for the Disruptor. */

        EventHandler<OrderEvent> persistHandler = new Consumers().persistHandler();
        EventHandler<OrderEvent> unmarshallHandler = new Consumers().unmarshallHandler();
        EventHandler<OrderEvent> orderEventHandler = new Consumers().orderEventHandler();
        disruptor
                .handleEventsWith(persistHandler)
                .handleEventsWith(unmarshallHandler);
        disruptor.after(persistHandler).handleEventsWith(unmarshallHandler);
        disruptor.after(unmarshallHandler).handleEventsWith(orderEventHandler);

        /* Ring Buffer is only responsible for the storing and updating of the data (Events) that move through the Disruptor */
        RingBuffer<OrderEvent> ringBuffer = disruptor.start();

        Random random = new Random();
        for (int eventCount = 0; eventCount < 32; eventCount++) {
            long sequenceId = ringBuffer.next();

            OrderEvent orderEvent = ringBuffer.get(sequenceId);
            orderEvent.setAmount(random.nextInt(1000000));

            ringBuffer.publish(sequenceId);
        }
    }

}
