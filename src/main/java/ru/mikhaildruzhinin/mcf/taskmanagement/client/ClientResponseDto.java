package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.TaskResponseDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerResponseDto;

import java.util.Set;

public record ClientResponseDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String name,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonManagedReference
        Set<TaskResponseDto> tasks,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonBackReference
        ManagerResponseDto manager
) {
}
