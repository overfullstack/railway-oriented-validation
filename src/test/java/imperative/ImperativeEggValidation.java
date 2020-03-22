package imperative;

import common.DataSet;
import domain.Egg;
import domain.validation.ValidationFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static common.DataSet.getExpectedEggValidationResults;
import static domain.validation.ValidationFailureConstants.ABOUT_TO_HATCH_P_3;
import static domain.validation.ValidationFailureConstants.NO_EGG_TO_VALIDATE_1;
import static domain.validation.ValidationFailureConstants.TOO_LATE_TO_HATCH_2;
import static domain.validation.ValidationFailureConstants.YOLK_IS_IN_WRONG_COLOR_C_3;
import static imperative.Operations.simpleOperation1;
import static imperative.Operations.throwableNestedOperation3;
import static imperative.Operations.throwableOperation2;
import static imperative.Operations.throwableOperation3;

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
public class ImperativeEggValidation {
    @Test
    void cyclomaticCode() {
        List<Egg> eggList = DataSet.getEggCarton();
        HashMap<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<>();
        int eggIndex = 0;
        for (Iterator<Egg> iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            Egg eggToBeValidated = iterator.next();
            if (!simpleOperation1(eggToBeValidated)) {
                iterator.remove(); // Mutation
                // How can you cleanly map validation-failure to which validation-method failed?
                badEggFailureBucketMap.put(eggIndex, NO_EGG_TO_VALIDATE_1);
                continue;
            }
            try {
                if (!throwableOperation2(eggToBeValidated)) {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, TOO_LATE_TO_HATCH_2);
                    continue;
                }
            } catch (Exception e) { // Repetition of same logic for exception handling
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                continue;
            }
            try { // Inter-dependent validations
                if (throwableOperation3(eggToBeValidated)) {
                    var yolkTobeValidated = eggToBeValidated.getYolk();
                    try {
                        if (!throwableNestedOperation3(yolkTobeValidated)) {
                            iterator.remove();
                            badEggFailureBucketMap.put(eggIndex, YOLK_IS_IN_WRONG_COLOR_C_3);
                        }
                    } catch (Exception e) {
                        iterator.remove();
                        badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                    }
                } else {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, ABOUT_TO_HATCH_P_3);
                    continue;
                }
            } catch (Exception e) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            }
        }
        for (Map.Entry<Integer, ValidationFailure> entry : badEggFailureBucketMap.entrySet()) {
            System.out.println(entry);
        }
        Assertions.assertEquals(getExpectedEggValidationResults(), badEggFailureBucketMap);
    }

}
