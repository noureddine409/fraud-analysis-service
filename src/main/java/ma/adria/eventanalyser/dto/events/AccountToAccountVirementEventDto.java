package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.CreditorDto;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class AccountToAccountVirementEventDto extends VirementDto {

    private CreditorDto creditor;

    @Override
    BigDecimal getAmount() {
        return this.creditor.getAmount();
    }
}
