package com.orioninc.agentservice.controller;

import com.orioninc.agentservice.entity.Agent;
import com.orioninc.agentservice.entity.User;
import com.orioninc.agentservice.service.AgentService;
import com.orioninc.avro.AgentSchema;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/agent")
public class AgentController {

  final AgentService agentService;
  final KafkaTemplate<String, AgentSchema> kafkaTemplate;
  final String AGENT_TOPIC = "agent-topic";

  public AgentController(
      AgentService agentService, KafkaTemplate<String, AgentSchema> kafkaTemplate) {
    this.agentService = agentService;
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping("/registerAgent")
  public ResponseEntity<Agent> registerAgent(@RequestBody Agent agent)
      throws ExecutionException, InterruptedException {
    Agent savedAgent =
        agentService.registerAgent(
            agent.getName(),
            agent.getStatus(),
            agent.getLanguage(),
            agent.getUsername(),
            agent.getPassword(),
            agent.getRole());
    AgentSchema agentSent =
        AgentSchema.newBuilder()
            .setRequestType("register")
            .setId(savedAgent.getId())
            .setName(savedAgent.getName())
            .setStatus(savedAgent.getStatus())
            .setSkill(new ArrayList<>())
            .setLanguage(savedAgent.getLanguage())
            .setIsAvailable(savedAgent.getAvailable())
            .build();

    ProducerRecord<String, AgentSchema> producerRecord =
        new ProducerRecord<>(AGENT_TOPIC, agentSent);

    var res = kafkaTemplate.send(producerRecord).get();
    kafkaTemplate.flush();
    System.out.println(res);

    return ResponseEntity.ok(savedAgent);
  }

  @GetMapping("/addSkill")
  public ResponseEntity<Agent> addSkill(@RequestParam Long id, @RequestParam String skill)
      throws ExecutionException, InterruptedException {
    Optional<Agent> agent = agentService.addSkill(id, skill);
    if(agent.isPresent()){
      return updateAgent(agent.get());
    }
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/updateAgent")
  public ResponseEntity<Agent> updateAgent(@RequestBody Agent agent)
      throws ExecutionException, InterruptedException {
    Optional<Agent> updateAgent = agentService.updateAgent(agent);
    if (updateAgent.isPresent()) {
      Agent updatedAgent = updateAgent.get();
      CharSequence[] ll =
          updatedAgent.getSkill().toArray(new CharSequence[updatedAgent.getSkill().size()]);
      List<CharSequence> bb = Arrays.stream(ll).toList();
      AgentSchema agentSent =
          AgentSchema.newBuilder()
              .setRequestType("update")
              .setId(updatedAgent.getId())
              .setName(updatedAgent.getName())
              .setStatus(updatedAgent.getStatus())
              .setSkill(bb)
              .setLanguage(updatedAgent.getLanguage())
              .setIsAvailable(updatedAgent.getAvailable())
              .build();

      ProducerRecord<String, AgentSchema> producerRecord =
          new ProducerRecord<>(AGENT_TOPIC, agentSent);

      var res = kafkaTemplate.send(producerRecord).get();
      System.out.println(res);
      return ResponseEntity.ok(updatedAgent);
    }
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/unregisterAgent")
  public ResponseEntity deleteAgent(@RequestParam Long id)
      throws ExecutionException, InterruptedException {
    Optional<Agent> agent = agentService.getAgent(id);
    if (agent.isPresent()) {
      Agent agentToDelete = agent.get();
      AgentSchema agentSent =
          AgentSchema.newBuilder()
              .setRequestType("delete")
              .setId(agentToDelete.getId())
              .setName(agentToDelete.getName())
              .setStatus(agentToDelete.getStatus())
              .setSkill(new ArrayList<>())
              .build();

      ProducerRecord<String, AgentSchema> producerRecord =
          new ProducerRecord<>(AGENT_TOPIC, agentSent);

      var res = kafkaTemplate.send(producerRecord).get();
      System.out.println(res);
      agentService.deleteAgent(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/checkStatus")
  public ResponseEntity<Boolean> checkAgentStatus(@RequestParam Long id) {
    Optional<Agent> agent = agentService.getAgent(id);
    return agent
        .map(value -> ResponseEntity.ok(value.getStatus()))
        .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @GetMapping("/findAllAgents")
  public Iterable<User> findAllAgents() {
    return this.agentService.findAll();
  }
}
