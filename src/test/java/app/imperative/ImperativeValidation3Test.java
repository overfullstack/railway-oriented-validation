package app.imperative;

import static app.common.DataSet.EGG_CARTON;
import static app.common.DataSet.EXPECTED_IMPERATIVE_VALIDATION_RESULTS;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Validations are broken down to separate functions.
 */
public class ImperativeValidation3Test {
  /**
   * This Octopus turns into a monster someday
   */
  @Test
  void octopusOrchestrator() {
    val badEggFailureBucketMap = ImperativeValidation3.validateEggCartonImperatively(
        EGG_CARTON.toJavaList()); // sending copy to use different iterator
    Assertions.assertEquals(EXPECTED_IMPERATIVE_VALIDATION_RESULTS, badEggFailureBucketMap);
  }

}
