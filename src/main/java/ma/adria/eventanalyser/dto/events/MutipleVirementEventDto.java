package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.CreditorDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class MutipleVirementEventDto extends VirementDto {
    private int nombreOperations;
    private List<CreditorDto> creditors;

    @Override
    BigDecimal getAmount() {
        return creditors.stream()
                .map(CreditorDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
