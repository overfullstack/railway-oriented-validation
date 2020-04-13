package imperative;

import common.DataSet;
import domain.Egg;
import domain.validation.ValidationFailure;
import domain.validation.ValidationFailures;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * gakshintala created on 3/26/20.
 */
@Log4j2
@UtilityClass
public class ImperativeValidation {
    static Map<Integer, ValidationFailure> validateEggCartonImperatively() {
        List<Egg> eggList = DataSet.EGG_CARTON;
        HashMap<Integer, ValidationFailure> badEggFailureBucketMap = new HashMap<>();
        int eggIndex = 0;
        for (Iterator<Egg> iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            Egg eggToBeValidated = iterator.next();
            if (!Operations.simpleOperation1(eggToBeValidated)) {
                iterator.remove(); // Mutation
                // How can you cleanly map validation-failure to which validation-method failed?
                badEggFailureBucketMap.put(eggIndex, ValidationFailures.NO_EGG_TO_VALIDATE_1);
                continue;
            }
            try {
                if (!Operations.throwableOperation2(eggToBeValidated)) {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, ValidationFailures.TOO_LATE_TO_HATCH_2);
                    continue;
                }
            } catch (Exception e) { // Repetition of same logic for exception handling
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                continue;
            }
            try { // Inter-dependent validations
                if (Operations.throwableOperation3(eggToBeValidated)) {
                    var yolkTobeValidated = eggToBeValidated.getYolk();
                    try {
                        if (!Operations.throwableNestedOperation3(yolkTobeValidated)) {
                            iterator.remove();
                            badEggFailureBucketMap.put(eggIndex, ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3);
                        }
                    } catch (Exception e) {
                        iterator.remove();
                        badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
                    }
                } else {
                    iterator.remove();
                    badEggFailureBucketMap.put(eggIndex, ValidationFailures.ABOUT_TO_HATCH_P_3);
                    continue;
                }
            } catch (Exception e) {
                iterator.remove();
                badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
            }
        }
        for (Map.Entry<Integer, ValidationFailure> entry : badEggFailureBucketMap.entrySet()) {
            log.info(entry);
        }
        log.info(badEggFailureBucketMap);
        return badEggFailureBucketMap;
    }
}
