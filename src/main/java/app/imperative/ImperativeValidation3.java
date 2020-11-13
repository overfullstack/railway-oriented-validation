package app.imperative;

import app.domain.Egg;
import app.domain.validation.ValidationFailure;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static app.domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3;
import static app.domain.validation.ValidationFailures.NONE;
import static app.domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1;
import static app.domain.validation.ValidationFailures.TOO_LATE_TO_HATCH_2;
import static app.domain.validation.ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3;
import static app.imperative.Rules.simpleOperation1;
import static app.imperative.Rules.throwableNestedOperation3;
import static app.imperative.Rules.throwableOperation2;
import static app.imperative.Rules.throwableOperation3;

/**
 * Validations are broken down to separate functions.
 */
@Slf4j
@UtilityClass
public class ImperativeValidation3 {
    private static void updateFailureForEgg(Iterator<Egg> iterator, int eggIndex, Map<Integer, ValidationFailure> badEggFailureBucketMap, ValidationFailure ValidationFailure) {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, ValidationFailure);
    }

    private static ValidationFailure validate1(Egg eggToBeValidated) {
        if (!simpleOperation1(eggToBeValidated)) {
            return NO_EGG_TO_VALIDATE_1;
        }
        return NONE;
    }

    private static ValidationFailure validate2(Egg eggToBeValidated) {
        try {
            if (!throwableOperation2(eggToBeValidated)) {
                return TOO_LATE_TO_HATCH_2;
            }
        } catch (Exception e) {
            return ValidationFailure.withErrorMessage(e.getMessage());
        }
        return NONE;
    }

    private static ValidationFailure validateParent3(Egg eggToBeValidated) {
        try {
            if (!throwableOperation3(eggToBeValidated)) {
                return ABOUT_TO_HATCH_P_3;
            }
        } catch (Exception e) {
            return ValidationFailure.withErrorMessage(e.getMessage());
        }
        return NONE;
    }

    private static ValidationFailure validateChild3(Egg eggToBeValidated) {
        val parentValidationFailure = validateParent3(eggToBeValidated);
        if (parentValidationFailure != NONE) {
            return parentValidationFailure;
        }
        var yolkTobeValidated = eggToBeValidated.getYolk();
        try {
            if (!throwableNestedOperation3(yolkTobeValidated)) {
                return YOLK_IS_IN_WRONG_COLOR_C_3;
            }
        } catch (Exception e) {
            return ValidationFailure.withErrorMessage(e.getMessage());
        }
        return NONE;
    }

    /**
     * This Octopus turns into a monster someday
     */
    static HashMap<Integer, ValidationFailure> validateEggCartonImperatively(List<Egg> eggList) {
        var badEggFailureBucketMap = new HashMap<Integer, ValidationFailure>();
        var eggIndex = 0;
        for (var iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) {
            var eggToBeValidated = iterator.next();

            // Global state is dangerous. badEggFailureBucketMap and iterator being passed to each and every function, difficult to keep track of how they are being mutated during debugging.
            val validate1Failure = validate1(eggToBeValidated);
            if (validate1Failure != NONE) {
                updateFailureForEgg(iterator, eggIndex, badEggFailureBucketMap, validate1Failure);
                continue;
            }

            // Adding a new validation in-between requires you to understand all the validations above and below, which slows down development and makes it prone to bugs.
            val validate2Failure = validate2(eggToBeValidated);
            if (validate2Failure != NONE) {
                updateFailureForEgg(iterator, eggIndex, badEggFailureBucketMap, validate2Failure);
                continue;
            }

            val validate3Failure = validateChild3(eggToBeValidated);
            if (validate3Failure != NONE) {
                updateFailureForEgg(iterator, eggIndex, badEggFailureBucketMap, validate3Failure);
                continue;
            }
        }

        for (var entry : badEggFailureBucketMap.entrySet()) {
            log.info(entry.toString());
        }
        return badEggFailureBucketMap;
    }

}
