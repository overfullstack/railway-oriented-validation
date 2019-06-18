package railwayoriented;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum ValidationFailure {
    VALIDATION_FAILURE_1("VALIDATION_FAILURE_1"),
    VALIDATION_FAILURE_2("VALIDATION_FAILURE_2"),
    VALIDATION_FAILURE_31("VALIDATION_FAILURE_31"),
    VALIDATION_FAILURE_32("VALIDATION_FAILURE_32"),
    VALIDATION_FAILURE_WITH_EXCEPTION("");

    private String errorMessage;

    private ValidationFailure setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
    
    static ValidationFailure withErrorMessage(String errorMessage) {
        return VALIDATION_FAILURE_WITH_EXCEPTION.setErrorMessage(errorMessage);
    }
}
