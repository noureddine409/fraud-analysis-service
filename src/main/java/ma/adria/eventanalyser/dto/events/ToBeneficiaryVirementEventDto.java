package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.CreditorDto;

@Getter
@Setter
@ToString(callSuper = true)

public class ToBeneficiaryVirementEventDto extends EventDto {
    private CreditorDto creditor;

}
