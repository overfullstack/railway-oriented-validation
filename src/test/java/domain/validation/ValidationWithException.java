package domain.validation;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
class ValidationWithException implements ValidationFailure {
    private final String exceptionMessage;
    
    ValidationWithException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
