package imperative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static common.DataSet.getExpectedEggValidationResults;

/**
 * <pre>
 * Problems:
 * ∙ Complexity
 * ∙ Mutation
 * . Non-sharable
 * ∙ Unit-Testability
 * ∙ Validation Jenga
 * </pre>
 */
public class ImperativeValidationTest {
    @Test
    void cyclomaticCode() {
        var badEggFailureBucketMap = ImperativeValidation.validateEggCartonImperatively();
        Assertions.assertEquals(getExpectedEggValidationResults(), badEggFailureBucketMap);
    }

}
