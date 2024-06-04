package ma.adria.eventanalyser.service;

import ma.adria.eventanalyser.exception.EventNotFoundException;
import ma.adria.eventanalyser.model.FraudCheckResult;
import ma.adria.eventanalyser.rules.FraudRule;

public interface FraudCheckResultService {
    FraudCheckResult save(FraudRule.FraudDetectionResult fraudDetectionResult) throws EventNotFoundException;
}
