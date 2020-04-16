package algebra.types;

import io.vavr.collection.List;
import io.vavr.control.Either;

import java.util.function.Function;

/**
 * gakshintala created on 4/14/20.
 */
@FunctionalInterface
public interface AccumulationStrategy<ValidatableT, FailureT> extends Function<ValidatableT, List<Either<FailureT, ValidatableT>>> {
}
