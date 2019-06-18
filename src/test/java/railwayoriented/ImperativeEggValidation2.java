package railwayoriented;

import org.junit.jupiter.api.Test;
import railwayoriented.Egg.Yellow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static railwayoriented.Egg.Condition.BAD;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_1;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_2;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_32;

public class ImperativeEggValidation2 {
    @Test
    void cyclomaticCodeBrokenIntoFunctions() {
        var eggList = Egg.getEggCarton();

        Map<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggTobeValidated = iterator.next();
            if (!validate1(badEggFailureBucketMap, eggIndex, iterator, eggTobeValidated)) continue;

            if (!validate2(badEggFailureBucketMap, eggIndex, iterator, eggTobeValidated)) continue;

            validate3(badEggFailureBucketMap, eggIndex, iterator, eggTobeValidated);
        }

        System.out.println(badEggFailureBucketMap);
    }

    private void validate3(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggTobeValidated) {
        try { // Inter-dependent validations
            if (throwableValidation31(eggTobeValidated)) {
                var yellowTobeValidated = eggTobeValidated.getYellow();
                if (yellowTobeValidated != null) { // Nested-if for null checking nested objects
                    try {
                        if (!throwableAndNestedValidation32(yellowTobeValidated)) {
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

    private boolean throwableAndNestedValidation32(Yellow yellowTobeValidated) throws Exception {
        if (yellowTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException("Yellow is Bad");
        } else {
            return false;
        }
    }

    private boolean throwableValidation31(Egg eggTobeValidated) throws Exception {
        return false;
    }

    private boolean validate2(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggTobeValidated) {
        try {
            if (!throwableValidation2(eggTobeValidated)) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
            }
        } catch (Exception e) { // Repetition of same logic for exception handling
            iterator.remove();
            badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            return false;
        }
        return true;
    }

    private boolean throwableValidation2(Egg eggTobeValidated) throws Exception {
        return false;
    }

    private boolean validate1(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex, Iterator<Egg> iterator, Egg eggTobeValidated) {
        if (!simpleValidation1(eggTobeValidated)) {
            iterator.remove(); // Mutation
            // How do you cleanly map validation-failure to which validation-method failed?
            badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_1);
            return false;
        }
        return true;
    }

    private boolean simpleValidation1(Egg eggTobeValidated) {
        return true;
    }

}
