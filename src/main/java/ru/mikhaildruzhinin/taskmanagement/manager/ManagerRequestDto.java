package ru.mikhaildruzhinin.taskmanagement.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record ManagerRequestDto(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank
        @Schema(defaultValue = "John Doe")
        String name
) {
}
