package ma.adria.eventanalyser.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class FraudCheckResult extends BaseEntity {
    private String ruleName;

    private boolean fraud;
    private String reason;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_id", nullable = false, updatable = false)
    private Event event;

    @Builder
    public FraudCheckResult(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String ruleName, boolean fraud, String reason, Event event) {
        super(id, createdAt, updatedAt);
        this.ruleName = ruleName;
        this.fraud = fraud;
        this.reason = reason;
        this.event = event;
    }
}
