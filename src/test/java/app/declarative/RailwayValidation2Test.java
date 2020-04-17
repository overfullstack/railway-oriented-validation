package app.declarative;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
    void declarativeOrchestrationFailFast() {
        val validationResults = IMMUTABLE_EGG_CARTON.iterator()
                .map(failFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .collect(Collectors.toList());
        validationResults.forEach(log::info);
        Assertions.assertEquals(EXPECTED_DECLARATIVE_VALIDATION_RESULTS, validationResults);
    }

    @Test
    void declarativeOrchestrationFailFastNonBulk() {
        val validationResult =
                failFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE).apply(IMMUTABLE_EGG_CARTON.get());
        log.info(validationResult);
    }
    
    @Test
    void declarativeOrchestrationErrorAccumulation() {
        val validationResultsAccumulated = IMMUTABLE_EGG_CARTON.iterator()
                .map(accumulationStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .collect(Collectors.toList());
        validationResultsAccumulated.forEach(log::info);
    }

    /**
     * Will switch to Parallel mode if EggCarton size is above `MAX_SIZE_FOR_PARALLEL`.
     */
    @Test
    void declarativeOrchestrationParallel() {
        val validationResults = getStreamBySize(IMMUTABLE_EGG_CARTON)
                .map(failFastStrategy(EGG_VALIDATION_CHAIN, NOTHING_TO_VALIDATE))
                .collect(Collectors.toList());
        validationResults.forEach(log::info);
        Assertions.assertEquals(EXPECTED_DECLARATIVE_VALIDATION_RESULTS, validationResults);
    }

}
