package ma.adria.eventanalyser.dto.events;

import lombok.*;
import ma.adria.eventanalyser.model.ExecutionFrequency;
import ma.adria.eventanalyser.model.Virement;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class VirementDto {
    private Virement.ExecutionDateType type;
    private String reason;
    private String curency;
    private ExecutionFrequency executionFrequency;
}
