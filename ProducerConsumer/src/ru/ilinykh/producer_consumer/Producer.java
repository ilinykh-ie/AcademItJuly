package ru.ilinykh.producer_consumer;

public class Producer implements Runnable {
    private final ProducerConsumerManager manager;

    public Producer(ProducerConsumerManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            int currentNumber = 1;

            while (!Thread.currentThread().isInterrupted()) {
                //noinspection BusyWait
                Thread.sleep(2000);
                manager.addItem("Item" + currentNumber);
                currentNumber++;
            }
        } catch (InterruptedException ignored) {
        }
    }
}
