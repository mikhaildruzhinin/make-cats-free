package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import java.util.List;

public record TasksResponseDto(List<TaskResponseDto> tasks) {
}
