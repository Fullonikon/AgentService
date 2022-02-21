package com.orioninc.agentservice.service;

import com.orioninc.agentservice.entity.Agent;
import com.orioninc.agentservice.entity.User;
import com.orioninc.agentservice.repository.AgentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

  final AgentRepository agentRepository;

  public AgentService(AgentRepository agentRepository) {
    this.agentRepository = agentRepository;
  }

  public Agent registerAgent(
      String name,
      Boolean status,
      String language,
      String username,
      String password,
      String role) {
    return this.agentRepository.save(
        new Agent(name, status, language, true, username, password, role));
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

  public Iterable<User> findAll() {
    return this.agentRepository.findAll().stream().map(User::of).collect(Collectors.toList());
  }

  public Agent findByUsername(String username) {
    return agentRepository.findByUsername(username);
  }

  public Optional<Agent> addSkill(Long id, String skill) {
    Optional<Agent> searchedAgent = agentRepository.findById(id);
    if (searchedAgent.isPresent()) {
      Agent foundAgent = searchedAgent.get();
      ArrayList<String> skills = (ArrayList<String>) foundAgent.getSkill();
      skills.add(skill);
      foundAgent.setSkill(skills);
      agentRepository.save(foundAgent);
      return Optional.of(foundAgent);
    }
    return Optional.empty();
  }

  /*public Agent agentSchemaRecordToAgent(AgentSchema agentSchema) {
    return new Agent(
        agentSchema.getId(),
        agentSchema.getName().toString(),
        agentSchema.getStatus(),
        agentSchema.getSkill(),
        agentSchema.getLanguage().toString(),
        agentSchema.getIsAvailable());
  }*/

}
