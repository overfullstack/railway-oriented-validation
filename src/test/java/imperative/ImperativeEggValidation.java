package imperative;

import org.junit.jupiter.api.Test;
import common.Egg;
import common.ValidationFailure;

import java.util.HashMap;
import java.util.Map;

import static common.ValidationFailure.VALIDATION_FAILURE_1;
import static common.ValidationFailure.VALIDATION_FAILURE_2;
import static common.ValidationFailure.VALIDATION_FAILURE_32;

public class ImperativeEggValidation {
    @Test
    void cyclomaticCode() {
        var eggList = Egg.getEggCarton();
        Map<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggTobeValidated = iterator.next();
            if (!Validations.simpleValidation1(eggTobeValidated)) {
                iterator.remove(); // Mutation
                // How do you cleanly map validation-failure to which validation-method failed?
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_1);
                continue;
            }
            try {
                if (!Validations.throwableValidation2(eggTobeValidated)) {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
                }
            } catch (Exception e) { // Repetition of same logic for exception handling
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                continue;
            }
            try { // Inter-dependent validations
                if (Validations.throwableValidation31(eggTobeValidated)) {
                    var yellowTobeValidated = eggTobeValidated.getYellow();
                    if (yellowTobeValidated != null) { // Nested-if for null checking nested objects
                        try {
                            if (!Validations.throwableAndNestedValidation32(yellowTobeValidated)) {
                                iterator.remove();
                                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_32);
                            }
                        } catch (Exception e) {
                            iterator.remove();
                            badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                        }
                    }
                } else {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
                }
            } catch (Exception e) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            }
        }
        // This algorithm is tightly coupled with One-type 'Omega', 
        // We need to repeat the entire algo for another type.
        for (var entry : badEggFailureBucketMap.entrySet()) {
            System.out.println(entry);
        }
    }

}
