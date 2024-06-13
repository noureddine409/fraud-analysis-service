package ma.adria.eventanalyser.rules.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.IpCountryService;
import ma.adria.eventanalyser.service.RuleConfigService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A fraud detection rule that flags events originated from high-risk countries.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HighRiskCountryIpRule implements FraudRule {

    public static final String RULE_NAME = "High Risk Country IP Rule";
    public static final String RULE_CODE = "R-001";

    private final IpCountryService ipCountryService;
    private final RuleConfigService ruleConfigService;

    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {
        final Long eventId = eventDto.getId();

        final RuleConfigService.RuleConfig ruleConfig;

        try {
            ruleConfig = ruleConfigService.getRuleConfig(RULE_CODE);
            log.info("RuleConfig retrieved for ruleCode {}: {}", RULE_CODE, ruleConfig);
        } catch (RuleConfigNotFoundException e) {
            final String errorMessage = String.format("RuleConfig not found for ruleCode %s and eventId %d", RULE_CODE, eventId);
            log.error(errorMessage, e);
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason(errorMessage)
                    .build();
        }

        final List<RuleConfigService.RuleConfig.Parameter> parameters = ruleConfig.getParameters();

        RuleConfigService.RuleConfig.Parameter highRiskCountriesParam = parameters.stream()
                .filter(p -> "LIST_HIGH_RISK_COUNTRIES".equals(p.getCode()))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(highRiskCountriesParam)) {
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason("parameter LIST_HIGH_RISK_COUNTRIES not found")
                    .build();
        }

        List<CountryCode> highRiskCountries = Arrays.stream(highRiskCountriesParam.getValue().split(","))
                .map(CountryCode::getByAlpha2Code) // Adjust if the method needs to be non-static
                .filter(Objects::nonNull) // Filter out any null values
                .toList();


        final LocationDto location = eventDto.getLocation();
        if (location == null || location.getIpAddress() == null) {
            String warningMessage = String.format("Invalid event data: location or ipAddress is null for eventId %d", eventId);
            log.warn(warningMessage);
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason(warningMessage)
                    .build();
        }

        CountryCode country = ipCountryService.getCountryName(location.getIpAddress());

        if (country == null) {
            String warningMessage = String.format("Invalid event data: country could not be determined for IP address in eventId %d", eventId);
            log.warn(warningMessage);
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason(warningMessage)
                    .build();
        }

        final boolean ipFromHighRiskCountry = highRiskCountries.contains(country);
        String fraudReason = ipFromHighRiskCountry
                ? String.format("IP address from high-risk country (%s) for eventId %d", country, eventId)
                : String.format("IP address not from high-risk country for eventId %d", eventId);

        log.info("Fraud detection result for eventId {}: isFraud={}, reason={}", eventId, ipFromHighRiskCountry, fraudReason);

        return FraudDetectionResult.builder()
                .ruleName(RULE_NAME)
                .eventId(eventId)
                .isFraud(ipFromHighRiskCountry)
                .reason(fraudReason)
                .build();
    }
}
