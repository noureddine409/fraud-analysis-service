package ma.adria.eventanalyser.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;

import java.util.Map;


/**
 * Service interface for managing and retrieving rule configurations.
 */
public interface RuleConfigService {

    /**
     * Retrieves the configuration for a specific rule identified by its rule code.
     *
     * @param ruleCode the unique code identifying the rule.
     * @return the configuration of the rule.
     * @throws RuleConfigNotFoundException if the rule configuration is not found.
     */
    RuleConfig getRuleConfig(String ruleCode) throws RuleConfigNotFoundException;

    /**
     * Class representing the configuration for a specific rule.
     */
    @Data
    @AllArgsConstructor
    @Builder
    class RuleConfig {
        private String ruleCode;
        private Map<String, Object> params;
    }
}
