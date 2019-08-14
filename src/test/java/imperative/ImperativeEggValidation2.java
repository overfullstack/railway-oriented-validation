package imperative;

import common.DataSet;
import domain.Egg;
import domain.validation.ValidationFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static common.DataSet.getExpectedEggValidationResults;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_1;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_2;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_CHILD_3;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_PARENT_3;
import static imperative.Operations.simpleOperation1;
import static imperative.Operations.throwableNestedOperation3;
import static imperative.Operations.throwableOperation2;
import static imperative.Operations.throwableOperation3;

/**
 * Validations are broken down to separate functions.
 * Problems to solve:
 *  ∙ Octopus Orchestration
 *  ∙ Mutation
 *  ∙ Unit-Testability
 *  ∙ Management of Validation Order
 *  ∙ Complexity
 *  ∙ Chaos
 */
public class ImperativeEggValidation2 {
    @Test
    void octopusOrchestrator() { // This Octopus turns into a monster someday.
        var eggList = DataSet.getEggCarton();
        // R3 - Trying to be the owner of all state.
        var badEggFailureBucketMap = new HashMap<Integer, ValidationFailure>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) { // R-1: Iterate through eggs
            var eggToBeValidated = iterator.next();

            // Global state is dangerous. badEggFailureBucketMap and iterator being passed to each and every function, difficult to keep track of how they are being mutated during debugging.
            if (!validate1(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue; // R-2: Manage fail-fast
            }

            // Adding a new validation in-between requires you to understand all the validations above and below, which slows down development and makes it prone to bugs.
            if (!validate2(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue;
            }

            validateChild3(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated);
        }

        for (var entry : badEggFailureBucketMap.entrySet()) {
            System.out.println(entry);
        }

        Assertions.assertEquals(getExpectedEggValidationResults(), badEggFailureBucketMap);
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

    private static boolean validateChild3(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
        if (!validateParent3(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
            return false;
        }
        var yolkTobeValidated = eggToBeValidated.getYolk();
        try {
            if (!throwableNestedOperation3(yolkTobeValidated)) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_CHILD_3);
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
            if (!throwableOperation3(eggToBeValidated)) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_PARENT_3);
                return false;
            }
        } catch (Exception e) {
            iterator.remove();
            badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            return false;
        }
        return true;
    }

}
