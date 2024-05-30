package ma.adria.eventanalyser.rules;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ma.adria.eventanalyser.dto.events.EventDto;

/**
 * Interface representing a rule for detecting fraud.
 */
public interface FraudRule {

    /**
     * Evaluates whether the given event is fraudulent.
     *
     * @param eventDto the event to evaluate
     * @return a {@link FraudDetectionResult} indicating if the event is fraudulent and the reason
     */
    FraudDetectionResult evaluate(EventDto eventDto);

    /**
     * Represents the result of a fraud detection evaluation.
     */
    @AllArgsConstructor
    @Data
    @Builder
    class FraudDetectionResult {
        /**
         * The name of the rule that produced this result.
         */
        private String ruleName;
        /**
         * The ID of the event that has been evaluated.
         */
        private Long eventId;
        /**
         * Indicates whether the event is fraudulent.
         */
        private boolean isFraud;

        /**
         * Provides a reason for why the event was determined to be fraudulent.
         */
        private String reason;
    }
}
