package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import org.mapstruct.*;
import ru.mikhaildruzhinin.mcf.taskmanagement.MappingConfig;
import ru.mikhaildruzhinin.mcf.taskmanagement.manager.ManagerMapper;
import ru.mikhaildruzhinin.mcf.taskmanagement.task.TaskMapper;

import java.util.List;
import java.util.Set;

@Mapper(uses = {TaskMapper.class, ManagerMapper.class}, config = MappingConfig.class)
public interface WorkerMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "manager", expression = "java(null)")
    @Mapping(target = "tasks", expression = "java(Set.of())")
    Worker toEntity(WorkerRequestDto dto);

    @Named("WorkerSetIgnoreManagerTasks")
    @IterableMapping(qualifiedByName = "WorkerIgnoreManagerTasks")
    Set<WorkerResponseDto> toDtoWorkerSetIgnoreManagerTasks(Set<Worker> workers);

    @Named("WorkerIgnoreManagerTasks")
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    WorkerResponseDto toDtoWorkerIgnoreManagerTasks(Worker worker);

    @IterableMapping(qualifiedByName = "Worker")
    List<WorkerResponseDto> toDtoList(List<Worker> worker);

    @Named("Worker")
    @Mapping(target = "manager", qualifiedByName = "ManagerIgnoreWorkersClients")
    @Mapping(target = "tasks", qualifiedByName = "TaskSetIgnoreWorkerClient")
    WorkerResponseDto toDto(Worker worker);
}
