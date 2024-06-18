package ma.adria.eventanalyser.rules.impl;

import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.dto.CreditorDto;
import ma.adria.eventanalyser.dto.events.AccountToAccountVirementEventDto;
import ma.adria.eventanalyser.dto.events.AuthenticationEventDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.dto.events.VirementDto;
import ma.adria.eventanalyser.service.RuleConfigService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionLimitRuleTest {

    @Mock
    private RuleConfigService ruleConfigService;

    @InjectMocks
    private TransactionLimitRule transactionLimitRule;

    @Test
    void testEvaluate_NullEventDto() {
        var result = transactionLimitRule.evaluate(null);
        assertFalse(result.isFraud());
        assertEquals("eventDto is null", result.getReason());
    }

    @Test
    void testEvaluate_InvalidEventDtoType() {
        EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .bankCode("BANK1")
                .segment("SEGMENT1")
                .build();
        var result = transactionLimitRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("eventDto is not an instance of VirementDto", result.getReason());
    }

    @Test
    void testEvaluate_RuleConfigNotFound() {
        VirementDto virementDto = createVirementDto(new BigDecimal("1000"));
        when(ruleConfigService.getRuleConfig(anyString())).thenReturn(null);
        var result = transactionLimitRule.evaluate(virementDto);
        assertFalse(result.isFraud());
        assertEquals("RuleConfig not found for ruleCode R-TRA-001 and eventId 1", result.getReason());
    }

    @Test
    void testEvaluate_LimitAmountParamNotFound() {
        VirementDto virementDto = createVirementDto(new BigDecimal("1000"));
        RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .parameters(Collections.emptyList())
                .build();
        when(ruleConfigService.getRuleConfig(anyString())).thenReturn(ruleConfig);
        var result = transactionLimitRule.evaluate(virementDto);
        assertFalse(result.isFraud());
        assertEquals("Limit amount parameter not found", result.getReason());
    }

    @Test
    void testEvaluate_TransactionWithinLimit() {
        VirementDto virementDto = createVirementDto(new BigDecimal("300"));
        RuleConfigService.RuleConfig.Parameter limitParam = createLimitParam("500");
        RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-TRA-001")
                .parameters(List.of(limitParam))
                .build();
        when(ruleConfigService.getRuleConfig("R-TRA-001")).thenReturn(ruleConfig);
        var result = transactionLimitRule.evaluate(virementDto);
        assertFalse(result.isFraud());
        assertEquals("Transaction amount within limit", result.getReason());
    }

    @Test
    void testEvaluate_TransactionExceedsLimit() {
        VirementDto virementDto = createVirementDto(new BigDecimal("1000"));
        RuleConfigService.RuleConfig.Parameter limitParam = createLimitParam("500");
        RuleConfigService.RuleConfig ruleConfig = RuleConfigService.RuleConfig.builder()
                .code("R-TRA-001")
                .parameters(List.of(limitParam))
                .build();
        when(ruleConfigService.getRuleConfig("R-TRA-001")).thenReturn(ruleConfig);
        var result = transactionLimitRule.evaluate(virementDto);
        assertTrue(result.isFraud());
        assertEquals("Transaction amount exceeds limit", result.getReason());
    }

    private VirementDto createVirementDto(BigDecimal amount) {
        return AccountToAccountVirementEventDto.builder()
                .id(1L)
                .bankCode("BANK1")
                .segment("SEGMENT1")
                .currency("MAD")
                .creditor(CreditorDto.builder()
                        .amount(amount)
                        .account(AccountDto.builder()
                                .accountNumber("XXXXXXXXXX")
                                .build())
                        .build()
                )
                .build();
    }

    private RuleConfigService.RuleConfig.Parameter createLimitParam(String value) {
        return RuleConfigService.RuleConfig.Parameter.builder()
                .code("LIMIT_AMOUNT")
                .value(value)
                .codeBank("BANK1")
                .segment("SEGMENT1")
                .build();
    }
}
