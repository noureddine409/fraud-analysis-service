package ma.adria.eventanalyser.service.impl;

import lombok.RequiredArgsConstructor;
import ma.adria.eventanalyser.datacache.CacheStore;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;
import ma.adria.eventanalyser.service.RuleConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RuleConfigServiceImpl implements RuleConfigService {

    private final RestTemplate restTemplate;
    private final CacheStore<RuleConfig> configCache;

    @Value("${config.server.url}")
    private String configServerUrl;

    @Override
    public RuleConfig getRuleConfig(String ruleCode) throws RuleConfigNotFoundException {
        // Check the cache first
        RuleConfig config = configCache.get(ruleCode);
        if (config != null) {
            return config;
        }

        // If not in cache, fetch from the server
        String urlWithQueryParam = configServerUrl + "?code={code}";
        ResponseEntity<RuleConfig> response = restTemplate.getForEntity(urlWithQueryParam, RuleConfig.class, ruleCode);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            config = response.getBody();
            // Store the fetched config in the cache
            configCache.put(ruleCode, config);
            return config;
        } else {
            throw new RuleConfigNotFoundException("RuleConfig not found for code: {}", ruleCode);
        }
    }
}
