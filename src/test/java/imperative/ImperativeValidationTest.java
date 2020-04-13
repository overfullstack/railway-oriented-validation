package imperative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static common.DataSet.EXPECTED_IMPERATIVE_VALIDATION_RESULTS;


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
        Assertions.assertEquals(EXPECTED_IMPERATIVE_VALIDATION_RESULTS, badEggFailureBucketMap);
    }

}
