package com.orioninc.agentservice.consumer;

import com.orioninc.avro.MessageSchema;
import java.util.concurrent.ExecutionException;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  private static final String TOPIC_NAME = "message-topic";
  private static final String CONSUMER_GROUP_ID = "agent-consumer-group";

  @KafkaListener(
      topics = {TOPIC_NAME},
      containerFactory = "kafkaMessageListenerContainerFactory",
      groupId = CONSUMER_GROUP_ID)
  public void consume(ConsumerRecord<String, GenericRecord> record)
      throws ExecutionException, InterruptedException {
    if (record.value() instanceof MessageSchema) {
      // бла бла передать сообщение агенту


    }
  }

}
