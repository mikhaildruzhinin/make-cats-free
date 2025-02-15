package ru.mikhaildruzhinin.taskmanagement.client;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    @Transactional
    public Client update(Client newClient) {
        Optional<Client> optionalClient = findByIdOptional(newClient.id());
        optionalClient.map(client -> {
            persist(newClient);
            return newClient;
        });
        return optionalClient.orElseThrow(() -> new RuntimeException("Not Found"));
    }
}
