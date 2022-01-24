package com.orioninc.agentservice.service;

import com.orioninc.agentservice.entity.Agent;
import com.orioninc.agentservice.repository.AgentRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

  final AgentRepository agentRepository;

  public AgentService(AgentRepository agentRepository) {
    this.agentRepository = agentRepository;
  }

  public Agent registerAgent(String name, Boolean status, String skill) {
    return this.agentRepository.save(new Agent(name, status, skill));
  }

  public Optional<Agent> updateAgent(Agent agent) {
    Optional<Agent> searchedAgent = agentRepository.findById(agent.getId());
    if (searchedAgent.isPresent()) {
      Agent foundAgent = searchedAgent.get();
      foundAgent.setId(agent.getId());
      foundAgent.setName(agent.getName());
      foundAgent.setStatus(agent.getStatus());
      foundAgent.setSkill(agent.getSkill());
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

}
