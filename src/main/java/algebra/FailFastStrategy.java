package algebra;

import io.vavr.Function1;
import io.vavr.control.Either;

@FunctionalInterface
public interface FailFastStrategy<ValidatableT, FailureT> extends
    Function1<ValidatableT, Either<FailureT, ValidatableT>> {
}
