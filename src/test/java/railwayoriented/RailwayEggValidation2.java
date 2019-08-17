package railwayoriented;

import common.DataSet;
import common.Utils;
import domain.ImmutableEgg;
import domain.validation.ValidationFailure;
import io.vavr.collection.List;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static common.DataSet.VALIDATION_LIST_2;
import static common.DataSet.getExpectedImmutableEggValidationResults;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_1;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_2;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_CHILD_3;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_PARENT_3;

/**
 * This class contains validations as values.
 * Problems solved:
 *  ∙ Octopus Orchestration - 😵 dead
 *  ∙ Mutation to Transformation
 *  ∙ Unit-Testability - 👍
 *  ∙ Management of Validation order - 👍
 *  ∙ Partial Failures
 *  ∙ Complexity - Minimum
 *  ∙ Chaos to Order
 */
public class RailwayEggValidation2 {

    /**
     * ∙ Unidirectional Data flow
     * ∙ Piped Validations like Lego ::
     */
    @Test
    void railwayCode() {
        final var validationResults = DataSet.getImmutableEggCarton().iterator()
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(validate1)
                .map(validate2)
                .map(validateChild3)
                .toList();
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    @Test
    void railwayCodeElegant() {
        final var validationResults = DataSet.getImmutableEggCarton().iterator()
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(eggToBeValidated -> VALIDATION_LIST_2
                        .foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .toList();
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    /**
     * ∙ No need to comprehend every time like nested for-loop
     * ∙ No need to unit tests
     * ∙ Shared vocabulary
     * ∙ Universal vocabulary
     */
    @Test
    void railwayCodeElegantParallel() {
        final var validationResults = Utils.getImmutableEggStream(DataSet.getImmutableEggCarton())
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(eggToBeValidated -> VALIDATION_LIST_2
                        .foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults().toJavaList(), validationResults);
    }

    public static UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validate1 = validatedEgg -> validatedEgg
            .filter(Operations::simpleOperation1)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));

    public static UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validate2 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation2)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
            .flatMap(ignore -> validatedEgg);

    private static UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validateParent3 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_PARENT_3))
            .flatMap(ignore -> validatedEgg);

    public static UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validateChild3 = validatedEgg ->
            validateParent3.apply(validatedEgg)
                    .map(ImmutableEgg::getYolk)
                    .map(Operations::throwableNestedOperation3)
                    .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                    .filter(Boolean::booleanValue)
                    .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_CHILD_3))
                    .flatMap(ignore -> validatedEgg);

}
