package domain.validation;


import lombok.EqualsAndHashCode;
import lombok.ToString;

public interface ValidationFailure {

    static ValidationFailure withErrorMessage(String exceptionMessage) {
        return new ValidationWithException(exceptionMessage);
    }

    @EqualsAndHashCode
    @ToString
    static class ValidationWithException implements ValidationFailure {
        private final String exceptionMessage;

        ValidationWithException(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }
    }
}

