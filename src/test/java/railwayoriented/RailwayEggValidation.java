package railwayoriented;

import domain.Egg;
import domain.ValidationFailure;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static domain.ValidationFailure.VALIDATION_FAILURE_1;
import static domain.ValidationFailure.VALIDATION_FAILURE_2;
import static domain.ValidationFailure.VALIDATION_FAILURE_31;
import static domain.ValidationFailure.VALIDATION_FAILURE_32;

public class RailwayEggValidation {
    @Test
    void railwayCode() {
        // TODO 2019-07-03 gakshintala: Use List map instead of Stream
        var validationStream = Egg.getEggCarton().stream()
                .map(Validation::<ValidationFailure, Egg>valid)
                .map(validate1)
                .map(validate2)
                .map(validate3);
        
        // TODO 2019-07-03 gakshintala: after standardization add assertion, to check every time you make changes
        validationStream.forEach(System.out::println);
    }

    private static UnaryOperator<Validation<ValidationFailure, Egg>> validate1 = validatedEgg -> validatedEgg
            .filter(Operations::simpleOperation1)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));

    private static UnaryOperator<Validation<ValidationFailure, Egg>> validate2 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation2)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
            .flatMap(ignore -> validatedEgg);

    private static UnaryOperator<Validation<ValidationFailure, Egg>> validate3 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation31)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_31))
            .flatMap(ignore -> validatedEgg)
            .map(Egg::getYolk)
            .map(Operations::throwableAndNestedOperation32)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_32))
            .flatMap(ignore -> validatedEgg);

}
