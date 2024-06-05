package ma.adria.eventanalyser.rules.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.rules.FraudRule;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * A fraud detection rule that flags events originated from fraudulent ip adresses.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SuspiciousIpRule implements FraudRule {

    public static final String RULE_NAME = "Suspicious IP Rule";

    List<String> fraudulentIpAddresses = List.of(
            "192.168.1.100",
            "203.0.113.5",
            "198.51.100.23",
            "203.0.113.78",
            "198.51.100.45"
    );


    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {
        final Long eventId = eventDto.getId();
        final LocationDto location = eventDto.getLocation();
        if (location == null || location.getIpAddress() == null) {
            log.warn("Invalid event data: location or ipAddress is null");
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason("Invalid event data")
                    .build();
        }

        final String ipAddress = location.getIpAddress();

        final boolean isOriginatedFromFraudulentIpAddress = fraudulentIpAddresses
                .stream()
                .anyMatch(ipAddress::equals);

        return FraudDetectionResult.builder()
                .ruleName(RULE_NAME)
                .eventId(eventId)
                .isFraud(isOriginatedFromFraudulentIpAddress)
                .reason(isOriginatedFromFraudulentIpAddress ? "Event originated from fraudulent IP Address" : "IP address not fraudulent")
                .build();

    }
}
