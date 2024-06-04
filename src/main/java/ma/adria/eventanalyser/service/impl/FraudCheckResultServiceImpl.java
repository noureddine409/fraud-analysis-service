package ma.adria.eventanalyser.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.adria.eventanalyser.exception.EventNotFoundException;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.model.FraudCheckResult;
import ma.adria.eventanalyser.repository.EventRepository;
import ma.adria.eventanalyser.repository.FraudCheckResultRepository;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.service.FraudCheckResultService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FraudCheckResultServiceImpl implements FraudCheckResultService {

    private final FraudCheckResultRepository resultRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public FraudCheckResult save(FraudRule.FraudDetectionResult fraudDetectionResult) throws EventNotFoundException {
        // Load the Event entity from the database using its ID
        final Long eventId = fraudDetectionResult.getEventId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event with id %s not found", eventId));
        // Create FraudCheckResult and associate it with the loaded Event
        FraudCheckResult preparedResult = FraudCheckResult.builder()
                .event(event) // Associate the event
                .fraud(fraudDetectionResult.isFraud())
                .reason(fraudDetectionResult.getReason())
                .ruleName(fraudDetectionResult.getRuleName())
                .build();
        return resultRepository.save(preparedResult);
    }
}
