package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaApplication {
   /* @Bean
    public KafkaTemplate kafkaTemplateCreate(){
        return new KafkaTemplate();
    }*/
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);

    }
}
