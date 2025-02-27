package ru.mikhaildruzhinin.mcf.taskmanagement.client;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    public Uni<List<ClientDto>> getAll() {
        return find("select id, name from Client order by name").project(ClientDto.class).list();
    }
}
