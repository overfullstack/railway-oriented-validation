package imperative;

import domain.Egg;
import domain.Yolk;
import lombok.experimental.UtilityClass;

import static domain.Color.GOLD;
import static domain.Condition.BAD;

@UtilityClass
class Operations {
    // TODO 2019-06-30 gakshintala: Standardize these operations.
    static boolean simpleOperation1(Egg eggTobeValidated) {
        return eggTobeValidated != null;
    }

    static boolean throwableOperation2(Egg eggTobeValidated) throws IllegalArgumentException {
        if (eggTobeValidated.getDaysToHatch() >= Egg.MAX_DAYS_TO_HATCH) {
            throw new IllegalArgumentException("throwableOperation2: Might never hatch 😕");
        } else {
            return eggTobeValidated.getDaysToHatch() >= 10;
        }
    }

    static boolean throwableOperation31(Egg eggTobeValidated) throws IllegalArgumentException {
        if (eggTobeValidated.getDaysToHatch() <= 0) {
            throw new IllegalArgumentException("throwableValidation31: Chicken might already be out! 🐣");
        } else {
            return eggTobeValidated.getDaysToHatch() <= 5;
        }
    }

    static boolean throwableAndNestedOperation32(Yolk yolkTobeValidated) throws IllegalArgumentException {
        if (yolkTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException("throwableAndNestedOperation32: Yellow is Bad 👎");
        } else {
            return yolkTobeValidated.getColor() == GOLD;
        }
    }
}
