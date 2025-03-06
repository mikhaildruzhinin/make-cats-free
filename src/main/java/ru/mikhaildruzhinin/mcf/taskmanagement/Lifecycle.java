package ru.mikhaildruzhinin.mcf.taskmanagement;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

@ApplicationScoped
public class Lifecycle {

    private static final Logger logger = Logger.getLogger(Lifecycle.class);

    // TODO create CLI command
    void onStart(@Observes StartupEvent event) {
        String password = BcryptUtil.bcryptHash("admin");
        logger.info(password);
    }
}
