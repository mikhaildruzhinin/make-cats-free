package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record TaskRequestDto(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank()
        String title,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank()
        String description,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank()
        Long clientId
) {
}
