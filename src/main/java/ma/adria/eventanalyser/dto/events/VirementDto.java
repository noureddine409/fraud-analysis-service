package ma.adria.eventanalyser.dto.events;


import lombok.*;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.ExecutionFrequencyDto;
import ma.adria.eventanalyser.model.Virement;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class VirementDto extends EventDto {
    private Virement.ExecutionDateType type;
    private String currency;
    private ExecutionFrequencyDto executionFrequency;

    public abstract BigDecimal getAmount();

}
