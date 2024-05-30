package ma.adria.eventanalyser.messaging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.engine.FraudRuleEngine;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final FraudRuleEngine fraudRuleEngine;

    @KafkaListener(topics = "t.events.prepared", groupId = "my-group")
    public void consumeEvent(String message) {
        log.info("Received Kafka message: {}", message);
        try {
            EventDto eventDto = objectMapper.readValue(message, EventDto.class);
            log.info("Received event of type {}: {}", eventDto.getClass().getSimpleName(), eventDto);
            fraudRuleEngine.executeRules(eventDto)
                    .subscribe(r -> log.info("rule evaluation result: {}", r));
        } catch (JsonProcessingException e) {
            log.error("error while parsing event: {}", e.getMessage());
        }
    }
}
