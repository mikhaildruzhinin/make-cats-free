package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerResponseDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.TaskResponseDto;

import java.util.Set;

public record WorkerResponseDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String name,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonBackReference
        ManagerResponseDto manager,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonManagedReference
        Set<TaskResponseDto> tasks
) {
}
