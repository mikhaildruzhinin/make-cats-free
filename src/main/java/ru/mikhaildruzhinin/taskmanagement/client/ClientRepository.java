package ru.mikhaildruzhinin.taskmanagement.client;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    @Transactional
    public boolean update(Client newClient) {
        Optional<Client> optionalClient = findByIdOptional(newClient.getId());
        Optional<Boolean> isUpdated = optionalClient.map(client -> {
            client.setId(newClient.getId());
            client.setName(newClient.getName());
            client.setTasks(newClient.getTasks());
            return true;
        });
        return isUpdated.orElse(false);
    }
}
