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
            .filter(Operations::throwableOperation2)
            .getOrElse(() -> Either.left(TOO_LATE_TO_HATCH_2));

    public static final ThrowableValidator<ImmutableEgg, ValidationFailure> validateParent3 = validatedEgg -> validatedEgg
            .filter(Operations::throwableOperation3)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3));

    public static final ThrowableValidator<Yolk, ValidationFailure> validateChild31 = validatedYolk -> validatedYolk
            .filter(Operations::throwableNestedOperation3)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3));

    /** ----------------------------------- JUST DUPLICATE PLACE-HOLDERS ----------------------------------- **/

    public static final ThrowableValidator<Yolk, ValidationFailure> validateChild32 = validatedYolk -> validatedYolk
            .filter(Operations::throwableNestedOperation3)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3));

    public static final ThrowableValidator<ImmutableEgg, ValidationFailure> validateParent41 = validatedEgg -> validatedEgg
            .filter(Operations::throwableOperation3)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3));

    public static final ThrowableValidator<ImmutableEgg, ValidationFailure> validateParent42 = validatedEgg -> validatedEgg
            .filter(Operations::throwableOperation3)
            .getOrElse(() -> Either.left(ABOUT_TO_HATCH_P_3));

    // Child with multiple Parent Validations
    public static final ThrowableValidator<Yolk, ValidationFailure> validateChild4 = validatedYolk -> validatedYolk
            .filter(Operations::throwableNestedOperation3)
            .getOrElse(() -> Either.left(YOLK_IS_IN_WRONG_COLOR_C_3));

}
