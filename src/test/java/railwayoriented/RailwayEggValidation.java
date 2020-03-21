package railwayoriented;

import common.DataSet;
import common.ValidationUtils;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static common.DataSet.getExpectedImmutableEggValidationResults;
import static common.ValidationConfig.EGG_VALIDATION_CHAIN;
import static domain.validation.ValidationFailureConstants.ABOUT_TO_HATCH_P_3;
import static domain.validation.ValidationFailureConstants.NO_EGG_TO_VALIDATE_1;
import static domain.validation.ValidationFailureConstants.TOO_LATE_TO_HATCH_2;
import static domain.validation.ValidationFailureConstants.YOLK_IS_IN_WRONG_COLOR_C_3;

/**
 * This class contains validations as functions.
 * Requirements
 * ∙ Partial Failures
 * Problems solved:
 * ∙ Octopus Orchestrator - 😵 dead
 * ∙ Mutation to Transformation
 * ∙ Unit-Testability - 👍
 * ∙ Complexity - Minimum
 * ∙ Chaos to Order
 */
public class RailwayEggValidation {
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

    @Test
    void declarativeOrchestration() {
        final var validationResults = DataSet.getImmutableEggCarton().stream()
                .map(Either::<ValidationFailure, ImmutableEgg>right)
                .map(eggToBeValidated -> EGG_VALIDATION_CHAIN // this is vavr list
                        /*foldLeft from vavr list*/.foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());

        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    @Test
    void declarativeOrchestrationParallel() {
        final var validationResults = ValidationUtils.getImmutableEggStream(DataSet.getImmutableEggCarton())
                .map(Either::<ValidationFailure, ImmutableEgg>right)
                .map(eggToBeValidated -> EGG_VALIDATION_CHAIN // this is vavr list
                        /*foldLeft from vavr list*/.foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());

        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

}
