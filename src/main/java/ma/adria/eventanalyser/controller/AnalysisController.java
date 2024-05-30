package ma.adria.eventanalyser.controller;


import com.speedment.jpastreamer.application.JPAStreamer;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.rules.FraudRule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analysis")
public class AnalysisController {

    private final FraudRule fraudRule;
    private final JPAStreamer jpaStreamer;

    public AnalysisController(FraudRule fraudRule, JPAStreamer jpaStreamer) {
        this.fraudRule = fraudRule;
        this.jpaStreamer = jpaStreamer;
    }

    @PostMapping
    public ResponseEntity<FraudRule.FraudDetectionResult> analyse(@RequestBody Event event) {
        var result = fraudRule.evaluate(event, jpaStreamer);
        return ResponseEntity.ok(result);
    }
}
