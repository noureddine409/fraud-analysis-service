package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.CreditorDto;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class MutipleVirementEventDto extends EventDto {
    private int nombreOperations;
    private List<CreditorDto> creditors;

}
