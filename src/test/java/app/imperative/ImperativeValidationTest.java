package app.imperative;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static app.common.DataSet.EGG_CARTON;
import static app.common.DataSet.EXPECTED_IMPERATIVE_VALIDATION_RESULTS;


/**
 * <pre>
 * Problems:
 * - Complexity
 * - Mutation
 * . Non-sharable
 * - Unit-Testability
 * - Validation Jenga
 * </pre>
 */
public class ImperativeValidationTest {
    @Test
    void cyclomaticCode() {
        val badEggFailureBucketMap = ImperativeValidation.validateEggCartonImperatively(EGG_CARTON.toJavaList());
        Assertions.assertEquals(EXPECTED_IMPERATIVE_VALIDATION_RESULTS, badEggFailureBucketMap);
    }

}
