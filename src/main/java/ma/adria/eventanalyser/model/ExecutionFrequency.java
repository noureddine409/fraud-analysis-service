package ma.adria.eventanalyser.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "executionFrequency")
public class ExecutionFrequency extends BaseEntity{
    private ExecutionFrequencyType frequency;
    private LocalDateTime firstTransactionDateTime;

    @Builder
    public ExecutionFrequency(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, ExecutionFrequencyType frequency, LocalDateTime firstTransactionDateTime) {
        super(id, createdAt, updatedAt);
        this.frequency = frequency;
        this.firstTransactionDateTime = firstTransactionDateTime;
    }

    public  enum ExecutionFrequencyType{
        YEARLY, MONTHLY ,WEEKLY, DAILY
    }
}
