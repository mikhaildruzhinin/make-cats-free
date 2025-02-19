package ru.mikhaildruzhinin.taskmanagement.client;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import ru.mikhaildruzhinin.taskmanagement.manager.Manager;

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

    @Transactional
    public void save(ClientRequestDto clientRequestDto, Optional<Manager> optionalManager) {
        Client client = clientRequestDto.toEntity();
        optionalManager.ifPresent(client::setManager);
        persist(client);
    }
}
