package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class WorkerRepository implements PanacheRepository<Worker> {

    public Uni<List<WorkerDto>> getAll() {
        return find("select id, name, manager.id from Worker order by name").project(WorkerDto.class).list();
    }

    public Uni<WorkerDto> get(Long id) {
        return find("select id, name, manager.id from Worker where id = ?1", id)
                .project(WorkerDto.class)
                .firstResult();
    }

    public Uni<Integer> update(Long id, String name, Long managerId) {
        return update("name = '%s', manager.id = %d where id = ?1".formatted(name, managerId), id);
    }
}
