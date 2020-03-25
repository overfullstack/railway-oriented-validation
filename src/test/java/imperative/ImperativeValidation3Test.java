package imperative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static common.DataSet.getExpectedEggValidationResults;

/**
 * Validations are broken down to separate functions.
 */
public class ImperativeValidation3Test {
    /**
     * This Octopus turns into a monster someday
     */
    @Test
    void octopusOrchestrator() {
        var badEggFailureBucketMap = ImperativeValidation3.validateEggCartonImperatively();
        Assertions.assertEquals(getExpectedEggValidationResults(), badEggFailureBucketMap);
    }

}
