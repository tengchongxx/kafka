package com.example.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class KafkaConsumer {
    @Autowired
    ConsumerFactory consumerFactory;
    @Autowired
    KafkaTemplate kafkaTemplate;

    @Bean
    public ConcurrentKafkaListenerContainerFactory filterConsumer() {
        ConcurrentKafkaListenerContainerFactory listenerFactory
                = new ConcurrentKafkaListenerContainerFactory();
        listenerFactory.setReplyTemplate(kafkaTemplate);
        listenerFactory.setConsumerFactory(consumerFactory);
        listenerFactory.setAckDiscarded(true);
        listenerFactory.setRecordFilterStrategy(consumerRecord ->
        {
            if (Integer.valueOf(consumerRecord.value().toString()) % 2==0) {
                return false;
            }
            return true;

        });
        return listenerFactory;
    }
}
