package app.declarative;

import app.domain.ImmutableEgg;
import app.domain.Rules;
import app.domain.Yolk;
import app.domain.validation.ValidationFailure;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import static app.domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3;
import static app.domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1;
import static app.domain.validation.ValidationFailures.TOO_LATE_TO_HATCH_2;
import static app.domain.validation.ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3;
import static io.vavr.CheckedFunction1.liftTry;

/**
 * <pre>
 * This class contains validations as functions.
 *
 * Requirements
 * ∙ Partial Failures
 *
 * Problems solved:
 * ∙ Octopus Orchestrator - 😵 dead
 * ∙ Mutation to Transformation
 * ∙ Unit-Testability - 👍
 * ∙ Complexity - Minimum
 * ∙ Chaos to Order
 * </pre>
 */
@UtilityClass
public class RailwayValidation {
    public static Either<ValidationFailure, ?> validate1(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .filter(Rules::simpleOperation1)
                .getOrElse(() -> Either.left(NO_EGG_TO_VALIDATE_1));
    }

    public static Either<ValidationFailure, ?> validate2(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(egg -> liftTry(Rules::throwableOperation2).apply(egg))
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(TOO_LATE_TO_HATCH_2));
    }

    private static Either<ValidationFailure, ?> validateParent3(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(egg -> liftTry(Rules::throwableOperation3).apply(egg))
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3));
    }

    public static Either<ValidationFailure, ?> validateChild31(Either<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(yolk -> liftTry(Rules::throwableNestedOperation3).apply(yolk))
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3));
    }

    public static Either<ValidationFailure, ?> validateChild32(Either<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(yolk -> liftTry(Rules::throwableNestedOperation3).apply(yolk))
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3));
    }

    private static Either<ValidationFailure, ?> validateParent41(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(egg -> liftTry(Rules::throwableOperation3).apply(egg))
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3));
    }

    private static Either<ValidationFailure, ?> validateParent42(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(egg -> liftTry(Rules::throwableOperation3).apply(egg))
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3));
    }

    public static Either<ValidationFailure, ?> validateChild4(Either<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(yolk -> liftTry(Rules::throwableNestedOperation3).apply(yolk))
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3));
    }

}
