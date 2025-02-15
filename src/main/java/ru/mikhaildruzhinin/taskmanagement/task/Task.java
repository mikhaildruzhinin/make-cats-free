package ru.mikhaildruzhinin.taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public record Task(@Id @GeneratedValue @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
                   String title,
                   String description) {}
