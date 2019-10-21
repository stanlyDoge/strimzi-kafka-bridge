/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */

package io.strimzi.kafka.bridge.metrics;

public interface BridgeMXBean {
    Integer getMessagesPerSecond();
    Integer getMessagesFailedPerSecond();
    Integer getConsumersCount();
    String getKafkaAddress();
}
