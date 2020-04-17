package algebra;

import algebra.types.ThrowableValidator;
import algebra.types.Validator;
import io.vavr.CheckedFunction1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.function.Function;

import static io.vavr.CheckedFunction1.liftTry;

/**
 * gakshintala created on 4/15/20.
 */
@UtilityClass
public class Dsl {

    public static <FailureT, ValidatableT> Validator<ValidatableT, FailureT> lift(
            Function<ValidatableT, FailureT> toBeLifted, FailureT none) {
        return toBeValidated -> toBeValidated.flatMap(validatable -> {
            val result = toBeLifted.apply(validatable);
            return result != none ? Either.left(result) : Either.right(validatable);
        });
    }

    public static <FailureT, ValidatableT> List<Validator<ValidatableT, FailureT>> liftAll(
            List<Function<ValidatableT, FailureT>> toBeLiftedFns, FailureT none) {
        return toBeLiftedFns.map(toBeLifted -> lift(toBeLifted, none));
    }

    public static <FailureT, ValidatableT> Validator<ValidatableT, FailureT> liftThrowable(
            CheckedFunction1<ValidatableT, FailureT> toBeLifted, FailureT none, Function<Throwable, FailureT> throwableMapper) {
        return liftThrowable(validatable -> {
            val result = liftTry(toBeLifted).apply(validatable).toEither();
            return result.fold(
                    throwable -> Either.left(Either.left(throwable)),
                    failure -> failure != none ? Either.left(Either.right(failure)) : Either.right(validatable));

        }, throwableMapper);
    }

    public static <FailureT, ValidatableT> List<Validator<ValidatableT, FailureT>> liftAllThrowable(
            List<CheckedFunction1<ValidatableT, FailureT>> toBeLiftedFns, FailureT none, Function<Throwable, FailureT> mapper) {
        return toBeLiftedFns.map(toBeLifted -> liftThrowable(toBeLifted, none, mapper));
    }

    private static <FailureT, ValidatableT> Validator<ValidatableT, FailureT> liftThrowable(
            ThrowableValidator<ValidatableT, FailureT> throwableValidator, Function<Throwable, FailureT> throwableMapper) {
        return validatable -> validatable
                .flatMap(egg -> {
                    val apply = throwableValidator.apply(egg);
                    return fold(apply, throwableMapper);
                });
    }

    private static <FailureT, ValidatableT> Either<FailureT, ValidatableT> fold(
            Either<Either<Throwable, FailureT>, ValidatableT> throwableFailure, Function<Throwable, FailureT> throwableMapper) {
        return throwableFailure.fold(
                throwableOrFailure ->
                        Either.left(throwableOrFailure.fold(
                                throwableMapper,
                                Function.identity()
                        )),
                Either::right);
    }
    
}
