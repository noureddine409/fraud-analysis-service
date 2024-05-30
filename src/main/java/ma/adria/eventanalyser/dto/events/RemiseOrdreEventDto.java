package ma.adria.eventanalyser.dto.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.dto.ContratDto;
import ma.adria.eventanalyser.dto.DeviceDto;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.model.event.RemiseOrdreEvent;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class RemiseOrdreEventDto extends EventDto {
    private String natureRemise;
    private String FormatRemiseOrdre;
    private String donneurOrdre;
    private AccountDto compteDebit;
    private String fichier;
    private RemiseOrdreEvent.AjouterRemiseStatus status;

    @Builder

    public RemiseOrdreEventDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String reference, LocalDateTime timestamp, Event.Canal canal, LocalDateTime activityTime, String username, String bankCode, String countryCode, String segment, LocationDto location, ContratDto contrat, DeviceDto device, String motif, String natureRemise, String formatRemiseOrdre, String donneurOrdre, AccountDto compteDebit, String fichier, RemiseOrdreEvent.AjouterRemiseStatus status) {
        super(id, createdAt, updatedAt, reference, timestamp, canal, activityTime, username, bankCode, countryCode, segment, location, contrat, device, motif);
        this.natureRemise = natureRemise;
        FormatRemiseOrdre = formatRemiseOrdre;
        this.donneurOrdre = donneurOrdre;
        this.compteDebit = compteDebit;
        this.fichier = fichier;
        this.status = status;
    }
}
