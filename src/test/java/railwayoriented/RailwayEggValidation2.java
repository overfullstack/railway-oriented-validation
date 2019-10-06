package railwayoriented;

import common.DataSet;
import common.Utils;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static common.DataSet.getExpectedImmutableEggValidationResults;
import static common.ValidationConfig.EGG_VALIDATION_CHAIN;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_1;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_2;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_CHILD_3;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_PARENT_3;

/**
 * This class contains validations as values.
 * Requirements
 * ∙ Partial Failures
 * Problems solved:
 * ∙ Octopus Orchestrator - 😵 dead
 * ∙ Mutation to Transformation
 * ∙ Unit-Testability - 👍
 *
 * <p>.
 * ∙ Complexity - Minimum
 * ∙ Chaos to Order
 */
public class RailwayEggValidation2 {

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
     * ∙ No need to comprehend every time, like nested for-loop
     * ∙ No need to unit test
     * ∙ Shared vocabulary
     * ∙ Universal vocabulary
     */
    @Test
    void declarativeOrchestration() {
        final var validationResults = DataSet.getImmutableEggCarton().stream()
                .map(egg -> EGG_VALIDATION_CHAIN // this is a vavr list
                        /*foldLeft from vavr list*/.foldLeft(Either.<ValidationFailure, ImmutableEgg>right(egg),
                                (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    @Test
    void declarativeOrchestrationParallel() {
        final var validationResults = Utils.getImmutableEggStream(DataSet.getImmutableEggCarton())
                .map(egg -> EGG_VALIDATION_CHAIN // this is a vavr list
                        /*foldLeft from vavr list*/.foldLeft(Either.<ValidationFailure, ImmutableEgg>right(egg),
                                (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .collect(Collectors.toList());
        validationResults.forEach(System.out::println);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validate1 = validatedEgg -> validatedEgg
            .filter(Operations::simpleOperation1)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_1));

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validate2 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation2)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage()))) // Trick in the Monad
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_2))
            .flatMap(ignore -> validatedEgg);

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validateParent3 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_PARENT_3))
            .flatMap(ignore -> validatedEgg);

    public static final UnaryOperator<Either<ValidationFailure, Yolk>> validateChild31 = validatedYolk -> validatedYolk
            .map(Operations::throwableNestedOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_CHILD_3))
            .flatMap(ignore -> validatedYolk);

    public static final UnaryOperator<Either<ValidationFailure, Yolk>> validateChild32 = validatedYolk -> validatedYolk
            .map(Operations::throwableNestedOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_CHILD_3))
            .flatMap(ignore -> validatedYolk);

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validateParent41 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_PARENT_3))
            .flatMap(ignore -> validatedEgg);

    public static final UnaryOperator<Either<ValidationFailure, ImmutableEgg>> validateParent42 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_PARENT_3))
            .flatMap(ignore -> validatedEgg);

    // Child with multiple Parent Validations
    public static final UnaryOperator<Either<ValidationFailure, Yolk>> validateChild4 = validatedYolk -> validatedYolk
            .map(Operations::throwableNestedOperation3)
            .flatMap(tryResult -> tryResult.toEither().mapLeft(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Either.left(VALIDATION_FAILURE_CHILD_3))
            .flatMap(ignore -> validatedYolk);

}
