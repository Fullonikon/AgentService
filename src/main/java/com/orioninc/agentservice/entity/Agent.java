package com.orioninc.agentservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agents")
public class Agent {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name")
  String name;

  @Column(name = "status")
  Boolean status;

  @Column(name = "skill")
  String skill;

  @Column(name = "language")
  String language;

  @Column(name = "isAvailable")
  Boolean isAvailable;

  public Agent(String name, Boolean status, String skill, String language, Boolean isAvailable) {
    this.name = name;
    this.status = status;
    this.skill = skill;
    this.language = language;
    this.isAvailable = isAvailable;
  }

  public Agent(
      Long id, String name, Boolean status, String skill, String language, Boolean isAvailable) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.skill = skill;
    this.language = language;
    this.isAvailable = isAvailable;
  }

  public Agent() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getSkill() {
    return skill;
  }

  public void setSkill(String skill) {
    this.skill = skill;
  }

  public Boolean getAvailable() {
    return isAvailable;
  }

  public void setAvailable(Boolean available) {
    isAvailable = available;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
