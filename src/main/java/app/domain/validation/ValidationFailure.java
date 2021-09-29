package app.domain.validation;


import lombok.ToString;

public interface ValidationFailure {

  static ValidationFailure withErrorMessage(String exceptionMessage) {
    return new FailureForThrowable(exceptionMessage);
  }

  static ValidationFailure withThrowable(Throwable throwable) {
    return new FailureForThrowable(throwable.getMessage());
  }

  @ToString
  record FailureForThrowable(String exceptionMessage) implements ValidationFailure {}
}

