package ma.adria.eventanalyser.rules.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.IpCountryService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A fraud detection rule that flags events originated from high-risk countries.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HighRiskCountryIpRule implements FraudRule {

    public static final String RULE_NAME = "High Risk Country IP Rule";

    private final IpCountryService ipCountryService;

    List<CountryCode> highRiskCountries = List.of(CountryCode.CO, CountryCode.IN, CountryCode.TN);

    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {
        final LocationDto location = eventDto.getLocation();
        final Long eventId = eventDto.getId();
        if (location == null || location.getIpAddress() == null) {
            log.warn("Invalid event data: location or ipAddress is null");
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason("Invalid event data")
                    .build();
        }

        CountryCode country = ipCountryService.getCountryName(location.getIpAddress());

        if (country == null) {
            log.warn("Invalid event data: ip address not found");
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason("Cannot found location of ip Address")
                    .build();
        }

        final boolean ipFromHighRiskCountry = highRiskCountries.stream().anyMatch(country::equals);

        return FraudDetectionResult.builder()
                .ruleName(RULE_NAME)
                .eventId(eventId)
                .isFraud(ipFromHighRiskCountry)
                .reason(ipFromHighRiskCountry ? "IP address from a high-risk country" : "IP address not from a high-risk country")
                .build();

    }
}
