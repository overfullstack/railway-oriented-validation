package imperative;

import common.DataSet;
import domain.Egg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
 * Problems:
 * ∙ Octopus Orchestration
 * ∙ Mutation
 * ∙ Unit-Testability
 * ∙ Don't attempt to run in Parallel
 * Major Problems
 * ∙ Management of Validation Order
 * ∙ Complexity
 * 
 * ∙ Chaos
 */
public class ImperativeEggValidation2 {
    @Test
    void octopusOrchestrator() { // This Octopus turns into a monster someday.
        List<Egg> eggList = DataSet.getEggCarton();
        // R3 - Trying to be the owner of all state.
        HashMap<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<Integer, ValidationFailure>();
        int eggIndex = 0;
        for (Iterator<Egg> iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) { // R-1: Iterate through eggs
            var eggToBeValidated = iterator.next();

            // Global state is dangerous. badEggFailureBucketMap and iterator being passed to each and every function, difficult to keep track of how they are being mutated during debugging.
            if (!validate1(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue; // R-2: Manage fail-fast
            }

            // Adding a new validation in-between requires you to understand all the validations above and below, which slows down development and makes it prone to bugs.
            if (!validate2(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue;
            }

            // Parent with multiple Child Validations
            if (!validateParent3(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue;
            }
            
            // Child with multiple Parent Validations
            if (!validateChild4(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
                continue;
            }
        }

        for (Map.Entry<Integer, ValidationFailure> entry : badEggFailureBucketMap.entrySet()) {
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

    // Parent with multiple Child Validations
    private static boolean validateParent3(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
        try {
            if (throwableOperation3(eggToBeValidated)) {
                var yolkTobeValidated = eggToBeValidated.getYolk();
                if (!validateChild31(badEggFailureBucketMap, eggIndex, iterator, yolkTobeValidated)) {
                    return false;
                }
                if (!validateChild32(badEggFailureBucketMap, eggIndex, iterator, yolkTobeValidated)) {
                    return false;
                }
            } else {
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

    private static boolean validateChild31(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Yolk yolkTobeValidated) {
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

    private static boolean validateChild32(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Yolk yolkTobeValidated) {
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

    // Child with multiple Parent Validations
    private static boolean validateChild4(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
        if (!validateParent41(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
            return false;
        }
        if (!validateParent42(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
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

    private static boolean validateParent41(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
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

    private static boolean validateParent42(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggToBeValidated) {
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
