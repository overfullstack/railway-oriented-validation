package imperative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static common.DataSet.EXPECTED_IMPERATIVE_VALIDATION_RESULTS;


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
        var badEggFailureBucketMap = ImperativeValidation2.validateEggCartonImperatively();
        Assertions.assertEquals(EXPECTED_IMPERATIVE_VALIDATION_RESULTS, badEggFailureBucketMap);
    }

}
