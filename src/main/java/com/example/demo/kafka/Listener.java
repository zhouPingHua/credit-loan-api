package com.example.demo.kafka;

/**
 * Created by zph  Date：2017/8/9.
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

public class Listener {

    @KafkaListener(topics = {"testtopic"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("testtopic " + message);
        }
    }
    @KafkaListener(topics = {"testtopic2"})
    public void listen2(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            System.out.println("testtopic2" + message);
        }
    }
}

