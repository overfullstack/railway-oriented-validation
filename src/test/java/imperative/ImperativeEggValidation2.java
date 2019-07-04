package imperative;

import org.junit.jupiter.api.Test;
import domain.Egg;
import domain.ValidationFailure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static domain.ValidationFailure.VALIDATION_FAILURE_1;
import static domain.ValidationFailure.VALIDATION_FAILURE_2;
import static domain.ValidationFailure.VALIDATION_FAILURE_32;
import static imperative.Operations.simpleOperation1;
import static imperative.Operations.throwableAndNestedOperation32;
import static imperative.Operations.throwableOperation2;
import static imperative.Operations.throwableOperation31;

/**
 * Validations are broken down to separate functions.
 */
public class ImperativeEggValidation2 {
    @Test // This Octopus turns into a monster someday
    void octopusOrchestrator() {
        var eggList = Egg.getEggCarton();

        Map<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggTobeValidated = iterator.next();
            
            // Global state is dangerous. badEggFailureBucketMap and iterator being passed to each and every function, difficult to keep track of how they are being mutated during debugging.
            if (!validate1(badEggFailureBucketMap, eggIndex, iterator, eggTobeValidated)) continue;

            // Adding a new validation in-between requires you to understand all the validations above and below, which slows down development and makes it prone to bugs.
            if (!validate2(badEggFailureBucketMap, eggIndex, iterator, eggTobeValidated)) continue;
            
            validate3(badEggFailureBucketMap, eggIndex, iterator, eggTobeValidated);
        }

        for (var entry : badEggFailureBucketMap.entrySet()) {
            System.out.println(entry);
        }
    }

    // Can't ensure the uniformity of signature among validations, which can increase the complexity. 
    private boolean validate1(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggTobeValidated) {
        if (!simpleOperation1(eggTobeValidated)) {
            iterator.remove();
            badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_1);
            return false;
        }
        return true;
    }

    private boolean validate2(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggTobeValidated) {
        try {
            if (!throwableOperation2(eggTobeValidated)) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
            }
        } catch (Exception e) {
            iterator.remove();
            badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            return false;
        }
        return true;
    }

    
    private void validate3(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggTobeValidated) {
        // pyramid of doom!
        try {
            if (throwableOperation31(eggTobeValidated)) {
                var yolkTobeValidated = eggTobeValidated.getYolk();
                if (yolkTobeValidated != null) { // Every nested object needs a null-check.
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

}
