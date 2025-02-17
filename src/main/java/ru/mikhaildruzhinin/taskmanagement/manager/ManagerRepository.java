package ru.mikhaildruzhinin.taskmanagement.manager;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class ManagerRepository implements PanacheRepository<Manager> {

    @Transactional
    public boolean update(Long id, ManagerDto managerDto) {
        Optional<Manager> optionalManager = findByIdOptional(id);
        Optional<Boolean> isUpdated = optionalManager.map(manager -> {
            manager.setName(managerDto.name());
            return true;
        });
        return isUpdated.orElse(false);
    }
}
