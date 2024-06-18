package ma.adria.eventanalyser.utils;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.RuleConfigService;
import ma.adria.eventanalyser.service.RuleConfigService.RuleConfig;

import java.util.List;

@UtilityClass
@Slf4j
public class RuleConfigUtils {

    public static RuleConfig.Parameter getParam(List<RuleConfig.Parameter> parameters,
                                                String param, String bankCode) {
        return parameters.stream()
                .filter(p -> param.equals(p.getCode()) && bankCode.equals(p.getCodeBank()))
                .findFirst()
                .orElse(null);
    }

    public RuleConfigService.RuleConfig getRuleConfig(RuleConfigService ruleConfigService, String ruleCode, Long eventId) {
        try {
            RuleConfigService.RuleConfig ruleConfig = ruleConfigService.getRuleConfig(ruleCode);
            log.info("RuleConfig retrieved for ruleCode {}: {}", ruleCode, ruleConfig);
            return ruleConfig;
        } catch (RuleConfigNotFoundException e) {
            log.error("RuleConfig not found for ruleCode {} and eventId {}", ruleCode, eventId, e);
            return null;
        }
    }

    public FraudRule.FraudDetectionResult buildResult(Long eventId, String ruleName, boolean isFraud, String reason) {
        return FraudRule.FraudDetectionResult.builder()
                .ruleName(ruleName)
                .eventId(eventId)
                .isFraud(isFraud)
                .reason(reason)
                .build();
    }

    public RuleConfig.Parameter getParam(List<RuleConfig.Parameter> parameters, String param, String bankCode, String segment) {
        return parameters.stream()
                .filter(p -> param.equals(p.getCode())
                        && bankCode.equals(p.getCodeBank())
                        && segment.equals(p.getSegment()))
                .findFirst()
                .orElse(null);
    }

}
