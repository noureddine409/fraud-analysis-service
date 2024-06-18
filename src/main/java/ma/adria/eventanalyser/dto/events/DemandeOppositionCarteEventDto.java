package ma.adria.eventanalyser.dto.events;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.DemandeOppositionCarteEvent;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class DemandeOppositionCarteEventDto extends EventDto {
    private AccountDto account;
    private String motif;
    private LocalDateTime datePerte;
    private LocalDateTime dateEnvoie;
    private DemandeOppositionCarteEvent.DemandeOppositionCarteStatus status;

}
