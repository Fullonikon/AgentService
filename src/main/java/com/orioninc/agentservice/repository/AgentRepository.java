package com.orioninc.agentservice.repository;

import com.orioninc.agentservice.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

  Agent findByUsername(String username);

}
