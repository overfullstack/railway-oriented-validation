package imperative;

import domain.Egg;
import domain.Yolk;
import lombok.experimental.UtilityClass;

import static domain.Color.GOLD;
import static domain.Condition.BAD;
import static domain.Egg.MAX_DAYS_TO_HATCH;

@UtilityClass
class Operations {
    // TODO 2019-06-30 gakshintala: Standardize these operations.
    static boolean simpleOperation1(Egg eggToBeValidated) {
        return eggToBeValidated != null;
    }

    static boolean throwableOperation2(Egg eggToBeValidated) {
        if (eggToBeValidated.getDaysToHatch() >= MAX_DAYS_TO_HATCH) {
            // Unchecked Exception. Caller would have no clue of this.
            throw new IllegalArgumentException("throwableOperation2: Might never hatch 😕"); 
        } else {
            return eggToBeValidated.getDaysToHatch() <= 5;
        }
    }

    static boolean throwableOperation3(Egg eggToBeValidated) {
        if (eggToBeValidated.getDaysToHatch() <= 0) {
            throw new IllegalArgumentException("throwableValidation31: Chicken might already be out! 🐣");
        } else {
            return eggToBeValidated.getDaysToHatch() >= 10;
        }
    }

    static boolean throwableNestedOperation3(Yolk yolkTobeValidated) {
        if (yolkTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException("throwableAndNestedOperation32: Yellow is Bad 👎");
        } else {
            return yolkTobeValidated.getColor() == GOLD;
        }
    }
}
