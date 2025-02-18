package ru.mikhaildruzhinin.taskmanagement.client;

import java.util.List;

public record ClientsResponseDto(List<ClientResponseDto> clients) {
}
