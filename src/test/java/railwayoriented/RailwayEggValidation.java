package railwayoriented;

import common.DataSet;
import common.Utils;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static common.ValidationConfig.EGG_VALIDATION_CHAIN;
import static common.DataSet.getExpectedImmutableEggValidationResults;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_1;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_2;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_CHILD_3;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_PARENT_3;

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
    @Test
    void declarativeOrchestration() {
        final var validationResults = DataSet.getImmutableEggCarton().stream()
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(eggToBeValidated -> EGG_VALIDATION_CHAIN // this is vavr list
    /*foldLeft from vavr list*/.foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());
        
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    @Test
    void declarativeOrchestrationParallel() {
        final var validationResults = Utils.getImmutableEggStream(DataSet.getImmutableEggCarton())
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(eggToBeValidated -> EGG_VALIDATION_CHAIN // this is vavr list
    /*foldLeft from vavr list*/.foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());
        
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    public static Validation<ValidationFailure, ImmutableEgg> validate1(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .filter(Operations::simpleOperation1)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));
    }

    public static Validation<ValidationFailure, ImmutableEgg> validate2(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation2)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
                .flatMap(ignore -> validatedEgg);
    }

    private static Validation<ValidationFailure, ImmutableEgg> validateParent3(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation3)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_PARENT_3))
                .flatMap(ignore -> validatedEgg);
    }

    public static Validation<ValidationFailure, Yolk> validateChild31(Validation<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(Operations::throwableNestedOperation3)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_CHILD_3))
                .flatMap(ignore -> validatedYolk);
    }

    public static Validation<ValidationFailure, Yolk> validateChild32(Validation<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(Operations::throwableNestedOperation3)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_CHILD_3))
                .flatMap(ignore -> validatedYolk);
    }

    private static Validation<ValidationFailure, ImmutableEgg> validateParent41(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation3)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_PARENT_3))
                .flatMap(ignore -> validatedEgg);
    }

    private static Validation<ValidationFailure, ImmutableEgg> validateParent42(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation3)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_PARENT_3))
                .flatMap(ignore -> validatedEgg);
    }

    public static Validation<ValidationFailure, Yolk> validateChild4(Validation<ValidationFailure, Yolk> validatedYolk) {
        return validatedYolk
                .map(Operations::throwableNestedOperation3)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_CHILD_3))
                .flatMap(ignore -> validatedYolk);
    }

}
