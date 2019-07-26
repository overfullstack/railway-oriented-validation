package imperative;

import common.DataSet;
import domain.validation.ValidationFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static common.DataSet.getExpectedEggValidationResults;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_1;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_2;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_CHILD_3;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_PARENT_3;
import static imperative.Operations.simpleOperation1;
import static imperative.Operations.throwableNestedOperation3;
import static imperative.Operations.throwableOperation2;
import static imperative.Operations.throwableOperation3;

public class ImperativeEggValidation {
    @Test
    void cyclomaticCode() {
        var eggList = DataSet.getEggCarton();
        var badEggFailureBucketMap = new HashMap<Integer, ValidationFailure>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggToBeValidated = iterator.next();
            if (!simpleOperation1(eggToBeValidated)) {
                iterator.remove(); // Mutation
                // How can you cleanly map validation-failure to which validation-method failed?
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_1);
                continue;
            }
            try {
                if (!throwableOperation2(eggToBeValidated)) {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
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
                            badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_CHILD_3);
                        }
                    } catch (Exception e) {
                        iterator.remove();
                        badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                    }
                } else {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_PARENT_3);
                    continue;
                }
            } catch (Exception e) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            }
        }
        for (var entry : badEggFailureBucketMap.entrySet()) {
            System.out.println(entry);
        }
        Assertions.assertEquals(getExpectedEggValidationResults(), badEggFailureBucketMap);
    }

}
