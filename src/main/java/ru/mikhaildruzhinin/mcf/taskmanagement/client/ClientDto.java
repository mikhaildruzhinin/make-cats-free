package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ClientDto(Long id, String name, @ProjectedFieldName("manager.id") Long managerId) {
}
