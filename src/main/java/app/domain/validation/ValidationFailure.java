package app.domain.validation;


import lombok.EqualsAndHashCode;
import lombok.ToString;

public interface ValidationFailure {

    static ValidationFailure withErrorMessage(String exceptionMessage) {
        return new ValidationWithException(exceptionMessage);
    }

    static ValidationFailure withThrowable(Throwable throwable) {
        return new ValidationWithException(throwable.getMessage());
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

