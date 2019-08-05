package railwayoriented;

import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationErrorMessages;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;

import static domain.Color.GOLD;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static common.Utils.MAX_DAYS_TO_HATCH;
import static common.Utils.MIN_DAYS_TO_HATCH;

@UtilityClass
class Operations {

    static boolean simpleOperation1(ImmutableEgg eggToBeValidated) {
        return eggToBeValidated != null;
    }

    static Try<Boolean> throwableOperation2(ImmutableEgg eggToBeValidated) {
        return Try.of(() -> {
            if (eggToBeValidated.getDaysToHatch() >= MAX_DAYS_TO_HATCH) {
                throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_OPERATION_2);
            } else {
                return eggToBeValidated.getDaysToHatch() <= MIN_DAYS_TO_HATCH;
            }
        });
    }

    static Try<Boolean> throwableOperation3(ImmutableEgg eggToBeValidated) {
        return Try.of(() -> {
            if (eggToBeValidated.getDaysToHatch() <= 0) {
                throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_VALIDATION_3);
            } else {
                return eggToBeValidated.getDaysToHatch() >= 5;
            }
        });
    }

    static Try<Boolean> throwableNestedOperation3(Yolk yolkTobeValidated) {
        return Try.of(() -> {
            if (yolkTobeValidated == null) {
                throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_NESTED_OPERATION_31);
            } else if (yolkTobeValidated.getCondition() == BAD) {
                throw new IllegalArgumentException(ValidationErrorMessages.THROWABLE_NESTED_OPERATION_32);
            } else {
                return yolkTobeValidated.getColor() == GOLD || yolkTobeValidated.getColor() == YELLOW;
            }
        });
    }
}
