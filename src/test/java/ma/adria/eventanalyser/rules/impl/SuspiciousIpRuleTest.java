package ma.adria.eventanalyser.rules.impl;

import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.AuthenticationEventDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.RuleConfigService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuspiciousIpRuleTest {

    @Mock
    private RuleConfigService ruleConfigService;

    @InjectMocks
    private SuspiciousIpRule suspiciousIpRule;

    @Test
    void testEvaluate_FraudulentIpAddress() {
        // Prepare Test Data
        final RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-002")
                .name("Suspicious IP Rule")
                .parameters(List.of(
                        RuleConfigService.RuleConfig.Parameter.builder()
                                .code("LIST_FRAUDULENT_IP_ADDRESSES")
                                .codeBank("B001")
                                .value("192.168.0.1,192.168.0.2")
                                .build()
                ))
                .build();
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress("192.168.0.1")
                        .build())
                .build();

        // Mock Rule Config Service
        when(ruleConfigService.getRuleConfig("R-002")).thenReturn(ruleConfig);

        // evaluate the rule
        FraudRule.FraudDetectionResult result = suspiciousIpRule.evaluate(eventDto);

        // Assertions
        assertTrue(result.isFraud());
        assertEquals("Event originated from fraudulent IP Address", result.getReason());
    }

    @Test
    void testEvaluate_NonFraudulentIpAddress() {
        // Prepare Test Data
        final RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-002")
                .name("Suspicious IP Rule")
                .parameters(List.of(
                        RuleConfigService.RuleConfig.Parameter.builder()
                                .code("LIST_FRAUDULENT_IP_ADDRESSES")
                                .codeBank("B001")
                                .value("192.168.0.1,192.168.0.2")
                                .build()
                ))
                .build();
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress("10.0.0.1")
                        .build())
                .build();

        // Mock Rule Config Service
        when(ruleConfigService.getRuleConfig("R-002")).thenReturn(ruleConfig);

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = suspiciousIpRule.evaluate(eventDto);

        // Assertions
        assertFalse(result.isFraud());
        assertEquals("IP address not fraudulent", result.getReason());
    }

    @Test
    void testEvaluate_RuleConfigNotFoundException() {
        // Mock Rule Config Service to throw RuleConfigNotFoundException
        when(ruleConfigService.getRuleConfig("R-002")).thenThrow(new RuleConfigNotFoundException("Rule config not found: {}", "R-002"));

        // Prepare EventDto
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress("192.168.0.1")
                        .build())
                .build();

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = suspiciousIpRule.evaluate(eventDto);

        // Assertions
        assertFalse(result.isFraud());
        assertTrue(result.getReason().contains("RuleConfig not found"));
    }

    @Test
    void testEvaluate_NullEventDto() {
        // Evaluate the rule with null EventDto
        FraudRule.FraudDetectionResult result = suspiciousIpRule.evaluate(null);

        // Assertions
        assertFalse(result.isFraud()); // Assuming a null EventDto does not constitute fraud
        assertTrue(result.getReason().contains("eventDto is null"));
    }

}
