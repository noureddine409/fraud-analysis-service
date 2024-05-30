package ma.adria.eventanalyser.dto.events;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import ma.adria.eventanalyser.dto.ContratDto;
import ma.adria.eventanalyser.dto.DeviceDto;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.model.Event;

import java.time.LocalDateTime;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "@eventType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AuthenticationEventDto.class, name = "AUTHENTICATION"),
        @JsonSubTypes.Type(value = RemiseOrdreEventDto.class, name = "REMISE_ORDRE"),
        @JsonSubTypes.Type(value = PayementFactureEventDto.class, name = "PAYEMENT_FACTURE"),
        @JsonSubTypes.Type(value = CarteTransactionEventDto.class, name = "CARTE_TRANSACTION"),
        @JsonSubTypes.Type(value = BeneficiaryInfoEventDto.class, name = "BENEFICIARY_INFO"),
        @JsonSubTypes.Type(value = AccountToAccountVirementEventDto.class, name = "ACCOUNTOACCOUNT_VIREMENT"),
        @JsonSubTypes.Type(value = ToBeneficiaryVirementEventDto.class, name = "TOBENEFICIARY_VIREMENT"),
        @JsonSubTypes.Type(value = MutipleVirementEventDto.class, name = "MULTIPLE_VIREMENT"),
        @JsonSubTypes.Type(value = DemandeChequierEventDto.class, name = "DEMANDE_CHEQUIER"),
        @JsonSubTypes.Type(value = DemandeLcnEventDto.class, name = "DEMANDE_LCN"),
        @JsonSubTypes.Type(value = DemandeRecalculCodePinEventDto.class, name = "DEMANDE_RECALCUL_CODE_PIN"),
        @JsonSubTypes.Type(value = DemandeOppositionCarteEventDto.class, name = "DEMANDE_OPPOSITION_CARTE"),
        @JsonSubTypes.Type(value = RechargeCarteEventDto.class, name = "RECHARGE_CARTE"),
        @JsonSubTypes.Type(value = DemandeOppositionChequeEventDto.class, name = "DEMANDE_OPPOSITION_CHEQUE"),


})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class EventDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String reference;
    private LocalDateTime timestamp;
    private Event.Canal canal;
    private LocalDateTime activityTime;
    private String username;
    private String bankCode;
    private String countryCode;
    private String segment;
    private LocationDto location;
    private ContratDto contrat;
    private DeviceDto device;
    private String motif;


}
