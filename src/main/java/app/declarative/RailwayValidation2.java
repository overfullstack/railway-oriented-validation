package app.declarative;

import algebra.types.Validator;
import app.domain.ImmutableEgg;
import app.domain.Yolk;
import app.domain.validation.ValidationFailure;
import io.vavr.CheckedFunction1;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

import static app.domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3;
import static app.domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1;
import static app.domain.validation.ValidationFailures.TOO_LATE_TO_HATCH_2;
import static app.domain.validation.ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3;
import static io.vavr.CheckedFunction1.liftTry;

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

    public static final Validator<ImmutableEgg, ValidationFailure> validate1Simple = validatedEgg -> validatedEgg
            .filter(Operations::simpleOperation1)
            .getOrElse(() -> Either.left(NO_EGG_TO_VALIDATE_1));

    public static final Validator<ImmutableEgg, ValidationFailure> validate2Throwable = validatedEgg -> validatedEgg
            .map(egg -> liftTry(Operations::throwableOperation2).apply(egg))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage()))) // Trick in the Monad
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(TOO_LATE_TO_HATCH_2))
            .flatMap(ignore -> validatedEgg);

    public static final Validator<ImmutableEgg, ValidationFailure> validateParent3 = validatedEgg -> validatedEgg
            .map(egg -> liftTry(Operations::throwableOperation3).apply(egg))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
            .flatMap(ignore -> validatedEgg);

    public static final Validator<Yolk, ValidationFailure> validateChild31 = validatedYolk -> validatedYolk
            .map(yolk -> liftTry(Operations::throwableNestedOperation3).apply(yolk))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
            .flatMap(ignore -> validatedYolk);

    public static final Validator<Yolk, ValidationFailure> validateChild32 = validatedYolk -> validatedYolk
            .map(yolk -> liftTry(Operations::throwableNestedOperation3).apply(yolk))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
            .flatMap(ignore -> validatedYolk);

    public static final Validator<ImmutableEgg, ValidationFailure> validateParent41 = validatedEgg -> validatedEgg
            .map(egg -> liftTry(Operations::throwableOperation3).apply(egg))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
            .flatMap(ignore -> validatedEgg);

    public static final Validator<ImmutableEgg, ValidationFailure> validateParent42 = validatedEgg -> validatedEgg
            .map(egg -> liftTry(Operations::throwableOperation3).apply(egg))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3))
            .flatMap(ignore -> validatedEgg);

    // Child with multiple Parent Validations
    public static final Validator<Yolk, ValidationFailure> validateChild4 = validatedYolk -> validatedYolk
            .map(yolk -> liftTry(Operations::throwableNestedOperation3).apply(yolk))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3))
            .flatMap(ignore -> validatedYolk);

    public static final Function<ImmutableEgg, ValidationFailure> validate = egg -> TOO_LATE_TO_HATCH_2;

    public static final CheckedFunction1<ImmutableEgg, ValidationFailure> validateThrowable = validatedEgg -> {
        throw new IllegalArgumentException("illegal"); };

}
