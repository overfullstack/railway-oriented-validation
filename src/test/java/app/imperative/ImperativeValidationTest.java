package app.imperative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static app.common.DataSet.EGG_CARTON;
import static app.common.DataSet.EXPECTED_IMPERATIVE_VALIDATION_RESULTS;


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
        var badEggFailureBucketMap = ImperativeValidation.validateEggCartonImperatively(new ArrayList<>(EGG_CARTON));
        Assertions.assertEquals(EXPECTED_IMPERATIVE_VALIDATION_RESULTS, badEggFailureBucketMap);
    }

}
