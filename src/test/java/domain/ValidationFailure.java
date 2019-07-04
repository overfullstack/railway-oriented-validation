package domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum ValidationFailure {
    // TODO 2019-07-03 gakshintala: Have meaningful messages for validation failures
    VALIDATION_FAILURE_1("VALIDATION_FAILURE_1"),
    VALIDATION_FAILURE_2("VF2: Too late to hatch"),
    VALIDATION_FAILURE_31("VALIDATION_FAILURE_31"),
    VALIDATION_FAILURE_32("VALIDATION_FAILURE_32"),
    VALIDATION_FAILURE_WITH_EXCEPTION("");

    private String errorMessage;

    private ValidationFailure setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
    
    public static ValidationFailure withErrorMessage(String errorMessage) {
        return VALIDATION_FAILURE_WITH_EXCEPTION.setErrorMessage(errorMessage);
    }
}