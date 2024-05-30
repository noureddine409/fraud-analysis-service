package ma.adria.eventanalyser.engine;


import lombok.AllArgsConstructor;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.rules.FraudRule;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;


/**
 * A service responsible for executing fraud detection rules on incoming events.
 */
@Component
@AllArgsConstructor
public class FraudRuleEngine {

    private final List<FraudRule> fraudRules; // inject all FraudRule implementations

    /**
     * Executes fraud detection rules on the given event and returns a flux of fraud detection results.
     *
     * @param eventDto The event DTO to be evaluated.
     * @return A flux of fraud detection results.
     */
    public Flux<FraudRule.FraudDetectionResult> executeRules(EventDto eventDto) {
        return Flux.fromIterable(fraudRules)
                .parallel() // Execute rules in parallel
                .runOn(Schedulers.parallel()) // Use parallel scheduler
                .flatMap(rule -> Mono.fromCallable(() -> rule.evaluate(eventDto))) // Evaluate each rule
                .sequential();
    }


}
