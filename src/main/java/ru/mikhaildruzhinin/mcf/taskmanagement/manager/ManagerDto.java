package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ManagerDto(Long id, String name) {
}
