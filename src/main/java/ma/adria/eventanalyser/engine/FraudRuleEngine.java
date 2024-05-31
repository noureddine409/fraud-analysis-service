package ma.adria.eventanalyser.engine;


import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.rules.FraudRule;
import reactor.core.publisher.Flux;


/**
 * A service responsible for executing fraud detection rules on incoming events.
 */
public interface FraudRuleEngine {


    /**
     * Executes fraud detection rules on the given event and returns a flux of fraud detection results.
     *
     * @param eventDto The event DTO to be evaluated.
     * @return A flux of fraud detection results.
     */
    Flux<FraudRule.FraudDetectionResult> executeRules(EventDto eventDto);


}
