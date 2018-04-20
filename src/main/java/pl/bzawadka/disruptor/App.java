package pl.bzawadka.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class App {

    public static void main(String[] args) {

        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(
                MyEvent.FACTORY,
                16,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );

        EventHandler<MyEvent> handler = new PrintConsumer().getEventHandler();
        disruptor.handleEventsWith(handler);

        RingBuffer<MyEvent> ringBuffer = disruptor.start();

        for (int eventCount = 0; eventCount < 32; eventCount++) {
            long sequenceId = ringBuffer.next();
            MyEvent myEvent = ringBuffer.get(sequenceId);
            myEvent.setValue(eventCount);
            ringBuffer.publish(sequenceId);
        }

    }
}
