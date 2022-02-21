package com.orioninc.agentservice.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record User(@JsonProperty long id, @JsonProperty String name, @JsonProperty String username, @JsonProperty String role, @JsonProperty List<String> skills) {

  @JsonCreator
  public User{
  }

  public static User of(Agent agent){
    return new User(agent.getId(), agent.getName(), agent.getUsername(), agent.getRole(), agent.getSkill());
  }

}