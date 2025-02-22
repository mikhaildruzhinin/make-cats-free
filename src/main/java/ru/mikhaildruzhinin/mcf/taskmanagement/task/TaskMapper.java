package ru.mikhaildruzhinin.mcf.taskmanagement.task;

import org.mapstruct.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.MappingConfig;
import ru.mikhaildruzhinin.mcf.taskmanagement.client.ClientMapper;
import ru.mikhaildruzhinin.mcf.taskmanagement.worker.WorkerMapper;

import java.util.List;
import java.util.Set;

@Mapper(uses = {ClientMapper.class, WorkerMapper.class}, config = MappingConfig.class)
public interface TaskMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "client", expression = "java(null)")
    @Mapping(target = "worker", expression = "java(null)")
    Task toEntity(TaskRequestDto dto);

    @Named("TaskSetIgnoreWorkerClient")
    @IterableMapping(qualifiedByName = "TaskIgnoreWorkerClient")
    Set<TaskResponseDto> toDtoTaskSetIgnoreWorker(Set<Task> tasks);

    @Named("TaskIgnoreWorkerClient")
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "client", ignore = true)
    TaskResponseDto toDtoTaskIgnoreWorker(Task task);

    @IterableMapping(qualifiedByName = "Task")
    List<TaskResponseDto> toDtoList(List<Task> tasks);

    @Named("Task")
    @Mapping(target = "client", qualifiedByName = "ClientIgnoreManagerTasks")
    @Mapping(target = "worker", qualifiedByName = "WorkerIgnoreManagerTasks")
    TaskResponseDto toDto(Task task);
}
