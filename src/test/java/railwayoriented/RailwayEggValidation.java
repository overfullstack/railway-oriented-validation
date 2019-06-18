package railwayoriented;

import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;
import railwayoriented.Egg.Yellow;

import static railwayoriented.Egg.Condition.BAD;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_1;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_2;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_31;
import static railwayoriented.ValidationFailure.VALIDATION_FAILURE_32;

public class RailwayEggValidation {
    @Test
    void railwayCode() {
        Egg.getEggCarton().stream()
                .map(Validation::<ValidationFailure, Egg>valid)
                .map(this::validate1)
                .map(this::validate2)
                .map(this::validate3)
                .forEach(System.out::println);
    }

    private Validation<ValidationFailure, Egg> validate1(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .filter(this::simpleValidation1)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));
    }

    private boolean simpleValidation1(Egg eggTobeValidated) {
        return true;
    }

    private Validation<ValidationFailure, Egg> validate2(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .flatMap(eggTobeValidated -> throwableValidation2(eggTobeValidated)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
                .flatMap(ignore -> validatedEgg);
    }

    private Try<Boolean> throwableValidation2(Egg eggTobeValidated) {
        return Try.of(() -> {
            if (eggTobeValidated.getDaysToHatch() >= 5) {
                throw new RuntimeException("throwableValidation2");
            } else {
                return eggTobeValidated.getDaysToHatch() <= 2;
            }
        });
    }

    private Validation<ValidationFailure, Egg> validate3(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .flatMap(eggTobeValidated -> throwableValidation31(eggTobeValidated)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_31))
                .flatMap(ignore -> validatedEgg)
                .map(Egg::getYellow)
                .flatMap(yellowTobeValidated -> throwableAndNestedValidation32(yellowTobeValidated)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_32))
                .flatMap(ignore -> validatedEgg);
    }

    private Try<Boolean> throwableAndNestedValidation32(Yellow yellowTobeValidated) {
        return Try.of(() -> {
            if (yellowTobeValidated.getCondition() == BAD) {
                throw new IllegalArgumentException("Yellow is Bad");
            } else {
                return true;
            }
        });
    }

    private Try<Boolean> throwableValidation31(Egg eggTobeValidated) {
        return Try.of(() -> {
            if (eggTobeValidated.getDaysToHatch() <= 0) {
                throw new RuntimeException("throwableValidation31");
            } else {
                return eggTobeValidated.getDaysToHatch() <= 2;
            }
        });
    }

}
