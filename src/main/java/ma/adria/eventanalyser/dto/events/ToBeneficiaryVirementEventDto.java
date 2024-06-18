package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.CreditorDto;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class ToBeneficiaryVirementEventDto extends VirementDto {
    private CreditorDto creditor;

    @Override
    BigDecimal getAmount() {
        return this.creditor.getAmount();
    }
}
