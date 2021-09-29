package algebra;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;

@FunctionalInterface
public interface AccumulationStrategy<ValidatableT, FailureT> extends
    Function1<ValidatableT, List<Either<FailureT, ValidatableT>>> {
}
