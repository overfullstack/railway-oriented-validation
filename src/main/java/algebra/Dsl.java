package algebra;

import algebra.types.ThrowableValidator;
import algebra.types.Validator;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;
import lombok.val;

import static io.vavr.CheckedFunction1.liftTry;
import static io.vavr.Function1.identity;

/**
 * gakshintala created on 4/15/20.
 */
@UtilityClass
public class Dsl {

    public static <FailureT, ValidatableT> Validator<ValidatableT, FailureT> liftThrowable(
            ThrowableValidator<ValidatableT, FailureT> toBeLifted, Function1<Throwable, FailureT> throwableMapper) {
        return validatable -> {
            val result = liftTry(toBeLifted).apply(validatable).toEither();
            return result.fold(throwable -> Either.left(throwableMapper.apply(throwable)), identity());
        };
    }

    public static <FailureT, ValidatableT> List<Validator<ValidatableT, FailureT>> liftAllThrowable(
            List<ThrowableValidator<ValidatableT, FailureT>> toBeLiftedFns, Function1<Throwable, FailureT> throwableMapper) {
        return toBeLiftedFns.map(toBeLifted -> liftThrowable(toBeLifted, throwableMapper));
    }

}
