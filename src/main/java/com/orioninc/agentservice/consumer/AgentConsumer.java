package com.orioninc.agentservice.consumer;

import com.orioninc.agentservice.controller.AgentController;
import com.orioninc.agentservice.entity.Agent;
import com.orioninc.agentservice.repository.AgentRepository;
import com.orioninc.avro.AgentSchema;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AgentConsumer {

  private static final String TOPIC_NAME = "agent-topic";
  private static final String CONSUMER_GROUP_ID = "agent-consumer-group";

  final AgentController agentController;
  final AgentRepository agentRepository;

  public AgentConsumer(AgentController agentController,
      AgentRepository agentRepository) {
    this.agentController = agentController;
    this.agentRepository = agentRepository;
  }

  @KafkaListener(
      topics = {TOPIC_NAME},
      containerFactory = "kafkaListenerContainerFactory",
      groupId = CONSUMER_GROUP_ID)
  public void consume(ConsumerRecord<String, GenericRecord> record)
      throws ExecutionException, InterruptedException {
    if (record.value() instanceof AgentSchema agentSchema) {
      if (agentSchema.getRequestType().toString().equals("updateAvailability")) {
        Optional<Agent> findAgent = agentRepository.findById(agentSchema.getId());
        if (findAgent.isPresent()) {
          Agent agentForUpdate = findAgent.get();
          agentForUpdate.setAvailable(agentForUpdate.getAvailable());
          agentController.updateAgent(agentForUpdate);
        }
      }
    }
  }

}