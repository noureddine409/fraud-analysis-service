package ma.adria.eventanalyser.dto.events;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.DemandeRecalculCodePinEvent;

@Getter
@Setter
@ToString(callSuper = true)
public class DemandeRecalculCodePinEventDto extends EventDto {
    private AccountDto account;
    private String motif;
    private String carteId;
    private DemandeRecalculCodePinEvent.TransferMode modeEnvoie;
    private DemandeRecalculCodePinEvent.DemandeRecalculCodePinStatus status;


}
