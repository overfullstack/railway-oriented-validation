package railwayoriented;

import domain.Egg;
import domain.Yolk;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

import static domain.Color.GOLD;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;

@UtilityClass
class Operations {

    static boolean simpleOperation1(Egg eggTobeValidated) {
        return eggTobeValidated != null;
    }

    static Try<Boolean> throwableOperation2(Egg eggTobeValidated) throws IllegalArgumentException {
        return Try.of(() -> {
            if (eggTobeValidated.getDaysToHatch() >= Egg.MAX_DAYS_TO_HATCH) {
                throw new IllegalArgumentException("throwableOperation2: Might never hatch 😕");
            } else {
                return eggTobeValidated.getDaysToHatch() >= 5;
            }
        });
    }

    static Try<Boolean> throwableOperation3(Egg eggTobeValidated) throws IllegalArgumentException {
        return Try.of(() -> {
            if (eggTobeValidated.getDaysToHatch() <= 0) {
                throw new IllegalArgumentException("throwableValidation31: Chicken might already be out! 🐣");
            } else {
                return eggTobeValidated.getDaysToHatch() <= 10;
            }
        });
    }

    // TODO 2019-07-04 gakshintala: Place this validation into a different class say YolkValidation and demonstrate how it can be shared by different validators.
    static Try<Boolean> throwableAndNestedOperation31(Yolk yolkTobeValidated) throws IllegalArgumentException {
        return Try.of(() -> {
            if (yolkTobeValidated.getCondition() == BAD) {
                throw new IllegalArgumentException("throwableAndNestedOperation32: Yolk is Bad 🤮");
            } else {
                return yolkTobeValidated.getColor() == GOLD || yolkTobeValidated.getColor() == YELLOW;
            }
        });
    }
}
