package railwayoriented;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static common.Config.EGG_VALIDATION_CHAIN;
import static common.Config.getStreamBySize;
import static common.DataSet.getExpectedImmutableEggValidationResults;
import static common.DataSet.getImmutableEggCarton;
import static declarative.ValidationStrategies.getErrorAccumulationStrategy;
import static declarative.ValidationStrategies.getFailFastStrategy;
import static declarative.ValidationStrategies.runAllValidationsFailFastImperative;

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
        final var eggCarton = getImmutableEggCarton();
        var validationResults = runAllValidationsFailFastImperative(eggCarton, EGG_VALIDATION_CHAIN);
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
        final var validationResults = getImmutableEggCarton().iterator()
                .map(getFailFastStrategy(EGG_VALIDATION_CHAIN))
                .collect(Collectors.toList());
        validationResults.forEach(log::info);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

    @Test
    void declarativeOrchestrationErrorAccumulation() {
        final var validationResultsAccumulated = getImmutableEggCarton().iterator()
                .map(getErrorAccumulationStrategy(EGG_VALIDATION_CHAIN))
                .collect(Collectors.toList());
        validationResultsAccumulated.forEach(log::info);
    }

    /**
     * Will switch to Parallel mode if EggCarton size is above `MAX_SIZE_FOR_PARALLEL`.
     */
    @Test
    void declarativeOrchestrationParallel() {
        final var validationResults = getStreamBySize(getImmutableEggCarton())
                .map(getFailFastStrategy(EGG_VALIDATION_CHAIN))
                .collect(Collectors.toList());
        validationResults.forEach(log::info);
        Assertions.assertEquals(getExpectedImmutableEggValidationResults(), validationResults);
    }

}
