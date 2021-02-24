package app.common;

import app.domain.Egg;
import app.domain.ImmutableEgg;
import app.domain.Yolk;
import app.domain.validation.ValidationFailure;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.Map;

import static app.domain.Color.GOLD;
import static app.domain.Color.ORANGE;
import static app.domain.Color.YELLOW;
import static app.domain.Condition.BAD;
import static app.domain.Condition.GOOD;
import static app.domain.validation.ThrowableMsgs.THROWABLE_NESTED_OPERATION_31;
import static app.domain.validation.ThrowableMsgs.THROWABLE_NESTED_OPERATION_32;
import static app.domain.validation.ThrowableMsgs.THROWABLE_OPERATION_2;
import static app.domain.validation.ThrowableMsgs.THROWABLE_VALIDATION_3;
import static app.domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3;
import static app.domain.validation.ValidationFailures.NOTHING_TO_VALIDATE;
import static app.domain.validation.ValidationFailures.NO_CHILD_TO_VALIDATE;
import static app.domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1;
import static app.domain.validation.ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD;
import static app.domain.validation.ValidationFailures.TOO_LATE_TO_HATCH_2;
import static app.domain.validation.ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3;

@UtilityClass
public class DataSet {
    public static final List<Egg> EGG_CARTON = List.of(
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
    );

    public static final List<ImmutableEgg> IMMUTABLE_EGG_CARTON =
            io.vavr.collection.List.of( // Using vavr list as `java.util.List` doesn't allow `null` in `List.of()`
                    null, // No egg to validate
                    new ImmutableEgg(1, new Yolk(GOOD, GOLD)), // About to hatch
                    new ImmutableEgg(8, new Yolk(BAD, ORANGE)), // Yolk is bad
                    new ImmutableEgg(25, new Yolk(GOOD, ORANGE)), // Might never hatch
                    new ImmutableEgg(5, new Yolk(GOOD, YELLOW)), // Valid ✅
                    new ImmutableEgg(-1, new Yolk(BAD, GOLD)), // Chicken might already be out
                    new ImmutableEgg(16, new Yolk(GOOD, GOLD)), // Too late to hatch 
                    new ImmutableEgg(14, new Yolk(GOOD, GOLD)), // Valid ✅
                    new ImmutableEgg(0, new Yolk(BAD, YELLOW)), // Chicken might already be out
                    new ImmutableEgg(6, new Yolk(BAD, ORANGE)), // Yolk is bad
                    new ImmutableEgg(12, new Yolk(GOOD, ORANGE)), // Yolk in wrong color
                    new ImmutableEgg(6, null) // No Child to validate 
            );

    public static final Map<Integer, ValidationFailure> EXPECTED_IMPERATIVE_VALIDATION_RESULTS = Map.of(
            0, NO_EGG_TO_VALIDATE_1,
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
    
    public static final List<Either<ValidationFailure, ?>> EXPECTED_DECLARATIVE_VALIDATION_RESULTS = List.of(
            Either.left(NOTHING_TO_VALIDATE),
            Either.left(ABOUT_TO_HATCH_P_3),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2)),
            Either.right(new ImmutableEgg(5, new Yolk(GOOD, YELLOW))),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
            Either.left(TOO_LATE_TO_HATCH_2),
            Either.right(new ImmutableEgg(14, new Yolk(GOOD, GOLD))),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
            Either.left(YOLK_IS_IN_WRONG_COLOR_C_3),
            Either.left(NO_CHILD_TO_VALIDATE)
    );

    public static final List<Either<ValidationFailure, ?>> EXPECTED_DECLARATIVE_VALIDATION_RESULTS_2 = List.of(
            Either.left(NOTHING_TO_VALIDATE),
            Either.left(ABOUT_TO_HATCH_P_3),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_OPERATION_2)),
            Either.right(new ImmutableEgg(5, new Yolk(GOOD, YELLOW))),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
            Either.left(TOO_LATE_TO_HATCH_2),
            Either.right(new ImmutableEgg(14, new Yolk(GOOD, GOLD))),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_VALIDATION_3)),
            Either.left(ValidationFailure.withErrorMessage(THROWABLE_NESTED_OPERATION_32)),
            Either.left(YOLK_IS_IN_WRONG_COLOR_C_3),
            Either.left(NO_CHILD_TO_VALIDATE)
    );

    // TODO: 5/10/20 Incomplete, need to build it 
    public static List<List<Either<ValidationFailure, ?>>> getExpectedImmutableEggAccumulatedValidationResults() {
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

