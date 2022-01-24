package com.orioninc.agentservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableBinding(Processor.class)
public class AgentServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AgentServiceApplication.class, args);

    /*Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", "localhost:9095");
    properties.setProperty("acks", "1");
    properties.setProperty("retries", "10");

    properties.setProperty("key.serializer", StringSerializer.class.getName());
    properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
    properties.setProperty("schema.registry.url", "http://localhost:8080");

    KafkaProducer<String, Agent> kafkaProducer = new KafkaProducer<String, Agent>(properties);
    final String AGENT_TOPIC = "agentTopic";

    Agent agent = Agent.newBuilder().setId(1L).setName("name").setStatus(true).setSkill("pepega")
        .build();

    ProducerRecord<String, Agent> producerRecord = new ProducerRecord<>(AGENT_TOPIC, agent);

    kafkaProducer.send(producerRecord);

    kafkaProducer.flush();
    kafkaProducer.close();*/


  }

}
