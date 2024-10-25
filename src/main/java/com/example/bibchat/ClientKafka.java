package com.example.bibchat;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.time.Duration;

import static java.lang.System.getProperty;

public class ClientKafka {
    private static final String BOOTSTRAP_SERVERS = getProperty("server", "localhost:9092");
    private static final String TOPIC = getProperty("topic", "001");
    private static final String GROUP_ID = getProperty("user", "soro");
    private static ClientKafka INSTANCE = null;
    private final KafkaConsumer<String, String> consumer ;
    private final KafkaProducer<String, String> producer ;

    private ClientKafka() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("group.id", GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(TOPIC));
        producer = new KafkaProducer<>(props);
    }

    public void envoyer_Message(String message) {
        ProducerRecord<String, String> cree_message = new ProducerRecord<>(TOPIC, GROUP_ID, message);
        producer.send(cree_message);
        producer.flush();
    }

    public ConsumerRecords<String, String> consumer_message() {
        return consumer.poll(100);
    }

    public void commitAsynch() {
        consumer.commitAsync();
    }

    static ClientKafka getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ClientKafka();
        }
        return INSTANCE ;
    }
}
