package com.xyz.redirect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.xyz.redirect.dto.KafkaMessage;
import com.xyz.redirect.entity.URL;
import com.xyz.redirect.event.KafkaConsumerConfig;
import com.xyz.redirect.repository.UrlRepo;
import com.xyz.redirect.utility.KafkaMessageType;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.AllArgsConstructor;

@Service
@Slf4j
@Data
@AllArgsConstructor
public class KafkaService{
    private final String topicName = "irembo-interview";

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Autowired
    private UrlRepo urlRepo;

    public void sendMessage(KafkaMessage msg) {
        log.info(msg.toString());
        kafkaTemplate.send(topicName, msg);
    }

    public void sendPromisableMessage(KafkaMessage message) {
            
        ListenableFuture<SendResult<String, KafkaMessage>> future = 
          kafkaTemplate.send(topicName, message);
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, KafkaMessage>>() {
    
            @Override
            public void onSuccess(SendResult<String, KafkaMessage> result) {
                System.out.println("Sent message=[" + message + 
                  "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" 
                  + message + "] due to : " + ex.getMessage());
            }
        });
    }

    //    @KafkaListener(topics = "topic1, topic2", groupId = "foo") //listen to multiple message
    @KafkaListener(topics = "irembo-interview", groupId = "heavy-consumer")
    public void listenGroupFoo(KafkaMessage message) {
        System.out.println("Received Message in group heavy-consumer: " + message);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage>
    filterKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new KafkaConsumerConfig().consumerFactory());
        factory.setRecordFilterStrategy(
        record -> record.value().getType() != KafkaMessageType.FAKE_URL_GENERATED);
        return factory;
    }

    @KafkaListener(topics = "irembo-interview", containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(KafkaMessage message) {
        log.info(message.toString());
        URL url = message.getUrl();
        url.setRefId(message.getUrl().getId());
        this.urlRepo.save(url);
    }
}
