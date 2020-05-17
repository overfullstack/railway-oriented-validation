package app.declarative;

import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static algebra.Strategies.accumulationStrategy;
import static algebra.Strategies.failFastStrategy;
import static algebra.Strategies.runAllValidationsFailFastImperative;
import static app.common.DataSet.EXPECTED_DECLARATIVE_VALIDATION_RESULTS;
import static app.common.DataSet.IMMUTABLE_EGG_CARTON;
import static app.declarative.Config.EGG_VALIDATION_CHAIN;
import static app.declarative.Config.getStreamBySize;
import static app.domain.validation.ValidationFailures.NOTHING_TO_VALIDATE;

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
@Slf4j
public class RailwayValidation2Test {
    /**
     * Again mixing How-to-do from What-to-do.
     */
    @Test
    void plainOldImperativeOrchestration() {
        var validationResults = runAllValidationsFailFastImperative(IMMUTABLE_EGG_CARTON, EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE);
        Assertions.assertEquals(EXPECTED_DECLARATIVE_VALIDATION_RESULTS, validationResults);
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
    void failFast() {
        val validationResults = IMMUTABLE_EGG_CARTON.iterator()
                .map(failFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .collect(Collectors.toList());
        validationResults.forEach(result -> log.info(result.toString()));
        Assertions.assertEquals(EXPECTED_DECLARATIVE_VALIDATION_RESULTS, validationResults);
    }

    @Test
    void failFastNonBulk() {
        val validationResult =
                failFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE).apply(IMMUTABLE_EGG_CARTON.get());
        log.info(validationResult.toString());
    }

    @Test
    void noValidations() {
        val validationResult =
                failFastStrategy(List.empty(), NOTHING_TO_VALIDATE).apply(IMMUTABLE_EGG_CARTON);
        log.info(validationResult.toString());
    }

    @Test
    void errorAccumulation() {
        val validationResultsAccumulated = IMMUTABLE_EGG_CARTON.iterator()
                .map(accumulationStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .collect(Collectors.toList());
        validationResultsAccumulated.forEach(result -> log.info(result.toString()));
    }

    /**
     * Will switch to Parallel mode if EggCarton size is above `MAX_SIZE_FOR_PARALLEL`.
     */
    @Test
    void parallel() {
        val validationResults = getStreamBySize(IMMUTABLE_EGG_CARTON)
                .map(failFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .collect(Collectors.toList());
        validationResults.forEach(result -> log.info(result.toString()));
        Assertions.assertEquals(EXPECTED_DECLARATIVE_VALIDATION_RESULTS, validationResults);
    }

}
