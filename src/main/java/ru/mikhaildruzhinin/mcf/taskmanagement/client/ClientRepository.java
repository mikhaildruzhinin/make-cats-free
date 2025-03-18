package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    public Uni<List<ClientDto>> getAll() {
        return find("select id, name, manager.id from Client order by name").project(ClientDto.class).list();
    }

    public Uni<ClientDto> get(Long id) {
        return find("select id, name, manager.id from Client where id = ?1", id)
                .project(ClientDto.class)
                .firstResult();
    }

    public Uni<Integer> update(Long id, String name, Long managerId) {
        return update("name = '%s', manager.id = %d where id = ?1".formatted(name, managerId), id);
    }

    public Uni<Client> findByName(String name) {
        return find("select c from Client c where name = ?1", name).firstResult();
    }
}
