package ma.adria.eventanalyser.rules.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.AuthenticationEventDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.exception.RuleConfigNotFoundException;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.IpCountryService;
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
@Slf4j
class HighRiskCountryIpRuleTest {

    @Mock
    private RuleConfigService ruleConfigService;

    @Mock
    private IpCountryService ipCountryService;

    @InjectMocks
    private HighRiskCountryIpRule highRiskCountryIpRule;

    @Test
    void testEvaluate_FraudulentCountry() {
        // Prepare Test Data
        final RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-001")
                .name("High Risk Country IP Rule")
                .parameters(List.of(
                        RuleConfigService.RuleConfig.Parameter.builder()
                                .code("LIST_HIGH_RISK_COUNTRIES")
                                .codeBank("B001")
                                .value("IR,IN") // High-risk countries
                                .build()
                ))
                .build();
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress("8.8.8.8") // Assuming this IP belongs to a high-risk country
                        .build())
                .build();

        // Mock Rule Config Service
        when(ruleConfigService.getRuleConfig("R-001")).thenReturn(ruleConfig);

        // Mock IpCountryService
        when(ipCountryService.getCountryName("8.8.8.8")).thenReturn(CountryCode.IR);

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = highRiskCountryIpRule.evaluate(eventDto);

        // Assertions
        assertTrue(result.isFraud());
        assertEquals("IP address from high-risk country (IR) for eventId 1", result.getReason());
    }

    @Test
    void testEvaluate_NonFraudulentCountry() {
        // Prepare Test Data
        final RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-001")
                .name("High Risk Country IP Rule")
                .parameters(List.of(
                        RuleConfigService.RuleConfig.Parameter.builder()
                                .code("LIST_HIGH_RISK_COUNTRIES")
                                .codeBank("B001")
                                .value("IR,SY") // High-risk countries
                                .build()
                ))
                .build();
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress("192.168.0.1") // Assuming this IP does not belong to a high-risk country
                        .build())
                .build();

        // Mock Rule Config Service
        when(ruleConfigService.getRuleConfig("R-001")).thenReturn(ruleConfig);

        // Mock IpCountryService
        when(ipCountryService.getCountryName("192.168.0.1")).thenReturn(CountryCode.getByAlpha2Code("US"));

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = highRiskCountryIpRule.evaluate(eventDto);

        // Assertions
        assertFalse(result.isFraud());
        assertEquals("IP address not from high-risk country for eventId 1", result.getReason());
    }

    @Test
    void testEvaluate_RuleConfigNotFoundException() {
        // Prepare Test Data
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress("192.168.0.1")
                        .build())
                .build();

        // Mock Rule Config Service to throw RuleConfigNotFoundException
        when(ruleConfigService.getRuleConfig("R-001")).thenThrow(new RuleConfigNotFoundException("Rule config not found: %s", "R-001"));

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = highRiskCountryIpRule.evaluate(eventDto);

        // Assertions
        assertFalse(result.isFraud());
        assertTrue(result.getReason().contains("RuleConfig not found"));
    }

    @Test
    void testEvaluate_MissingHighRiskCountriesParam() {
        // Prepare Test Data
        final RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-001")
                .name("High Risk Country IP Rule")
                .parameters(List.of(
                        RuleConfigService.RuleConfig.Parameter.builder()
                                .code("ANOTHER_PARAM")
                                .codeBank("B001")
                                .value("IR,SY")
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
        when(ruleConfigService.getRuleConfig("R-001")).thenReturn(ruleConfig);

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = highRiskCountryIpRule.evaluate(eventDto);

        // Assertions
        assertFalse(result.isFraud());
        assertTrue(result.getReason().contains("Parameter LIST_HIGH_RISK_COUNTRIES not found"));
    }

    @Test
    void testEvaluate_NullIpAddress() {
        // Prepare Test Data
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress(null)
                        .build())
                .build();

        final RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-001")
                .name("High Risk Country IP Rule")
                .parameters(List.of(
                        RuleConfigService.RuleConfig.Parameter.builder()
                                .code("ANOTHER_PARAM")
                                .codeBank("B001")
                                .value("IR,SY")
                                .build()
                ))
                .build();

        // Mock Rule Config Service
        when(ruleConfigService.getRuleConfig("R-001")).thenReturn(ruleConfig);

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = highRiskCountryIpRule.evaluate(eventDto);

        // Assertions
        assertFalse(result.isFraud());
        assertTrue(result.getReason().contains("Invalid event data"));
    }

    @Test
    void testEvaluate_InvalidIpAddress() {
        // Prepare Test Data
        final EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("B001")
                .location(LocationDto.builder()
                        .ipAddress("999.999.999.999") // Invalid IP
                        .build())
                .build();

        final RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-001")
                .name("High Risk Country IP Rule")
                .parameters(List.of(
                        RuleConfigService.RuleConfig.Parameter.builder()
                                .code("ANOTHER_PARAM")
                                .codeBank("B001")
                                .value("IR,SY")
                                .build()
                ))
                .build();

        // Mock Rule Config Service
        when(ruleConfigService.getRuleConfig("R-001")).thenReturn(ruleConfig);

        // Mock IpCountryService
        when(ipCountryService.getCountryName("999.999.999.999")).thenReturn(null);

        // Evaluate the rule
        FraudRule.FraudDetectionResult result = highRiskCountryIpRule.evaluate(eventDto);

        // Assertions
        assertFalse(result.isFraud());
        assertTrue(result.getReason().contains("Invalid event data"));
    }


}

