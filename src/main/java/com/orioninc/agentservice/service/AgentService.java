package com.orioninc.agentservice.service;

import com.orioninc.agentservice.entity.Agent;
import com.orioninc.agentservice.repository.AgentRepository;
import com.orioninc.avro.AgentSchema;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

  final AgentRepository agentRepository;

  public AgentService(AgentRepository agentRepository) {
    this.agentRepository = agentRepository;
  }

  public Agent registerAgent(String name, Boolean status, String skill, String language) {
    return this.agentRepository.save(new Agent(name, status, skill, language, true));
  }

  public Optional<Agent> updateAgent(Agent agent) {
    Optional<Agent> searchedAgent = agentRepository.findById(agent.getId());
    if (searchedAgent.isPresent()) {
      Agent foundAgent = searchedAgent.get();
      foundAgent.setId(agent.getId());
      foundAgent.setName(agent.getName());
      foundAgent.setStatus(agent.getStatus());
      foundAgent.setAvailable(agent.getAvailable());
      foundAgent.setSkill(agent.getSkill());
      foundAgent.setLanguage(foundAgent.getLanguage());
      agentRepository.save(foundAgent);
      return Optional.of(foundAgent);
    }
    return Optional.empty();
  }

  public void deleteAgent(Long id) {
    agentRepository.deleteById(id);
  }

  public Optional<Agent> getAgent(Long id) {
    return Optional.of(agentRepository.getById(id));
  }

  public Agent agentSchemaRecordToAgent(AgentSchema agentSchema) {
    return new Agent(
        agentSchema.getId(),
        agentSchema.getName().toString(),
        agentSchema.getStatus(),
        agentSchema.getSkill().toString(),
        agentSchema.getLanguage().toString(),
        agentSchema.getIsAvailable());
  }

}
