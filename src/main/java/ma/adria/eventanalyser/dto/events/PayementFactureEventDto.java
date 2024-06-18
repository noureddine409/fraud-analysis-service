package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.PayementFactureEvent;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class PayementFactureEventDto extends EventDto {
    private String creancier;
    private String creance;
    private String intituleClient;
    private AccountDto compteDebitor;
    private LocalDateTime dateCreation;
    private LocalDateTime dateEnvoie;
    private String amount;
    private PayementFactureEvent.StatusPayementFactureEvent statusPayementFactureEvent;
}
