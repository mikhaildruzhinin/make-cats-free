package ru.mikhaildruzhinin.mcf.taskmanagement.manager;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ManagerRepository implements PanacheRepository<Manager> {

    public Uni<List<ManagerDto>> getAll() {
        return find("select id, name from Manager order by name").project(ManagerDto.class).list();
    }

    public Uni<ManagerDto> get(Long id) {
        return find("select id, name from Manager where id = ?1", id)
                .project(ManagerDto.class)
                .firstResult();
    }

    public Uni<Manager> find(Long id) {
        return find("select m from Manager m left join fetch m.workers w left join fetch m.clients c where m.id = ?1", id).firstResult();
    }

    public Uni<Integer> update(Long id, String name) {
        return update("name = '%s' where id = ?1".formatted(name), id);
    }

    public Uni<Manager> findByName(String name) {
        return find("select m from Manager m where name = ?1", name).firstResult();
    }
}
