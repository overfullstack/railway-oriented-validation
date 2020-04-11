package declarative;

import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.function.UnaryOperator;

import static domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3;
import static domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1;
import static domain.validation.ValidationFailures.TOO_LATE_TO_HATCH_2;
import static domain.validation.ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3;

/**
 * <pre>
 * This class contains validations as values.
 *
 * Requirements
 * ∙ Partial Failures
 *
 * Problems solved:
 * ∙ Octopus Orchestrator - 😵 dead
 * ∙ Mutation to Transformation
 * ∙ Unit-Testability - 👍
 *
 * Results:
 * ∙ Complexity - Minimum
 * ∙ Chaos to Order
 * </pre>
 */
@UtilityClass
public class RailwayValidation2 {

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validate1Simple = validatedEgg -> validatedEgg
            .filter(Operations::simpleOperation1)
            .getOrElse(() -> Either.left(NO_EGG_TO_VALIDATE_1));

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validate2Throwable = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation2)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage()))) // Trick in the Monad
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(TOO_LATE_TO_HATCH_2))
            .flatMap(ignore -> validatedEgg);

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validateParent3 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
            .flatMap(ignore -> validatedEgg);

    public static final UnaryOperator<Either<ValidationFailure, Yolk>> validateChild31 = validatedYolk -> validatedYolk
            .map(Operations::throwableNestedOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
            .flatMap(ignore -> validatedYolk);

    public static final UnaryOperator<Either<ValidationFailure, Yolk>> validateChild32 = validatedYolk -> validatedYolk
            .map(Operations::throwableNestedOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
            .flatMap(ignore -> validatedYolk);

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validateParent41 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
            .flatMap(ignore -> validatedEgg);

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validateParent42 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
            .flatMap(ignore -> validatedEgg);

    // Child with multiple Parent Validations
    public static final UnaryOperator<Either<ValidationFailure, Yolk>> validateChild4 = validatedYolk -> validatedYolk
            .map(Operations::throwableNestedOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
            .flatMap(ignore -> validatedYolk);

}
