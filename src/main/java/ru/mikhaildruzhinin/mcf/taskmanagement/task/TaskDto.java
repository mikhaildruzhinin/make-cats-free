package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record TaskDto(Long id, String title, String description, Integer price) {
}
