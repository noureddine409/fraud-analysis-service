package ma.adria.eventanalyser.rules;

import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ma.adria.eventanalyser.model.Event;

/**
 * Interface representing a rule for detecting fraud.
 */
public interface FraudRule {

    /**
     * Evaluates whether the given event is fraudulent.
     *
     * @param event       the event to evaluate
     * @param jpaStreamer the JPAStreamer instance used for querying
     * @return a {@link FraudDetectionResult} indicating if the event is fraudulent and the reason
     */
    FraudDetectionResult evaluate(Event event, JPAStreamer jpaStreamer);

    /**
     * Represents the result of a fraud detection evaluation.
     */
    @AllArgsConstructor
    @Data
    @Builder
    class FraudDetectionResult {
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
