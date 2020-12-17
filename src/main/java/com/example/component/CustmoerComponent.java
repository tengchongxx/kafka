package com.example.component;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustmoerComponent {

    //统一定义消费失败时的异常处理方法
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareListenerErrorHandler(){
        return (message, e, consumer) -> {
            System.out.println("消费失败："+message.getPayload());
            return null;
        };
    }

    @KafkaListener(topics ={"demo3"})
    public void onMessage1(String record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("转发过来的值为"+record);
        //System.out.println("收到转发过来得消息："+record.topic()+"-"+record.partition()+"-"+record.value());
    }

    //消费者指定监听的topic partition offset
   /* @KafkaListener(id="custmoer2",groupId="listen-group",topicPartitions = {@TopicPartition(topic="demo2",partitionOffsets =
    @PartitionOffset(partition = "0",initialOffset = "8"))})
    public void onMessage2(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }*/

    //测试批量消费
    @KafkaListener(id="custmoer3" ,groupId="listen-group2",topics="demo2", /*errorHandler="consumerAwareListenerErrorHandler",*/
            containerFactory = "filterConsumer")
    @SendTo("demo3")
    public String  onMessage3(ConsumerRecord records){
        //throw new RuntimeException("简单消费-模拟异常");
        System.out.println("批量消费次数");
        System.out.println(records);
        System.out.println(records.getClass());
        System.out.println(records.value());
        return (String)records.value();
         /* for(ConsumerRecord<?,?> record:records){
              System.out.println("批量消费"+record.value());
          }*/

    }

   /* //测试消息在topic之间转发
    @KafkaListener(topics="demo2")
    @SendTo("demo3")
    public List onMessage4(List record){
        return record;
    }*/

}
