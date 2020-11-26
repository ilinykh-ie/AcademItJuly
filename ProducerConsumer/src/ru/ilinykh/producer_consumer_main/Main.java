package ru.ilinykh.producer_consumer_main;

import ru.ilinykh.producer_consumer.ProducerConsumerManager;

public class Main {
    public static void main(String[] args) {
        ProducerConsumerManager manager = new ProducerConsumerManager();
        manager.start();
    }
}
