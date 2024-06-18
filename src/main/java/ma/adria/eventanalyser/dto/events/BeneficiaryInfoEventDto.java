package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.BeneficiaryInfoDto;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class BeneficiaryInfoEventDto extends EventDto {
    private String action;
    private BeneficiaryInfoDto beneficiaryInfo;
}
