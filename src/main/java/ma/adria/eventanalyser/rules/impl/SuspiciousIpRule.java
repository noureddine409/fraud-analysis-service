package ma.adria.eventanalyser.rules.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.RuleConfigService;
import ma.adria.eventanalyser.utils.RuleConfigUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A fraud detection rule that flags events originated from fraudulent IP addresses.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SuspiciousIpRule implements FraudRule {

    private final RuleConfigService ruleConfigService;

    public static final String RULE_NAME = "Suspicious IP Rule";
    private static final String RULE_CODE = "R-002";
    private static final String FRAUDULENT_IP_ADDRESSES_PARAM = "LIST_FRAUDULENT_IP_ADDRESSES";

    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {
        if (eventDto == null) {
            log.warn("Invalid event data: eventDto is null");
            return RuleConfigUtils.buildResult(null, RULE_NAME, false, "eventDto is null");
        }


        final Long eventId = eventDto.getId();
        final String bankCode = eventDto.getBankCode();

        final RuleConfigService.RuleConfig ruleConfig;
        try {
            ruleConfig = ruleConfigService.getRuleConfig(RULE_CODE);
        } catch (RuleConfigNotFoundException e) {
            String errorMessage = String.format("RuleConfig not found for ruleCode %s and eventId %d", RULE_CODE, eventId);
            log.error(errorMessage, e);
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, errorMessage);
        }

        final List<RuleConfigService.RuleConfig.Parameter> parameters = ruleConfig.getParameters();
        RuleConfigService.RuleConfig.Parameter fraudulentIpAddressesParam = RuleConfigUtils.getParam(parameters, FRAUDULENT_IP_ADDRESSES_PARAM, bankCode);

        if (Objects.isNull(fraudulentIpAddressesParam)) {
            String errorMessage = String.format("Parameter %s not found", FRAUDULENT_IP_ADDRESSES_PARAM);
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, errorMessage);
        }

        final LocationDto location = eventDto.getLocation();
        if (location == null || location.getIpAddress() == null) {
            String warningMessage = String.format("Invalid event data: location or ipAddress is null for eventId %d", eventId);
            log.warn(warningMessage);
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, warningMessage);
        }

        final String ipAddress = location.getIpAddress();
        List<String> fraudulentIpAddresses = Arrays.asList(fraudulentIpAddressesParam.getValue().split(","));

        final boolean isOriginatedFromFraudulentIpAddress = fraudulentIpAddresses.contains(ipAddress);

        String fraudReason = isOriginatedFromFraudulentIpAddress ?
                "Event originated from fraudulent IP Address" :
                "IP address not fraudulent";

        log.info("Fraud detection result for eventId {}: isFraud={}, reason={}", eventId, isOriginatedFromFraudulentIpAddress, fraudReason);

        return RuleConfigUtils.buildResult(eventId, RULE_NAME, isOriginatedFromFraudulentIpAddress, fraudReason);
    }
}
