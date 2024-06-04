package ma.adria.eventanalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EventAnalyserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventAnalyserApplication.class, args);
    }

}
