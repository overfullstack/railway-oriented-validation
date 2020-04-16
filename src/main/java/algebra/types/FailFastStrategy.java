package algebra.types;
import io.vavr.control.Either;

import java.util.function.Function;

/**
 * gakshintala created on 4/14/20.
 */
@FunctionalInterface
public interface FailFastStrategy<ValidatableT, FailureT> extends Function<ValidatableT, Either<FailureT, ValidatableT>> {
}
