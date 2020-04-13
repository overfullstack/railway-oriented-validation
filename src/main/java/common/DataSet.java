package common;

import domain.Egg;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static domain.Color.GOLD;
import static domain.Color.ORANGE;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static domain.Condition.GOOD;
import static domain.validation.ThrowableMsgs.THROWABLE_NESTED_OPERATION_31;
import static domain.validation.ThrowableMsgs.THROWABLE_NESTED_OPERATION_32;
import static domain.validation.ThrowableMsgs.THROWABLE_OPERATION_2;
import static domain.validation.ThrowableMsgs.THROWABLE_VALIDATION_3;
import static domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3;
import static domain.validation.ValidationFailures.NOTHING_TO_VALIDATE;
import static domain.validation.ValidationFailures.NO_CHILD_TO_VALIDATE;
import static domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1;
import static domain.validation.ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD;
import static domain.validation.ValidationFailures.TOO_LATE_TO_HATCH_2;
import static domain.validation.ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3;

@UtilityClass
public class DataSet {
    public static final List<Egg> EGG_CARTON = new ArrayList<>(Arrays.asList(
            null, // No egg to validate
            new Egg(1, new Yolk(GOOD, GOLD)), // About to hatch
            new Egg(8, new Yolk(BAD, ORANGE)), // Yolk is bad
            new Egg(25, new Yolk(GOOD, ORANGE)), // Might never hatch
            new Egg(5, new Yolk(GOOD, YELLOW)), // Valid ✅
            new Egg(-1, new Yolk(BAD, GOLD)), // Chicken might already be out
            new Egg(16, new Yolk(GOOD, GOLD)), // Too late to hatch 
            new Egg(14, new Yolk(GOOD, GOLD)), // Valid ✅
            new Egg(0, new Yolk(BAD, YELLOW)), // Chicken might already be out
            new Egg(6, new Yolk(BAD, ORANGE)), // Yolk is bad
            new Egg(12, new Yolk(GOOD, ORANGE)), // Yolk in wrong color
            new Egg(6, null) // No Child to validate   
    ));

    public static final io.vavr.collection.List<ImmutableEgg> IMMUTABLE_EGG_CARTON =
            io.vavr.collection.List.of( // Using vavr list as `java.util.List` doesn't allow `null` in `List.of()`
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
                    ImmutableEgg.of(6, null) // No Child to validate 
            );

    public static final Map<Integer, ValidationFailure> EXPECTED_IMPERATIVE_VALIDATION_RESULTS = Map.of(
            0, NOTHING_TO_VALIDATE,
            1, ABOUT_TO_HATCH_P_3,
            2, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32),
            3, ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2),
            5, ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3),
            6, TOO_LATE_TO_HATCH_2,
            8, ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3),
            9, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32),
            10, YOLK_IS_IN_WRONG_COLOR_C_3,
            11, ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_31)
    );

    public static final List<Either<ValidationFailure, ImmutableEgg>> EXPECTED_DECLARATIVE_VALIDATION_RESULTS = List.of(
            Either.left(NOTHING_TO_VALIDATE),
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
            Either.left(NO_CHILD_TO_VALIDATE)
    );

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
