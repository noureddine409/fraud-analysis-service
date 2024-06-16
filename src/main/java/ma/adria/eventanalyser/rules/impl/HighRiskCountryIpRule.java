package ma.adria.eventanalyser.rules.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.IpCountryService;
import ma.adria.eventanalyser.service.RuleConfigService;
import ma.adria.eventanalyser.utils.RuleConfigUtils;
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

    private static final String RULE_NAME = "High Risk Country IP Rule";
    private static final String RULE_CODE = "R-001";
    private static final String HIGH_RISK_COUNTRIES_PARAM = "LIST_HIGH_RISK_COUNTRIES";

    private final IpCountryService ipCountryService;
    private final RuleConfigService ruleConfigService;

    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {
        if (eventDto == null) {
            log.warn("Invalid event data: eventDto is null");
            return RuleConfigUtils.buildResult(null, RULE_NAME, false, "eventDto is null");
        }


        final Long eventId = eventDto.getId();
        final String bankCode = eventDto.getBankCode();

        final var ruleConfig = RuleConfigUtils.getRuleConfig(ruleConfigService, RULE_CODE, eventId);

        if (Objects.isNull(ruleConfig)) {
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, String.format("RuleConfig not found for ruleCode %s and eventId %d", RULE_CODE, eventId));
        }

        final var parameters = ruleConfig.getParameters();
        var highRiskCountriesParam = RuleConfigUtils.getParam(parameters, HIGH_RISK_COUNTRIES_PARAM, bankCode);

        if (Objects.isNull(highRiskCountriesParam)) {
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, String.format("Parameter %s not found", HIGH_RISK_COUNTRIES_PARAM));
        }

        List<CountryCode> highRiskCountries = getHighRiskCountries(highRiskCountriesParam);
        final LocationDto location = eventDto.getLocation();

        if (location == null || location.getIpAddress() == null) {
            String warningMessage = String.format("Invalid event data: location or ipAddress is null for eventId %d", eventId);
            log.warn(warningMessage);
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, warningMessage);
        }

        CountryCode country = ipCountryService.getCountryName(location.getIpAddress());

        if (country == null) {
            String warningMessage = String.format("Invalid event data: country could not be determined for IP address in eventId %d", eventId);
            log.warn(warningMessage);
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, warningMessage);
        }

        final boolean ipFromHighRiskCountry = highRiskCountries.contains(country);
        String fraudReason = ipFromHighRiskCountry
                ? String.format("IP address from high-risk country (%s) for eventId %d", country, eventId)
                : String.format("IP address not from high-risk country for eventId %d", eventId);

        log.info("Fraud detection result for eventId {}: isFraud={}, reason={}", eventId, ipFromHighRiskCountry, fraudReason);

        return RuleConfigUtils.buildResult(eventId, RULE_NAME, ipFromHighRiskCountry, fraudReason);
    }

    private List<CountryCode> getHighRiskCountries(RuleConfigService.RuleConfig.Parameter highRiskCountriesParam) {
        return Arrays.stream(highRiskCountriesParam.getValue().split(","))
                .map(String::toUpperCase)
                .map(code -> {
                    CountryCode countryCode = CountryCode.getByAlpha2Code(code);
                    if (countryCode == null) {
                        log.warn("Cannot parse the country code {}", code);
                    }
                    return countryCode;
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
