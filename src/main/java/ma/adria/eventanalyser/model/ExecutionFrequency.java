package ma.adria.eventanalyser.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "executionFrequency")
@SuperBuilder
public class ExecutionFrequency extends BaseEntity {
    private ExecutionFrequencyType frequency;
    private LocalDateTime firstTransactionDateTime;

    public enum ExecutionFrequencyType {
        YEARLY, MONTHLY, WEEKLY
    }
}
