package ru.mikhaildruzhinin.taskmanagement.client;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mikhaildruzhinin.taskmanagement.manager.Manager;
import ru.mikhaildruzhinin.taskmanagement.task.Task;

import java.util.Optional;
import java.util.Set;

public record ClientResponseDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String name,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonManagedReference
        Set<Task> tasks,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonBackReference
        Optional<Manager> manager
) {
}
