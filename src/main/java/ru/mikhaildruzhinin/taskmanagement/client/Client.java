package ru.mikhaildruzhinin.taskmanagement.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public record Client(@Id @GeneratedValue @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id, String name) {}
