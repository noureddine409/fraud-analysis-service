package ma.adria.eventanalyser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.datacache.CacheStore;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;
import ma.adria.eventanalyser.service.RuleConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RuleConfigServiceImpl implements RuleConfigService {

    private final RestTemplate restTemplate;
    private final CacheStore<RuleConfig> configCache;

    @Value("${config.server.url}")
    private String configServerUrl;

    @Override
    public RuleConfig getRuleConfig(String ruleCode) throws RuleConfigNotFoundException {
        log.info("Fetching RuleConfig for ruleCode: {}", ruleCode);

        // Check the cache first
        RuleConfig config = configCache.get(ruleCode);
        if (config != null) {
            log.info("RuleConfig found in cache for ruleCode: {}", ruleCode);
            return config;
        }

        // If not in cache, fetch from the server
        log.info("RuleConfig not found in cache, fetching from server for ruleCode: {}", ruleCode);
        try {
            ResponseEntity<RuleConfig> response = restTemplate.getForEntity(configServerUrl, RuleConfig.class, ruleCode);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                config = response.getBody();
                // Store the fetched config in the cache
                configCache.put(ruleCode, config);
                log.info("Fetched RuleConfig from server and stored in cache for ruleCode: {}", ruleCode);
                return config;
            } else {
                log.error("RuleConfig not found for ruleCode: {}", ruleCode);
                throw new RuleConfigNotFoundException("RuleConfig not found for code", ruleCode);
            }
        } catch (RestClientException e) {
            log.error("Error occurred while fetching RuleConfig from server for ruleCode: {}", ruleCode, e);
            throw new RuleConfigNotFoundException("Error fetching RuleConfig for code", ruleCode, e);
        }
    }
}
