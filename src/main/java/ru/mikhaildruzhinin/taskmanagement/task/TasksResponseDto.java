package ru.mikhaildruzhinin.taskmanagement.task;

import java.util.List;

public record TasksResponseDto(List<TaskResponseDto> tasks) {
}
