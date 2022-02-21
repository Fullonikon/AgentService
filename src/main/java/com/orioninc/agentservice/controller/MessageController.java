package com.orioninc.agentservice.controller;

import com.orioninc.avro.MessageSchema;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agentMessage")
public class MessageController {

  private static final String MESSAGE_TOPIC_NAME = "message-topic";

  final KafkaTemplate<String, MessageSchema> messageKafkaTemplate;

  public MessageController(
      KafkaTemplate<String, MessageSchema> messageKafkaTemplate) {
    this.messageKafkaTemplate = messageKafkaTemplate;
  }

  @PostMapping("/sendMessage")
  public ResponseEntity<String> sendMessage(
      @RequestParam String customerId,
      @RequestParam String agentId,
      @RequestParam String message) throws ExecutionException, InterruptedException {

    MessageSchema messageSent = MessageSchema.newBuilder()
        .setRequestType("forCustomer")
        .setMessage("message")
        .setCustomerId(customerId)
        .setAgentId(agentId).build();

    ProducerRecord<String, MessageSchema> producerRecord = new ProducerRecord<>(MESSAGE_TOPIC_NAME, messageSent);

    var res = messageKafkaTemplate.send(producerRecord).get();
    messageKafkaTemplate.flush();
    System.out.println(res);

    return ResponseEntity.ok(message);
  }
}
