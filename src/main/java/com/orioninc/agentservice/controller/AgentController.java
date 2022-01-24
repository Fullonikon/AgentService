package com.orioninc.agentservice.controller;

import com.orioninc.queueservice.avro.AgentSchema;
import com.orioninc.agentservice.entity.Agent;
import com.orioninc.agentservice.service.AgentService;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AgentController {

  final AgentService agentService;
  final KafkaTemplate<String, AgentSchema> kafkaTemplate;
  final String AGENT_TOPIC = "agent-topic";

  public AgentController(AgentService agentService,
      KafkaTemplate<String, AgentSchema> kafkaTemplate) {
    this.agentService = agentService;
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping("/registerAgent")
  public ResponseEntity<Agent> registerAgent(@RequestBody Agent agent)
      throws ExecutionException, InterruptedException {
    Agent savedAgent = agentService.registerAgent(agent.getName(), agent.getStatus(),
        agent.getSkill());
    AgentSchema agentSent = AgentSchema.newBuilder().setRequestType("register")
        .setId(savedAgent.getId())
        .setName(savedAgent.getName()).setStatus(savedAgent.getStatus())
        .setSkill(savedAgent.getSkill())
        .build();

    ProducerRecord<String, AgentSchema> producerRecord = new ProducerRecord<>(AGENT_TOPIC,
        agentSent);

    var res = kafkaTemplate.send(producerRecord).get();
    System.out.println(res);
    return ResponseEntity.ok(savedAgent);
  }

  @PostMapping("/updateAgent")
  public ResponseEntity<Agent> updateAgent(@RequestBody Agent agent)
      throws ExecutionException, InterruptedException {
    Optional<Agent> updateAgent = agentService.updateAgent(agent);
    if(updateAgent.isPresent()){
      Agent updatedAgent = updateAgent.get();
      AgentSchema agentSent = AgentSchema.newBuilder().setRequestType("update")
          .setId(updatedAgent.getId())
          .setName(updatedAgent.getName()).setStatus(updatedAgent.getStatus())
          .setSkill(updatedAgent.getSkill())
          .build();

      ProducerRecord<String, AgentSchema> producerRecord = new ProducerRecord<>(AGENT_TOPIC,
          agentSent);

      var res = kafkaTemplate.send(producerRecord).get();
      System.out.println(res);
      return ResponseEntity.ok(updatedAgent);
    }
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  public ResponseEntity deleteAgent(@RequestParam Long id) {
    agentService.deleteAgent(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/checkStatus")
  public ResponseEntity<Boolean> checkAgentStatus(@RequestParam Long id) {
    return ResponseEntity.ok().build();
  }
}
