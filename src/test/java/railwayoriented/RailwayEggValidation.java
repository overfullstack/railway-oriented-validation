package railwayoriented;

import domain.ImmutableEgg;
import domain.ValidationFailure;
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
 * This class contains validations as functions.
 */
public class RailwayEggValidation {
    @Test
    void plainOldImperative() {
        List<UnaryOperator<Validation<ValidationFailure, ImmutableEgg>>> validationList =
                List.of(RailwayEggValidation::validate1, RailwayEggValidation::validate2, RailwayEggValidation::validateParent31);
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
                .map(RailwayEggValidation::validate1)
                .map(RailwayEggValidation::validate2)
                .map(RailwayEggValidation::validate3)
                .forEach(System.out::println);

        // TODO 2019-07-03 gakshintala: after standardization add assertion, to check every time you make changes
    }

    @Test
    void railwayCodeElegant() {
        List<UnaryOperator<Validation<ValidationFailure, ImmutableEgg>>> validationList =
                List.of(RailwayEggValidation::validate1, RailwayEggValidation::validate2, RailwayEggValidation::validate3);
        ImmutableEgg.getEggCarton()
                .map(Validation::<ValidationFailure, ImmutableEgg>valid)
                .map(eggToBeValidated -> validationList.foldLeft(eggToBeValidated, (validatedEgg, currentValidation) -> currentValidation.apply(validatedEgg)))
                .forEach(System.out::println);
    }

    private static Validation<ValidationFailure, ImmutableEgg> validate1(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .filter(Operations::simpleOperation1)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_1));
    }

    private static Validation<ValidationFailure, ImmutableEgg> validate2(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation2)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_2))
                .flatMap(ignore -> validatedEgg);
    }
   
    private static Validation<ValidationFailure, ImmutableEgg> validateParent31(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validatedEgg
                .map(Operations::throwableOperation3)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_31))
                .flatMap(ignore -> validatedEgg);
    }

    private static Validation<ValidationFailure, ImmutableEgg> validate3(Validation<ValidationFailure, ImmutableEgg> validatedEgg) {
        return validateParent31(validatedEgg)
                .map(ImmutableEgg::getYolk)
                .map(Operations::throwableAndNestedOperation31)
                .flatMap(tryResult -> tryResult.toValidation(cause -> ValidationFailure.withErrorMessage(cause.getMessage())))
                .filter(Boolean::booleanValue)
                .getOrElse(() -> Validation.invalid(VALIDATION_FAILURE_32))
                .flatMap(ignore -> validatedEgg);
    }

}
