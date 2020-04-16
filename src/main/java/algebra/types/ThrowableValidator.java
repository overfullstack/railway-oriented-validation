package algebra.types;

import io.vavr.control.Either;

import java.util.function.Function;

/**
 * gakshintala created on 4/11/20.
 */
@FunctionalInterface
public interface ThrowableValidator<ValidatableT, FailureT>
        extends Function<ValidatableT, Either<Either<Throwable, FailureT>, ValidatableT>> {
}
