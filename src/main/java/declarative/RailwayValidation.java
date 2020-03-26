package declarative;

import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import static domain.validation.ValidationFailureConstants.ABOUT_TO_HATCH_P_3;
import static domain.validation.ValidationFailureConstants.NO_EGG_TO_VALIDATE_1;
import static domain.validation.ValidationFailureConstants.TOO_LATE_TO_HATCH_2;
import static domain.validation.ValidationFailureConstants.YOLK_IS_IN_WRONG_COLOR_C_3;

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
    public static Either<ValidationFailure, ImmutableEgg> validate1(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .filter(Operations::simpleOperation1)
                .getOrElse(() -> Either.left(NO_EGG_TO_VALIDATE_1));
    }

    public static Either<ValidationFailure, ImmutableEgg> validate2(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation2)
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(TOO_LATE_TO_HATCH_2))
                .flatMap(ignore -> validatedEgg);
    }

    private static Either<ValidationFailure, ImmutableEgg> validateParent3(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation3)
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
                .flatMap(ignore -> validatedEgg);
    }

    public static Either<ValidationFailure, Yolk> validateChild31(Either<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(Operations::throwableNestedOperation3)
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
                .flatMap(ignore -> validatedYolk);
    }

    public static Either<ValidationFailure, Yolk> validateChild32(Either<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(Operations::throwableNestedOperation3)
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
                .flatMap(ignore -> validatedYolk);
    }

    private static Either<ValidationFailure, ImmutableEgg> validateParent41(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation3)
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
                .flatMap(ignore -> validatedEgg);
    }

    private static Either<ValidationFailure, ImmutableEgg> validateParent42(Either<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation3)
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
                .flatMap(ignore -> validatedEgg);
    }

    public static Either<ValidationFailure, Yolk> validateChild4(Either<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(Operations::throwableNestedOperation3)
                .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
                .flatMap(ignore -> validatedYolk);
    }

}
