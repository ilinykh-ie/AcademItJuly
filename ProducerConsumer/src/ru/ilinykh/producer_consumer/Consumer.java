package ru.ilinykh.producer_consumer;

public class Consumer implements Runnable {
    private final ProducerConsumerManager manager;

    public Consumer(ProducerConsumerManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String item = manager.getItem();
                //noinspection BusyWait
                Thread.sleep(1500);
                System.out.println(item);
            }
        } catch (InterruptedException ignored) {
        }
    }
}
