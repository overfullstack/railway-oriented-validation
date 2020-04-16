package algebra.types;

import io.vavr.control.Either;

import java.util.function.UnaryOperator;

/**
 * gakshintala created on 4/11/20.
 */
@FunctionalInterface
public interface Validator<ValidatableT, FailureT> extends UnaryOperator<Either<FailureT, ValidatableT>> {
}
