package railwayoriented;

import common.Egg;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

import static common.Egg.Condition.BAD;

@UtilityClass
class Validations {
    static boolean simpleValidation1(Egg eggTobeValidated) {
        return true;
    }

    static Try<Boolean> throwableValidation2(Egg eggTobeValidated) {
        return Try.of(() -> {
            if (eggTobeValidated.getDaysToHatch() >= 5) {
                throw new RuntimeException("throwableValidation2");
            } else {
                return eggTobeValidated.getDaysToHatch() <= 2;
            }
        });
    }

    static Try<Boolean> throwableAndNestedValidation32(Egg.Yellow yellowTobeValidated) {
        return Try.of(() -> {
            if (yellowTobeValidated.getCondition() == BAD) {
                throw new IllegalArgumentException("Yellow is Bad");
            } else {
                return true;
            }
        });
    }

    static Try<Boolean> throwableValidation31(Egg eggTobeValidated) {
        return Try.of(() -> {
            if (eggTobeValidated.getDaysToHatch() <= 0) {
                throw new RuntimeException("throwableValidation31");
            } else {
                return eggTobeValidated.getDaysToHatch() <= 2;
            }
        });
    }
}
