package com.orioninc.agentservice.repository;

import com.orioninc.agentservice.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

}
