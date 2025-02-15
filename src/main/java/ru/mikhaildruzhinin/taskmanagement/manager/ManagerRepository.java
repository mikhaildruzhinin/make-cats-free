package ru.mikhaildruzhinin.taskmanagement.manager;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class ManagerRepository implements PanacheRepository<Manager> {

    @Transactional
    public boolean update(Manager newManager) {
        Optional<Manager> optionalManager = findByIdOptional(newManager.getId());
        Optional<Boolean> isUpdated = optionalManager.map(manager -> {
            manager.setName(newManager.getName());
            manager.setClients(newManager.getClients());
            return true;
        });
        return isUpdated.orElse(false);
    }
}
