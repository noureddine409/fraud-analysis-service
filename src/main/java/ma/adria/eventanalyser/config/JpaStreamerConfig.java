package ma.adria.eventanalyser.config;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up JPAStreamer.
 * JPAStreamer is used for creating fluent API queries over JPA entities.
 */
@Configuration
public class JpaStreamerConfig {

    /**
     * Creates a bean for JPAStreamer using the provided EntityManagerFactory.
     *
     * @param entityManagerFactory the EntityManagerFactory to create JPAStreamer
     * @return an instance of JPAStreamer configured with the EntityManagerFactory
     */
    @Bean
    public JPAStreamer jpaStreamer(EntityManagerFactory entityManagerFactory) {
        return JPAStreamer.createJPAStreamerBuilder(entityManagerFactory).build();
    }
}
