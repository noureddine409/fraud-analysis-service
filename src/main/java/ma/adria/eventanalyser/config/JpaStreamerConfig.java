package ma.adria.eventanalyser.config;


import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaStreamerConfig {

    @Bean
    public JPAStreamer jpaStreamer(EntityManagerFactory entityManagerFactory) {
        return JPAStreamer.createJPAStreamerBuilder(entityManagerFactory).build();
    }
}
