package app.declarative;

import app.domain.ImmutableEgg;
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
 * Operations used by Functional Validations.
 */
@UtilityClass
class Operations {

    static boolean simpleOperation1(ImmutableEgg eggToBeValidated) {
        return eggToBeValidated != null;
    }

    // These check positive cases, true = success ; false = ValidationFailure
    //-----------------------|5----------------|15-------------------|21-------------------
    //----About to hatch----|------Valid-------|--Might never hatch--|--Too late to hatch--|

    static Boolean throwableOperation2(ImmutableEgg eggToBeValidated) {
        if (eggToBeValidated.daysToHatch() >= MAX_DAYS_TO_HATCH) { // Might never hatch
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_OPERATION_2);
        } else {
            return eggToBeValidated.daysToHatch() <= MIN_DAYS_TO_HATCH; // Otherwise, Too late to hatch
        }
    }

    static Boolean throwableOperation3(ImmutableEgg eggToBeValidated) {
        if (eggToBeValidated.daysToHatch() <= 0) {
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_VALIDATION_3);
        } else {
            return eggToBeValidated.daysToHatch() >= MAX_DAYS_TO_SHIP; // Otherwise, Might hatch too soon
        }
    }


    static Boolean throwableNestedOperation3(Yolk yolkTobeValidated) {
        if (yolkTobeValidated == null) {
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_NESTED_OPERATION_31);
        } else if (yolkTobeValidated.condition() == BAD) {
            throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_NESTED_OPERATION_32);
        } else {
            return yolkTobeValidated.color() == GOLD || yolkTobeValidated.color() == YELLOW;
        }
    }
}
