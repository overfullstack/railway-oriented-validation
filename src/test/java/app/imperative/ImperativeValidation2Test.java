package app.imperative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static app.common.DataSet.EGG_CARTON;
import static app.common.DataSet.EXPECTED_IMPERATIVE_VALIDATION_RESULTS;


/**
 * <pre>
 * Validations are broken down to separate functions.
 *
 * Problems:
 * ∙ Octopus Orchestration
 * ∙ Mutation
 * ∙ Unit-Testability
 * . Non-sharable
 * ∙ Don't attempt to run in Parallel
 *
 * Major Problems
 * ∙ Management of Validation Order - how-to-do
 * ∙ Complexity
 * ∙ Chaos
 * </pre>
 */
public class ImperativeValidation2Test {
    
    @Test
    void octopusOrchestrator() {
        var badEggFailureBucketMap = ImperativeValidation2.validateEggCartonImperatively(new ArrayList<>(EGG_CARTON)); // Sending a copy to use different iterator
        Assertions.assertEquals(EXPECTED_IMPERATIVE_VALIDATION_RESULTS, badEggFailureBucketMap);
    }

}
