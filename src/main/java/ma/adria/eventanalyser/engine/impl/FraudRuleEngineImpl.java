package ma.adria.eventanalyser.engine.impl;

import lombok.AllArgsConstructor;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.engine.FraudRuleEngine;
import ma.adria.eventanalyser.rules.FraudRule;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;


@Component
@AllArgsConstructor
public class FraudRuleEngineImpl implements FraudRuleEngine {

    private final List<FraudRule> fraudRules; // inject all FraudRule implementations

    @Override
    public Flux<FraudRule.FraudDetectionResult> executeRules(EventDto eventDto) {
        return Flux.fromIterable(fraudRules)
                .parallel() // Execute rules in parallel
                .runOn(Schedulers.parallel()) // Use parallel scheduler
                .flatMap(rule -> Mono.fromCallable(() -> rule.evaluate(eventDto))) // Evaluate each rule
                .sequential();
    }

}
