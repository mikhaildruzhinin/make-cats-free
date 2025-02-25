package ru.mikhaildruzhinin.mcf.taskmanagement.worker;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class WorkerRepository implements PanacheRepository<Worker> {

    public Uni<List<WorkerDto>> getAll() {
        return find("select id, name from Worker").project(WorkerDto.class).list();
    }
}
