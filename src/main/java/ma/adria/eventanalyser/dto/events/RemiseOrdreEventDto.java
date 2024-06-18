package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.RemiseOrdreEvent;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class RemiseOrdreEventDto extends EventDto {
    private String natureRemise;
    private String FormatRemiseOrdre;
    private String donneurOrdre;
    private AccountDto compteDebit;
    private String fichier;
    private RemiseOrdreEvent.AjouterRemiseStatus status;

}
