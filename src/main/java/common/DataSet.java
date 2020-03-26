package common;

import domain.Egg;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static domain.Color.GOLD;
import static domain.Color.ORANGE;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static domain.Condition.GOOD;
import static domain.validation.ThrowableMessages.THROWABLE_NESTED_OPERATION_31;
import static domain.validation.ThrowableMessages.THROWABLE_NESTED_OPERATION_32;
import static domain.validation.ThrowableMessages.THROWABLE_OPERATION_2;
import static domain.validation.ThrowableMessages.THROWABLE_VALIDATION_3;
import static domain.validation.ValidationFailureConstants.ABOUT_TO_HATCH_P_3;
import static domain.validation.ValidationFailureConstants.NO_EGG_TO_VALIDATE_1;
import static domain.validation.ValidationFailureConstants.NO_PARENT_TO_VALIDATE_CHILD;
import static domain.validation.ValidationFailureConstants.TOO_LATE_TO_HATCH_2;
import static domain.validation.ValidationFailureConstants.YOLK_IS_IN_WRONG_COLOR_C_3;

@UtilityClass
public class DataSet {
    public static List<Egg> getEggCarton() {
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

    public static io.vavr.collection.List<ImmutableEgg> getImmutableEggCarton() {
        return io.vavr.collection.List.of( // Using vavr list as `java.util.List` doesn't allow `null` in `List.of()`
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

    public static List<Either<ValidationFailure, ImmutableEgg>> getExpectedImmutableEggValidationResults() {
        return List.of(
                Either.left(NO_EGG_TO_VALIDATE_1),
                Either.left(ABOUT_TO_HATCH_P_3),
                Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
                Either.left(ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2)),
                Either.right(ImmutableEgg.of(5, new Yolk(GOOD, YELLOW))),
                Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
                Either.left(TOO_LATE_TO_HATCH_2),
                Either.right(ImmutableEgg.of(14, new Yolk(GOOD, GOLD))),
                Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
                Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
                Either.left(YOLK_IS_IN_WRONG_COLOR_C_3),
                Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_31))
        );
    }

    public static Map<Integer, ValidationFailure> getExpectedEggValidationResults() {
        var expectedResults = new java.util.HashMap<Integer, ValidationFailure>();
        expectedResults.put(0, NO_EGG_TO_VALIDATE_1);
        expectedResults.put(1, ABOUT_TO_HATCH_P_3);
        expectedResults.put(2, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32));
        expectedResults.put(3, ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2));
        expectedResults.put(5, ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3));
        expectedResults.put(6, TOO_LATE_TO_HATCH_2);
        expectedResults.put(8, ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3));
        expectedResults.put(9, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32));
        expectedResults.put(10, YOLK_IS_IN_WRONG_COLOR_C_3);
        expectedResults.put(11, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_31));
        return expectedResults;
    }

    public static List<List<Either<ValidationFailure, ImmutableEgg>>> getExpectedImmutableEggAccumulatedValidationResults() {
        return List.of(
                List.of(
                        Either.left(NO_EGG_TO_VALIDATE_1),
                        Either.left(ValidationFailure.withErrorMessage("null")),
                        Either.left(ValidationFailure.withErrorMessage("null")),
                        Either.left(NO_PARENT_TO_VALIDATE_CHILD),
                        Either.left(NO_PARENT_TO_VALIDATE_CHILD),
                        Either.left(ValidationFailure.withErrorMessage("null")),
                        Either.left(ValidationFailure.withErrorMessage("null")),
                        Either.left(NO_PARENT_TO_VALIDATE_CHILD)
                )
        );
    }

}
