package ma.adria.eventanalyser.rules.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.dto.events.VirementDto;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.RuleConfigService;
import ma.adria.eventanalyser.utils.RuleConfigUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionLimitRule implements FraudRule {

    private static final String RULE_NAME = "Transaction limit rule";
    private static final String RULE_CODE = "R-TRA-001";
    private static final String LIMIT_AMOUNT_PARAM = "LIMIT_AMOUNT";

    private final RuleConfigService ruleConfigService;

    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {

        if (eventDto == null) {
            log.warn("Invalid event data: eventDto is null");
            return RuleConfigUtils.buildResult(null, RULE_NAME, false, "eventDto is null");
        }
        if (!(eventDto instanceof VirementDto virement)) {
            log.warn("Invalid event data: eventDto is not an instance of VirementDto");
            return RuleConfigUtils.buildResult(eventDto.getId(), RULE_NAME, false, "eventDto is not an instance of VirementDto");
        }

        final Long eventId = eventDto.getId();
        final String bankCode = eventDto.getBankCode();
        final String segment = eventDto.getSegment();

        final BigDecimal transactionAmount = virement.getAmount();

        final var ruleConfig = RuleConfigUtils.getRuleConfig(ruleConfigService, RULE_CODE, eventId);

        if (Objects.isNull(ruleConfig)) {
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, String.format("RuleConfig not found for ruleCode %s and eventId %d", RULE_CODE, eventId));
        }

        final var parameters = ruleConfig.getParameters();

        var limitAmountParam = RuleConfigUtils.getParam(parameters, LIMIT_AMOUNT_PARAM, bankCode, segment);

        if (limitAmountParam == null) {
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, "Limit amount parameter not found");
        }

        BigDecimal limitAmount = new BigDecimal(limitAmountParam.getValue());

        if (transactionAmount.compareTo(limitAmount) > 0) {
            log.info("Transaction amount {} exceeds limit amount {}", transactionAmount, limitAmount);
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, true, "Transaction amount exceeds limit");
        }

        return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, "Transaction amount within limit");
    }
}
