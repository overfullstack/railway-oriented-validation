package app.imperative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static app.common.DataSet.EXPECTED_IMPERATIVE_VALIDATION_RESULTS;


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
        Assertions.assertEquals(EXPECTED_IMPERATIVE_VALIDATION_RESULTS, badEggFailureBucketMap);
    }

}
