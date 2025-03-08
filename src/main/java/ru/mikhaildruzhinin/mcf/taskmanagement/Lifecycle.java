package ru.mikhaildruzhinin.mcf.taskmanagement;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.VertxContextSupport;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import ru.mikhaildruzhinin.mcf.taskmanagement.user.UserRepository;

@ApplicationScoped
public class Lifecycle {

    private static final Logger logger = Logger.getLogger(Lifecycle.class);

    @Inject
    UserRepository userRepository;

    // TODO create CLI command
    void onStart(@Observes StartupEvent event) throws Throwable {
        String errorMessage = "User with an admin role is not found. Use CLI to create one.";
        VertxContextSupport.subscribeAndAwait(() ->
                Panache.withSession(() -> userRepository.findByRole("admin")
                        .onItem().ifNull().failWith(new RuntimeException(errorMessage)))
                        .onFailure().invoke(f -> logger.error(errorMessage))
        );
    }
}
