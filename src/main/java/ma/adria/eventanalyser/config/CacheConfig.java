package ma.adria.eventanalyser.config;


import com.neovisionaries.i18n.CountryCode;
import ma.adria.eventanalyser.datacache.CacheStore;
import ma.adria.eventanalyser.datacache.impl.InMemoryCacheStore;
import ma.adria.eventanalyser.service.RuleConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuration class for setting up cache-related beans.
 */
@Configuration
public class CacheConfig {


    @Value("${cache.rule-config.expire-duration-minutes}")
    private int ruleConfigCacheExpireMinutes;

    @Value("${cache.ip-to-country.expire-duration-minutes}")
    private int ipToCountryCacheExpireMinutes;

    /**
     * Creates a bean for an in-memory cache store that stores rule configurations.
     * The cache entries expire after 10 minutes.
     *
     * @return a CacheStore instance configured to store RuleConfigService.RuleConfig objects.
     */
    @Bean
    public CacheStore<RuleConfigService.RuleConfig> ruleConfigCacheStore() {
        return new InMemoryCacheStore<>(ruleConfigCacheExpireMinutes, TimeUnit.MINUTES);
    }

    /**
     * Creates a bean for an in-memory cache store that stores country codes from IP lookups.
     * The cache entries expire after 10 minutes.
     *
     * @return a CacheStore instance configured to store CountryCode objects.
     */
    @Bean
    public CacheStore<CountryCode> ipToCountryCodeCacheStore() {
        return new InMemoryCacheStore<>(ipToCountryCacheExpireMinutes, TimeUnit.MINUTES);
    }
}
