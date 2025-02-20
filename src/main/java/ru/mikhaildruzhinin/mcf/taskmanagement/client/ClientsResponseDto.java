package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ClientsResponseDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) List<ClientResponseDto> clients
) {
}
