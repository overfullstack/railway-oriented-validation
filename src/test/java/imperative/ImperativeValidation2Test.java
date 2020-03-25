package imperative;

import domain.validation.ValidationFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static common.DataSet.getExpectedEggValidationResults;

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
        HashMap<Integer, ValidationFailure> badEggFailureBucketMap = ImperativeValidation2.validateEggCartonImperatively();

        Assertions.assertEquals(getExpectedEggValidationResults(), badEggFailureBucketMap);
    }

}
