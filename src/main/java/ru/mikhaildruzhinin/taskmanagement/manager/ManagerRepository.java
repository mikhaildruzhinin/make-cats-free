package ru.mikhaildruzhinin.taskmanagement.manager;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import ru.mikhaildruzhinin.taskmanagement.client.Client;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class ManagerRepository implements PanacheRepository<Manager> {

    @Transactional
    public boolean update(Long id, ManagerRequestDto dto, Optional<Set<Client>> clients) {
        Optional<Manager> optionalManager = findByIdOptional(id);
        Optional<Boolean> isUpdated = optionalManager.map(manager -> {
            manager.setName(dto.name());
            manager.addClients(clients.orElse(new HashSet<>()));
            return true;
        });
        return isUpdated.orElse(false);
    }
}
