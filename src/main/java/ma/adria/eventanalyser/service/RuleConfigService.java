package ma.adria.eventanalyser.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;

import java.util.List;


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
    @NoArgsConstructor
    @AllArgsConstructor
    class RuleConfig {
        private String code;
        private String name;
        private String description;
        private List<Parameter> parameters;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Parameter {
            private String code;
            private String value;
            private String description;
            private String codeBank;
            private String countryCode;
            private String segment;
        }

    }
}
