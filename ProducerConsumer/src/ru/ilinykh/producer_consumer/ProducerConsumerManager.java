package ru.ilinykh.producer_consumer;

import java.util.ArrayList;

public class ProducerConsumerManager {
    private final ArrayList<String> list = new ArrayList<>();
    private final Object lock = new Object();
    private final static int capacity = 10;
    private final static int producersCount = 3;
    private final static int consumersCount = 3;

    public void start() {
        for (int i = 0; i < consumersCount; i++) {
            Thread consumer = new Thread(new Consumer(this));
            consumer.start();
        }

        for (int i = 0; i < producersCount; i++) {
            Thread producer = new Thread(new Producer(this));
            producer.start();
        }
    }

    public String getItem() throws InterruptedException {
        synchronized (lock) {
            while (list.size() <= 0) {
                lock.wait();
            }

            String result = list.remove(list.size() - 1);
            lock.notifyAll();
            return result;
        }
    }

    public void addItem(String item) throws InterruptedException {
        synchronized (lock) {
            while (list.size() >= capacity) {
                lock.wait();
            }

            list.add(item);
            lock.notifyAll();
        }
    }
}
