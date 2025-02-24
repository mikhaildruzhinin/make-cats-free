package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ManagerRequestDto(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank
        String name
) {
}
