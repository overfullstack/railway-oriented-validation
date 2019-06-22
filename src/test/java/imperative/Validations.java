package imperative;

import common.Egg;
import lombok.experimental.UtilityClass;

import static common.Egg.Condition.BAD;

@UtilityClass
class Validations {

    static boolean throwableAndNestedValidation32(Egg.Yellow yellowTobeValidated) throws Exception {
        if (yellowTobeValidated.getCondition() == BAD) {
            throw new IllegalArgumentException("Yellow is Bad");
        } else {
            return true;
        }
    }

    static boolean throwableValidation31(Egg eggTobeValidated) throws Exception {
        return true;
    }

    static boolean throwableValidation2(Egg eggTobeValidated) throws Exception {
        return true;
    }

    static boolean simpleValidation1(Egg eggTobeValidated) {
        return true;
    }
}
