package ru.mikhaildruzhinin.taskmanagement.client;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.Optional;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    @Transactional
    public Client update(Client newClient) {
        Optional<Client> optionalClient = findByIdOptional(newClient.getId());
        optionalClient.map(client -> {
            persist(newClient);
            return newClient;
        });
        return optionalClient.orElseThrow((NotFoundException::new));
    }
}
