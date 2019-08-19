package common;

import domain.Egg;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.collection.List;
import io.vavr.control.Validation;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static domain.Color.GOLD;
import static domain.Color.ORANGE;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static domain.Condition.GOOD;
import static domain.validation.ValidationErrorMessages.THROWABLE_NESTED_OPERATION_31;
import static domain.validation.ValidationErrorMessages.THROWABLE_NESTED_OPERATION_32;
import static domain.validation.ValidationErrorMessages.THROWABLE_OPERATION_2;
import static domain.validation.ValidationErrorMessages.THROWABLE_VALIDATION_3;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_1;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_2;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_CHILD_3;
import static domain.validation.ValidationFailureConstants.VALIDATION_FAILURE_PARENT_3;
import static railwayoriented.RailwayEggValidation2.validate1;
import static railwayoriented.RailwayEggValidation2.validate2;
import static railwayoriented.RailwayEggValidation2.validateChild31;
import static railwayoriented.RailwayEggValidation2.validateChild32;
import static railwayoriented.RailwayEggValidation2.validateChild4;
import static railwayoriented.RailwayEggValidation2.validateParent3;
import static railwayoriented.RailwayEggValidation2.validateParent41;
import static railwayoriented.RailwayEggValidation2.validateParent42;

public class DataSet {
    public static java.util.List<Egg> getEggCarton() {
        var eggCarton = new ArrayList<Egg>(); // Using arrayList because list prepared with List.of() throws exception when iterator.remove() is performed on them.
        eggCarton.add(null); // No egg to validate
        eggCarton.add(new Egg(1, new Yolk(GOOD, GOLD))); // About to hatch
        eggCarton.add(new Egg(8, new Yolk(BAD, ORANGE))); // Yolk is bad
        eggCarton.add(new Egg(25, new Yolk(GOOD, ORANGE))); // Might never hatch
        eggCarton.add(new Egg(5, new Yolk(GOOD, YELLOW))); // Valid ✅
        eggCarton.add(new Egg(-1, new Yolk(BAD, GOLD))); // Chicken might already be out
        eggCarton.add(new Egg(16, new Yolk(GOOD, GOLD))); // Too late to hatch 
        eggCarton.add(new Egg(14, new Yolk(GOOD, GOLD))); // Valid ✅
        eggCarton.add(new Egg(0, new Yolk(BAD, YELLOW))); // Chicken might already be out
        eggCarton.add(new Egg(6, new Yolk(BAD, ORANGE))); // Yolk is bad
        eggCarton.add(new Egg(12, new Yolk(GOOD, ORANGE))); // Yolk in wrong color
        eggCarton.add(new Egg(6, null)); // No Yolk to validate 
        return eggCarton;
    }

    public static List<ImmutableEgg> getImmutableEggCarton() {
        return List.of(
                null, // No egg to validate
                ImmutableEgg.of(1, new Yolk(GOOD, GOLD)), // About to hatch
                ImmutableEgg.of(8, new Yolk(BAD, ORANGE)), // Yolk is bad
                ImmutableEgg.of(25, new Yolk(GOOD, ORANGE)), // Might never hatch
                ImmutableEgg.of(5, new Yolk(GOOD, YELLOW)), // Valid ✅
                ImmutableEgg.of(-1, new Yolk(BAD, GOLD)), // Chicken might already be out
                ImmutableEgg.of(16, new Yolk(GOOD, GOLD)), // Too late to hatch 
                ImmutableEgg.of(14, new Yolk(GOOD, GOLD)), // Valid ✅
                ImmutableEgg.of(0, new Yolk(BAD, YELLOW)), // Chicken might already be out
                ImmutableEgg.of(6, new Yolk(BAD, ORANGE)), // Yolk is bad
                ImmutableEgg.of(12, new Yolk(GOOD, ORANGE)), // Yolk in wrong color
                ImmutableEgg.of(6, null) // No Yolk to validate 
        );
    }

    public static List<Validation<ValidationFailure, ImmutableEgg>> getExpectedImmutableEggValidationResults() {
        return List.of(
                Validation.invalid(VALIDATION_FAILURE_1),
                Validation.invalid(VALIDATION_FAILURE_PARENT_3),
                Validation.invalid(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
                Validation.invalid(ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2)),
                Validation.valid(ImmutableEgg.of(5, new Yolk(GOOD, YELLOW))),
                Validation.invalid(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
                Validation.invalid(VALIDATION_FAILURE_2),
                Validation.valid(ImmutableEgg.of(14, new Yolk(GOOD, GOLD))),
                Validation.invalid(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
                Validation.invalid(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
                Validation.invalid(VALIDATION_FAILURE_CHILD_3),
                Validation.invalid(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_31))
        );
    }

    public static java.util.Map<Integer, ValidationFailure> getExpectedEggValidationResults() {
        var expectedResults = new java.util.HashMap<Integer, ValidationFailure>();
        expectedResults.put(0, VALIDATION_FAILURE_1);
        expectedResults.put(1, VALIDATION_FAILURE_PARENT_3);
        expectedResults.put(2, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32));
        expectedResults.put(3, ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2));
        expectedResults.put(5, ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3));
        expectedResults.put(6, VALIDATION_FAILURE_2);
        expectedResults.put(8, ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3));
        expectedResults.put(9, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32));
        expectedResults.put(10, VALIDATION_FAILURE_CHILD_3);
        expectedResults.put(11, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_31));
        return expectedResults;
    }
    
    private static List<UnaryOperator<Validation<ValidationFailure, ImmutableEgg>>> PARENT_VALIDATIONS =
            List.of(validate1, validate2, validateParent3);
    
    private static List<UnaryOperator<Validation<ValidationFailure, Yolk>>> CHILD_VALIDATIONS
            = List.of(validateChild31, validateChild32);

    public static List<UnaryOperator<Validation<ValidationFailure, ImmutableEgg>>> EGG_VALIDATION_CHAIN =
                    PARENT_VALIDATIONS
                    .appendAll(liftAllToParentValidationType(CHILD_VALIDATIONS, ImmutableEgg::getYolk))
                    .appendAll(List.of(validateParent41, validateParent42, liftToParentValidationType(validateChild4, ImmutableEgg::getYolk)));

    // Generic lift functions and these can be extended to support different kinds of Eggs.
    private static <T, R> List<UnaryOperator<Validation<ValidationFailure, T>>> liftAllToParentValidationType(
            List<UnaryOperator<Validation<ValidationFailure, R>>> childValidations,
            Function<T, R> toChildMapper) {
        return childValidations.map(childValidation -> // Currying
                liftToParentValidationType(childValidation, toChildMapper));
    }

    private static <T, R> UnaryOperator<Validation<ValidationFailure, T>> liftToParentValidationType(
            UnaryOperator<Validation<ValidationFailure, R>> childValidation,
            Function<T, R> toChildMapper) {
        return eggToBeValidated -> childValidation
                .apply(eggToBeValidated.map(toChildMapper))
                .flatMap(ignore -> eggToBeValidated);
    }

}
