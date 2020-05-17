package app.imperative;

import app.domain.Egg;
import app.domain.Yolk;
import app.domain.validation.ThrowableMsgs;
import lombok.experimental.UtilityClass;

import static app.common.Constants.MAX_DAYS_TO_HATCH;
import static app.common.Constants.MAX_DAYS_TO_SHIP;
import static app.common.Constants.MIN_DAYS_TO_HATCH;
import static app.domain.Color.GOLD;
import static app.domain.Color.YELLOW;
import static app.domain.Condition.BAD;

/**
 * Operations used by Imperative Validations.
 */
@UtilityClass
class Operations {

    static boolean simpleOperation1(Egg eggToBeValidated) {
        return eggToBeValidated != null;
    }

    // These check positive cases, true = success ; false = ValidationFailure
    //-----------------------|5----------------|15-------------------|21-------------------
    //----About to hatch----|------Valid-------|--Might never hatch--|--Too late to hatch--|

    static boolean throwableOperation2(Egg eggToBeValidated) {
        if (eggToBeValidated.getDaysToHatch() >= MAX_DAYS_TO_HATCH) {
            // Unchecked Exception. Caller would have no clue of this.
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_OPERATION_2);
        } else {
            return eggToBeValidated.getDaysToHatch() <= MIN_DAYS_TO_HATCH; // Otherwise, Too late to hatch
        }
    }

    static boolean throwableOperation3(Egg eggToBeValidated) {
        if (eggToBeValidated.getDaysToHatch() <= 0) {
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_VALIDATION_3);
        } else {
            return eggToBeValidated.getDaysToHatch() >= MAX_DAYS_TO_SHIP; // Otherwise, Might hatch too soon
        }
    }

    static boolean throwableNestedOperation3(Yolk yolkTobeValidated) {
        if (yolkTobeValidated == null) {
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_NESTED_OPERATION_31);
        } else if (yolkTobeValidated.condition() == BAD) {
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_NESTED_OPERATION_32);
        } else {
            return yolkTobeValidated.color() == GOLD || yolkTobeValidated.color() == YELLOW;
        }
    }
}
