package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.BeneficiaryInfoDto;

@Getter
@Setter
@ToString(callSuper = true)

public class BeneficiaryInfoEventDto extends EventDto {
    private String action;
    private BeneficiaryInfoDto beneficiaryInfo;
}
