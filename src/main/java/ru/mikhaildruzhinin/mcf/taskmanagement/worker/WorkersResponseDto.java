package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import java.util.List;

public record WorkersResponseDto(List<WorkerResponseDto> workers) {
}
