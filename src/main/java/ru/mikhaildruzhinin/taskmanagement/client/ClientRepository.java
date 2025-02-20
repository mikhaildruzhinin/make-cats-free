package ru.mikhaildruzhinin.taskmanagement.client;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import ru.mikhaildruzhinin.taskmanagement.manager.Manager;

import java.util.Optional;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    @Transactional
    public boolean update(Long id, ClientRequestDto dto, Manager manager) {
        Optional<Client> optionalClient = findByIdOptional(id);
        Optional<Boolean> isUpdated = optionalClient.map(client -> {
            client.setName(dto.name());
            client.setManager(manager);
            // TODO tasks
            return true;
        });
        return isUpdated.orElse(false);
    }

    @Transactional
    public void save(Client client, Manager manager) {
        client.setManager(manager);
        persist(client);
    }
}
