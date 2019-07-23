package railwayoriented;

import domain.ImmutableEgg;
import domain.ValidationFailure;
import domain.Yolk;
import io.vavr.collection.List;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

import static domain.ValidationFailure.VALIDATION_FAILURE_1;
import static domain.ValidationFailure.VALIDATION_FAILURE_2;
import static domain.ValidationFailure.VALIDATION_FAILURE_31;
import static domain.ValidationFailure.VALIDATION_FAILURE_32;

/**
 * This class contains validations as values.
 */
public class RailwayEggValidation2 {
    public RailwayEggValidation2() {
        super();
    }

    @Test
    void plainOldImperative() {
        var validationList = List.of(validate1, validate2, validate3);
        final var eggCarton = ImmutableEgg.getEggCarton();
        var validationResults = new ArrayList<Validation<ValidationFailure, ImmutableEgg>>();
        for (ImmutableEgg egg : eggCarton) {
            Validation<ValidationFailure, ImmutableEgg> validatedEgg = Validation.valid(egg);
            for (UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validation : validationList) {
                validatedEgg = validation.apply(validatedEgg);
            }
            validationResults.add(validatedEgg); // mutation
        }
        for (Validation<ValidationFailure, ImmutableEgg> validationResult : validationResults) {
            System.out.println(validationResult);
        }
    }

    @Test
    void railwayCode() {
        ImmutableEgg.getEggCarton()
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(validate1)
                .map(validate2)
                .map(validate3)
                .forEach(System.out::println);

        // TODO 2019-07-03 gakshintala: after standardization add assertion, to check every time you make changes
    }

    @Test
    void railwayCodeElegant() {
        var validationList = List.of(validate1, validate2, validate3);
        ImmutableEgg.getEggCarton()
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(eggToBeValidated -> validationList.foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .forEach(System.out::println);
    }

    private static UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validate1 = validatedEgg -> validatedEgg
            .filter(Operations::simpleOperation1)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));

    private static UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validate2 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation2)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
            .flatMap(ignore -> validatedEgg);

    private static UnaryOperator<Validation<ValidationFailure, Yolk>> validate31 = validatedYolk -> validatedYolk
            .map(Operations::throwableAndNestedOperation31)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_32))
            .flatMap(ignore -> validatedYolk);

    private static UnaryOperator<Validation<ValidationFailure, ImmutableEgg>> validate3 = validatedEgg -> validatedEgg
            .map(Operations::throwableOperation3)
            .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
            .filter(Boolean::booleanValue)
            .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_31))
            .flatMap(ignore -> validatedEgg)
            .map(ImmutableEgg::getYolk)
            .map(Validation::<ValidationFailure, Yolk>valid)
            .flatMap(validate31)
            .flatMap(ignore -> validatedEgg);

}
