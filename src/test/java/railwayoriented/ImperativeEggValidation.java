package railwayoriented;

import org.junit.jupiter.api.Test;
import railwayoriented.Egg.Yellow;

import java.util.HashMap;
import java.util.Map;

import static railwayoriented.Egg.Condition.BAD;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_1;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_2;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_32;

public class ImperativeEggValidation {
    @Test
    void cyclomaticCode() {
        var eggList = Egg.getEggCarton();

        Map<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggTobeValidated = iterator.next();
            if (!simpleValidation1(eggTobeValidated)) {
                iterator.remove(); // Mutation
                // How do you cleanly map validation-failure to which validation-method failed?
                badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_1);
                continue;
            }
            try {
                if (!throwableValidation2(eggTobeValidated)) {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, VALIDATION_FAILURE_2);
                }
            } catch (Exception e) { // Repetition of same logic for exception handling
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                continue;
            }
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
    }

    private boolean throwableAndNestedValidation32(Yellow yellowTobeValidated) throws Exception {
        if (yellowTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException("Yellow is Bad");
        } else {
            return true;
        }
    }

    private boolean throwableValidation31(Egg eggTobeValidated) throws Exception {
        return true;
    }

    private boolean throwableValidation2(Egg eggTobeValidated) throws Exception {
        return true;
    }

    private boolean simpleValidation1(Egg eggTobeValidated) {
        return true;
    }

}
