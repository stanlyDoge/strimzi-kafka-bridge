/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

package io.strimzi.kafka.bridge.metrics;

import java.util.concurrent.atomic.AtomicInteger;

public class BridgeStatus implements BridgeMXBean {
    private static AtomicInteger messagesPerSecond;
    private static AtomicInteger messagesFailedPerSecond;
    private static AtomicInteger consumersCount;
    private static String kafkaAddress;
    private Thread backgroundThread;

    public BridgeStatus(String programName) {

        // First we initialize all the metrics
        this.backgroundThread = new Thread();
        this.messagesPerSecond = new AtomicInteger(0);
        this.messagesFailedPerSecond = new AtomicInteger(0);
        this.consumersCount = new AtomicInteger(0);

        // We will use a background thread to update the metrics
        this.backgroundThread = new Thread(() -> {
            try {
                while (true) {
                    // Every second we update the metrics
                    Thread.sleep(1000L);
                    messagesPerSecond.set(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.backgroundThread.setName("backgroundThread");
        this.backgroundThread.start();
    }

    @Override
    public Integer getMessagesPerSecond() {
        return messagesPerSecond.get();
    }

    @Override
    public Integer getMessagesFailedPerSecond() {
        return messagesFailedPerSecond.get();
    }

    @Override
    public Integer getConsumersCount() {
        return consumersCount.get();
    }

    @Override
    public String getKafkaAddress() {
        return kafkaAddress;
    }

    public static void increaseMessages() {
        messagesPerSecond.getAndIncrement();
    }

    public static void increaseFailedMessages() {
        messagesFailedPerSecond.getAndIncrement();
    }

    public static void increaseConsumers() {
        consumersCount.getAndIncrement();
    }

    public static void decreaseConsumers() {
        consumersCount.getAndDecrement();
    }

    public static void setKafkaAddress(String newKafkaAddress) {
        kafkaAddress = newKafkaAddress;
    }
}
