package declarative;

import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ThrowableMsgs;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

import static common.Config.MAX_DAYS_TO_HATCH;
import static common.Config.MAX_DAYS_TO_SHIP;
import static common.Config.MIN_DAYS_TO_HATCH;
import static domain.Color.GOLD;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;

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
    
    static Try<Boolean> throwableOperation2(ImmutableEgg eggToBeValidated) {
        return Try.of(() -> {
            if (eggToBeValidated.getDaysToHatch() >= MAX_DAYS_TO_HATCH) { // Might never hatch
                throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_OPERATION_2);
            } else {
                return eggToBeValidated.getDaysToHatch() <= MIN_DAYS_TO_HATCH; // Otherwise, Too late to hatch
            }
        });
    }

    static Try<Boolean> throwableOperation3(ImmutableEgg eggToBeValidated) {
        return Try.of(() -> {
            if (eggToBeValidated.getDaysToHatch() <= 0) {
                throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_VALIDATION_3);
            } else {
                return eggToBeValidated.getDaysToHatch() >= MAX_DAYS_TO_SHIP; // Otherwise, Might hatch too soon
            }
        });
    }

    static Try<Boolean> throwableNestedOperation3(Yolk yolkTobeValidated) {
        return Try.of(() -> {
            if (yolkTobeValidated == null) {
                throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_NESTED_OPERATION_31);
            } else if (yolkTobeValidated.getCondition() == BAD) {
                throw new IllegalArgumentException(ThrowableMsgs.THROWABLE_NESTED_OPERATION_32);
            } else {
                return yolkTobeValidated.getColor() == GOLD || yolkTobeValidated.getColor() == YELLOW;
            }
        });
    }
}
