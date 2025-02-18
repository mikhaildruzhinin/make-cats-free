package ru.mikhaildruzhinin.taskmanagement.client;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<Client> {

    @Transactional
    public boolean update(Long id, ClientRequestDto dto) {
        Optional<Client> optionalClient = findByIdOptional(id);
        Optional<Boolean> isUpdated = optionalClient.map(client -> {
            client.setName(dto.name());
            // TODO tasks
            // TODO manager
            return true;
        });
        return isUpdated.orElse(false);
    }
}
