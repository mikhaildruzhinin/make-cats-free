package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ManagerRepository implements PanacheRepository<Manager> {

    public Uni<List<ManagerDto>> getAll() {
        return find("select id, name from Manager").project(ManagerDto.class).list();
    }
}
