package railwayoriented;

import common.DataSet;
import common.ValidationUtils;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static common.DataSet.getExpectedImmutableEggValidationResults;
import static common.ValidationConfig.EGG_VALIDATION_CHAIN;
import static domain.validation.ValidationFailureConstants.ABOUT_TO_HATCH_P_3;
import static domain.validation.ValidationFailureConstants.NO_EGG_TO_VALIDATE_1;
import static domain.validation.ValidationFailureConstants.TOO_LATE_TO_HATCH_2;
import static domain.validation.ValidationFailureConstants.YOLK_IS_IN_WRONG_COLOR_C_3;

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
public class RailwayEggValidation2 {

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

    @Test
    void plainOldImperativeOrchestration() {
        final var eggCarton = DataSet.getImmutableEggCarton();
        var validationResults = new ArrayList<Either<ValidationFailure, ImmutableEgg>>();
        for (ImmutableEgg egg : eggCarton) {
            Either<ValidationFailure, ImmutableEgg> validatedEgg = Either.right(egg);
            for (UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validation : EGG_VALIDATION_CHAIN) {
                validatedEgg = validation.apply(validatedEgg);
            }
            validationResults.add(validatedEgg); // mutation
        }

        for (Either<ValidationFailure, ImmutableEgg> validationResult : validationResults) {
            System.out.println(validationResult);
        }
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    /**
     * <pre>
     * ∙ No need to comprehend every time, like nested for-loop
     * ∙ No need to unit test
     * ∙ Shared vocabulary
     * ∙ Universal vocabulary
     * </pre>
     */
    @Test
    void declarativeOrchestrationFailFast() {
        final var validationResults = DataSet.getImmutableEggCarton().stream()
                .map(egg -> EGG_VALIDATION_CHAIN.foldLeft(Either.<ValidationFailure, ImmutableEgg>right(egg),
                        (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    @Test
    void declarativeOrchestrationErrorAccumulation() {
        final var validationResultsAccumulated = DataSet.getImmutableEggCarton().stream()
                .map(Either::<ValidationFailure, ImmutableEgg>right)
                .map(eggToBeValidated -> EGG_VALIDATION_CHAIN.foldLeft(List.<Either<ValidationFailure, ImmutableEgg>>empty(),
                        (allValidationResults, currentValidation) -> allValidationResults.append(currentValidation.apply(eggToBeValidated))))
                .collect(Collectors.toList());
        validationResultsAccumulated.forEach(System.out::println);
    }

    @Test
    void declarativeOrchestrationParallel() {
        final var validationResults = ValidationUtils.getImmutableEggStream(DataSet.getImmutableEggCarton())
                .map(egg -> EGG_VALIDATION_CHAIN.foldLeft(Either.<ValidationFailure, ImmutableEgg>right(egg),
                        (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());

        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

}
