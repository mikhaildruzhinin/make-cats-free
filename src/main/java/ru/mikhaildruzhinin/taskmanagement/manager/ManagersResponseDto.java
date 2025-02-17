package ru.mikhaildruzhinin.taskmanagement.manager;

import java.util.List;

public record ManagersResponseDto(List<ManagerResponseDto> managers) {
}
