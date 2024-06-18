package ma.adria.eventanalyser.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FraudCheckResult> fraudCheckResults;


    public enum Canal {
        WEB,
        MOBILE,
        OTHER
    }
}
