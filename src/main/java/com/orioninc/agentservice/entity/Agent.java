package com.orioninc.agentservice.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "agents")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Agent implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name")
  String name;

  @Column(name = "status")
  Boolean status;

  @Type(type = "list-array")
  @Column(name = "skills", columnDefinition = "text[]")
  private List<String> skills = new ArrayList<>();

  @Column(name = "language")
  String language;

  @Column(name = "isAvailable")
  Boolean isAvailable;

  @Column(name = "username")
  String username;

  @Column(name = "password")
  String password;

  @Column(name = "role")
  String role;

  public Agent(
      String name, Boolean status, List<String> skill, String language, Boolean isAvailable) {
    this.name = name;
    this.status = status;
    this.skills = skill;
    this.language = language;
    this.isAvailable = isAvailable;
  }

  public Agent(
      Long id,
      String name,
      Boolean status,
      List<String> skill,
      String language,
      Boolean isAvailable) {
    this.id = id;
    this.name = name;
    this.status = status;
    this.skills = skill;
    this.language = language;
    this.isAvailable = isAvailable;
  }

  public Agent(
      String name,
      Boolean status,
      List<String> skills,
      String language,
      Boolean isAvailable,
      String username,
      String password,
      String role) {
    this.name = name;
    this.status = status;
    this.skills = skills;
    this.language = language;
    this.isAvailable = isAvailable;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public Agent(
      String name,
      Boolean status,
      String language,
      Boolean isAvailable,
      String username,
      String password,
      String role) {
    this.name = name;
    this.status = status;
    this.language = language;
    this.isAvailable = isAvailable;
    this.username = username;
    this.password = password;
    this.role = role;
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

  public List<String> getSkill() {
    return skills;
  }

  public void setSkill(List<String> skill) {
    this.skills = skill;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
