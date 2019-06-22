package railwayoriented;

import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;
import common.Egg;
import common.ValidationFailure;

import static common.ValidationFailure.VALIDATION_FAILURE_1;
import static common.ValidationFailure.VALIDATION_FAILURE_2;
import static common.ValidationFailure.VALIDATION_FAILURE_31;
import static common.ValidationFailure.VALIDATION_FAILURE_32;

public class RailwayEggValidation {
    @Test
    void railwayCode() {
        var validationStream = Egg.getEggCarton().stream()
                .map(Validation::<ValidationFailure, Egg>valid)
                .map(RailwayEggValidation::validate1)
                .map(RailwayEggValidation::validate2)
                .map(RailwayEggValidation::validate3);
        validationStream.forEach(System.out::println);
    }

    private static Validation<ValidationFailure, Egg> validate1(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .filter(Validations::simpleValidation1)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));
    }

    private static Validation<ValidationFailure, Egg> validate2(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .flatMap(eggTobeValidated -> Validations.throwableValidation2(eggTobeValidated)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
                .flatMap(ignore -> validatedEgg);
    }

    private static Validation<ValidationFailure, Egg> validate3(Validation<ValidationFailure, Egg> validatedEgg) {
        return validatedEgg
                .flatMap(eggTobeValidated -> Validations.throwableValidation31(eggTobeValidated)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_31))
                .flatMap(ignore -> validatedEgg)
                .map(Egg::getYellow)
                .flatMap(yellowTobeValidated -> Validations.throwableAndNestedValidation32(yellowTobeValidated)
                        .toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_32))
                .flatMap(ignore -> validatedEgg);
    }

}
