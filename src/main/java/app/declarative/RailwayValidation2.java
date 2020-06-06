package app.declarative;

import algebra.types.ThrowableValidator;
import algebra.types.Validator;
import app.domain.ImmutableEgg;
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

    public static final ThrowableValidator<ImmutableEgg, ValidationFailure> validate2Throwable = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation2)
            .filterOrElse(result -> result, ignore -> TOO_LATE_TO_HATCH_2);

    public static final Validator<ImmutableEgg, ValidationFailure> validateParent3 = validatedEgg -> validatedEgg
            .map(egg -> liftTry(Operations::throwableOperation3).apply(egg))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())));

    public static final ThrowableValidator<Yolk, ValidationFailure> validateChild31 = validatedYolk -> validatedYolk
            .map(Operations::throwableNestedOperation3)
            .filterOrElse(result -> result, ignore -> YOLK_IS_IN_WRONG_COLOR_C_3);

    /** ----------------------------------- JUST DUPLICATE PLACE-HOLDERS ----------------------------------- **/

    public static final Validator<Yolk, ValidationFailure> validateChild32 = validatedYolk -> validatedYolk
            .map(yolk -> liftTry(Operations::throwableNestedOperation3).apply(yolk))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filterOrElse(result -> result, ignore -> YOLK_IS_IN_WRONG_COLOR_C_3);

    public static final Validator<ImmutableEgg, ValidationFailure> validateParent41 = validatedEgg -> validatedEgg
            .map(egg -> liftTry(Operations::throwableOperation3).apply(egg))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filterOrElse(result -> result, ignore -> ABOUT_TO_HATCH_P_3);

    public static final Validator<ImmutableEgg, ValidationFailure> validateParent42 = validatedEgg -> validatedEgg
            .map(egg -> liftTry(Operations::throwableOperation3).apply(egg))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filterOrElse(result -> result, ignore -> TOO_LATE_TO_HATCH_2);

    // Child with multiple Parent Validations
    public static final Validator<Yolk, ValidationFailure> validateChild4 = validatedYolk -> validatedYolk
            .map(yolk -> liftTry(Operations::throwableNestedOperation3).apply(yolk))
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filterOrElse(result -> result, ignore -> YOLK_IS_IN_WRONG_COLOR_C_3);

}
