package declarative;

import io.vavr.control.Either;

import java.util.function.Function;

/**
 * gakshintala created on 4/11/20.
 */
public interface Validator<FailureT, ValidatableT> extends Function<Either<FailureT, ValidatableT>, Either<FailureT, ValidatableT>> {
}
