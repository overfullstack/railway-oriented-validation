package imperative;

import domain.Egg;
import domain.Yolk;
import domain.validation.ValidationErrorMessages;
import lombok.experimental.UtilityClass;

import static common.Utils.MIN_DAYS_TO_HATCH;
import static domain.Color.GOLD;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static domain.Egg.MAX_DAYS_TO_HATCH;

@UtilityClass
class Operations {
    
    static boolean simpleOperation1(Egg eggToBeValidated) {
        return eggToBeValidated != null;
    }

    static boolean throwableOperation2(Egg eggToBeValidated) {
        if (eggToBeValidated.getDaysToHatch() >= MAX_DAYS_TO_HATCH) {
            // Unchecked Exception. Caller would have no clue of this.
            throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_OPERATION_2);
        } else {
            return eggToBeValidated.getDaysToHatch() <= MIN_DAYS_TO_HATCH;
        }
    }

    static boolean throwableOperation3(Egg eggToBeValidated) {
        if (eggToBeValidated.getDaysToHatch() <= 0) {
            throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_VALIDATION_3);
        } else {
            return eggToBeValidated.getDaysToHatch() >= 5;
        }
    }

    static boolean throwableNestedOperation3(Yolk yolkTobeValidated) {
        if (yolkTobeValidated == null) {
            throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_NESTED_OPERATION_31);
        } else if (yolkTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_NESTED_OPERATION_32);
        } else {
            return yolkTobeValidated.getColor() == GOLD || yolkTobeValidated.getColor() == YELLOW;
        }
    }
}
