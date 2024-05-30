package ma.adria.eventanalyser.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Event extends BaseEntity {
    private String reference;
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private Canal canal;
    private LocalDateTime activityTime;
    private String username;
    private String bankCode;
    private String countryCode;

    private String segment;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @ManyToOne
    private Contrat contrat;

    @ManyToOne
    private Device device;

    private String motif;

    @Builder
    public Event(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String reference, LocalDateTime timestamp, Canal canal, LocalDateTime activityTime, String username, String bankCode, String countryCode, String segment, Location location, Contrat contrat, Device device, String motif) {
        super(id, createdAt, updatedAt);
        this.reference = reference;
        this.timestamp = timestamp;
        this.canal = canal;
        this.activityTime = activityTime;
        this.username = username;
        this.bankCode = bankCode;
        this.countryCode = countryCode;
        this.segment = segment;
        this.location = location;
        this.contrat = contrat;
        this.device = device;
        this.motif = motif;
    }

    public enum Canal {
        WEB,
        MOBILE,
        OTHER
    }
}
