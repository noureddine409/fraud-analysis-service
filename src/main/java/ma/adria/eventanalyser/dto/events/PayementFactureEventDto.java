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
import ma.adria.eventanalyser.model.event.PayementFactureEvent;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)

public class PayementFactureEventDto extends EventDto {
    private String creancier;
    private String creance;
    private String intituleClient;
    private AccountDto compteDebitor;
    private LocalDateTime dateCreation;
    private LocalDateTime dateEnvoie;
    private String amount;
    private PayementFactureEvent.StatusPayementFactureEvent statusPayementFactureEvent;

    @Builder

    public PayementFactureEventDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String reference, LocalDateTime timestamp, Event.Canal canal, LocalDateTime activityTime, String username, String bankCode, String countryCode, String segment, LocationDto location, ContratDto contrat, DeviceDto device, String motif, String creancier, String creance, String intituleClient, AccountDto compteDebitor, LocalDateTime dateCreation, LocalDateTime dateEnvoie, String amount, PayementFactureEvent.StatusPayementFactureEvent statusPayementFactureEvent) {
        super(id, createdAt, updatedAt, reference, timestamp, canal, activityTime, username, bankCode, countryCode, segment, location, contrat, device, motif);
        this.creancier = creancier;
        this.creance = creance;
        this.intituleClient = intituleClient;
        this.compteDebitor = compteDebitor;
        this.dateCreation = dateCreation;
        this.dateEnvoie = dateEnvoie;
        this.amount = amount;
        this.statusPayementFactureEvent = statusPayementFactureEvent;
    }
}
