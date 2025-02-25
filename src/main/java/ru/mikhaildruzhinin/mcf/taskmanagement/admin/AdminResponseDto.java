package ru.mikhaildruzhinin.mcf.taskmanagement.admin;

import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerDto;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerDto;

import java.util.List;

public record AdminResponseDto(
        List<ManagerDto> managers,
        List<ClientDto> clients,
        List<WorkerDto> workers
) {
}
