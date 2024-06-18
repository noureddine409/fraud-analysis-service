package ma.adria.eventanalyser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.adria.eventanalyser.model.ExecutionFrequency;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionFrequencyDto {
    private ExecutionFrequency.ExecutionFrequencyType frequency;
    private LocalDateTime firstTransactionDateTime;
}
