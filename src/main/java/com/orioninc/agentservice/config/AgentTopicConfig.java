package com.orioninc.agentservice.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class AgentTopicConfig {

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaAgentConfig.BOOTSTRAP_SERVERS);
    return new KafkaAdmin(configs);
  }

  /*@Bean
  public NewTopic topic() {
    return new NewTopic(AGENT_TOPIC, 1, (short) 1);
  }*/

}
