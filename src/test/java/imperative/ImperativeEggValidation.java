package imperative;

import domain.Egg;
import domain.ValidationFailure;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static domain.ValidationFailure.VALIDATION_FAILURE_1;
import static domain.ValidationFailure.VALIDATION_FAILURE_2;
import static domain.ValidationFailure.VALIDATION_FAILURE_32;
import static imperative.Operations.simpleOperation1;
import static imperative.Operations.throwableAndNestedOperation32;
import static imperative.Operations.throwableOperation2;
import static imperative.Operations.throwableOperation31;

public class ImperativeEggValidation {
    @Test
    void cyclomaticCode() {
        var eggList = Egg.getEggCarton();
        Map<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggTobeValidated = iterator.next();
            if (!simpleOperation1(eggTobeValidated)) {
                iterator.remove(); // Mutation
                // How can you cleanly map validation-failure to which validation-method failed?
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_1);
                continue;
            }
            try {
                if (!throwableOperation2(eggTobeValidated)) {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
                }
            } catch (Exception e) { // Repetition of same logic for exception handling
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                continue;
            }
            try { // Inter-dependent validations
                if (throwableOperation31(eggTobeValidated)) {
                    var yolkTobeValidated = eggTobeValidated.getYolk();
                    if (yolkTobeValidated != null) { // Nested-if for null checking nested objects
                        try {
                            if (!throwableAndNestedOperation32(yolkTobeValidated)) {
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
        for (var entry : badEggFailureBucketMap.entrySet()) {
            System.out.println(entry);
        }
    }

}
