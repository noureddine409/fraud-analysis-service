package ma.adria.eventanalyser.config;


import ma.adria.eventanalyser.datacache.CacheStore;
import ma.adria.eventanalyser.datacache.impl.InMemoryCacheStore;
import ma.adria.eventanalyser.service.RuleConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuration class for setting up cache-related beans.
 */
@Configuration
public class CacheConfig {

    /**
     * Creates a bean for an in-memory cache store that stores rule configurations.
     * The cache entries expire after 10 minutes.
     *
     * @return a CacheStore instance configured to store RuleConfigService.RuleConfig objects.
     */
    @Bean
    public CacheStore<RuleConfigService.RuleConfig> ruleConfigCacheStore() {
        return new InMemoryCacheStore<>(10, TimeUnit.MINUTES);
    }
}
