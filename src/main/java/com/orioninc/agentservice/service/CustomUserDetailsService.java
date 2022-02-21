package com.orioninc.agentservice.service;

import static java.util.Objects.isNull;


import com.orioninc.agentservice.detail.CustomUserDetails;
import com.orioninc.agentservice.entity.Agent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
  private final AgentService agentService;

  public CustomUserDetailsService(AgentService agentService) {
    this.agentService = agentService;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Agent agent = agentService.findByUsername(username);
    return isNull(agent) ? null : CustomUserDetails.fromUserEntityToCustomUserDetails(agent);
  }
}
