package imperative;

import domain.Egg;
import domain.ValidationFailure;
import domain.Yolk;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static domain.ValidationFailure.VALIDATION_FAILURE_1;
import static domain.ValidationFailure.VALIDATION_FAILURE_2;
import static domain.ValidationFailure.VALIDATION_FAILURE_32;
import static imperative.Operations.simpleOperation1;
import static imperative.Operations.throwableNestedOperation3;
import static imperative.Operations.throwableOperation2;
import static imperative.Operations.throwableOperation3;

/**
 * Validations are broken down to separate functions.
 */
public class ImperativeEggValidation2 {
    @Test // This Octopus turns into a monster someday
    void octopusOrchestrator() {
        var eggList = Egg.getEggCarton();

        var badEggFailureBucketMap = new HashMap<Integer, ValidationFailure>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggToBeValidated = iterator.next();
            
            // Global state is dangerous. badEggFailureBucketMap and iterator being passed to each and every function, difficult to keep track of how they are being mutated during debugging.
            if (!validate1(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue;
            }

            // Adding a new validation in-between requires you to understand all the validations above and below, which slows down development and makes it prone to bugs.
            if (!validate2(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue;
            }

            validateParent3(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated);
        }

        for (var entry : badEggFailureBucketMap.entrySet()) {
            System.out.println(entry);
        }
    }

    // Can't ensure the uniformity of signature among validations, which can increase the complexity. 
    private static boolean validate1(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
        if (!simpleOperation1(eggToBeValidated)) {
            iterator.remove();
            badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_1);
            return false;
        }
        return true;
    }

    private static boolean validate2(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
        try {
            if (!throwableOperation2(eggToBeValidated)) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
                return false;
            }
        } catch (Exception e) {
            iterator.remove();
            badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            return false;
        }
        return true;
    }

    private static boolean validateParent3(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
        try {
            if (throwableOperation3(eggToBeValidated)) {
                var yolkTobeValidated = eggToBeValidated.getYolk();
                validateChild3(badEggFailureBucketMap, eggIndex, iterator, yolkTobeValidated);
            } else {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
                return false;
            }
        } catch (Exception e) {
            iterator.remove();
            badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            return false;
        }
        return true;
    }

    private static boolean validateChild3(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Yolk yolkTobeValidated) {
        if (yolkTobeValidated != null) { // Every nested object needs a null-check.
            try {
                if (!throwableNestedOperation3(yolkTobeValidated)) {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_32);
                    return false;
                }
            } catch (Exception e) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                return false;
            }
        }
        return true;
    }

}
