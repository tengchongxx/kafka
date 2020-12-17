package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // 发送消息
    @GetMapping("/kafka/normal")
    public String sendMessage1() {
        kafkaTemplate.send("demo2", "1");
        kafkaTemplate.send("demo2", "2");
        kafkaTemplate.send("demo2", "3");
        kafkaTemplate.send("demo2", "4");
        kafkaTemplate.send("demo2", "5");
        kafkaTemplate.send("demo2", "6");
        kafkaTemplate.send("demo2", "7");
        kafkaTemplate.send("demo2", "8");
        return "发送成功";

    }


    //带回调的生产者
   @GetMapping("/kafka/callback")
    public String sendMessage2(){
        kafkaTemplate.send("demo2","杨锦漾").addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("发送消息失败"+throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送消息成功topic:"+result.getRecordMetadata().topic()+"分区:"+result.getRecordMetadata().partition()+"offset:"+
                        result.getRecordMetadata().offset());
            }
        });
   return "结束";}

   //kafka事务提交
    @GetMapping("/kafka/transaction")
    public void sendMessage3(){
        //带事务的提交 ，抛错之后原来的消息不能发送
        /*kafkaTemplate.executeInTransaction(operations ->{
            operations.send("demo3","小丑");
            throw new RuntimeException("fail");
        } );*/
        //不带事务提交 ，抛错之后原来的消息正常发送
        kafkaTemplate.send("demo2","大丑不是一个好孩子，也不是一个坏孩子");
        throw new RuntimeException("fail2");
    }


}
