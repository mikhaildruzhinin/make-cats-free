package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientResponseDto;

public record TaskResponseDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String title,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String description,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @JsonBackReference
        ClientResponseDto client
) {
}
