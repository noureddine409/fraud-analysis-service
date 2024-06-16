package ma.adria.eventanalyser.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up ModelMapper.
 * ModelMapper is used for mapping between objects in a type-safe manner.
 */
@Configuration
public class MappingConfig {

    /**
     * Creates a bean for ModelMapper with strict matching strategy.
     *
     * @return an instance of ModelMapper configured with strict matching strategy
     */
    @Bean
    public ModelMapper modelMapper() {
        final var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
