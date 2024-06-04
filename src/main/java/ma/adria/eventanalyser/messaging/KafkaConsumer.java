package ma.adria.eventanalyser.messaging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.engine.FraudRuleEngine;
import ma.adria.eventanalyser.model.FraudCheckResult;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.FraudCheckResultService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final FraudRuleEngine fraudRuleEngine;
    private final FraudCheckResultService fraudCheckResultService;

    @KafkaListener(topics = "t.events.prepared", groupId = "my-group")
    public void consumeEvent(String message) {
        log.info("Received Kafka message: {}", message);
        try {
            EventDto eventDto = objectMapper.readValue(message, EventDto.class);
            log.info("Received event of type {}: {}", eventDto.getClass().getSimpleName(), eventDto);
            fraudRuleEngine.executeRules(eventDto)
                    .subscribe(this::processFraudDetectionResult);
        } catch (JsonProcessingException e) {
            log.error("error while parsing event: {}", e.getMessage());
        }
    }

    private void processFraudDetectionResult(FraudRule.FraudDetectionResult r) {
        log.info("rule evaluation result: {}", r);
        FraudCheckResult savedFraudCheckResult = fraudCheckResultService.save(r);
        log.info("Saved FraudCheckResult with ID: {}, Rule Name: {}, Fraud Status: {}",
                savedFraudCheckResult.getId(),
                savedFraudCheckResult.getRuleName(),
                savedFraudCheckResult.isFraud());
    }
}

