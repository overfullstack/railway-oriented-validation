package app.declarative;/* gakshintala created on 8/20/19 */

import algebra.types.Validator;
import app.common.Constants;
import app.domain.ImmutableEgg;
import app.domain.Yolk;
import app.domain.validation.ValidationFailure;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

import static algebra.ConfigDsl.liftAllToParentValidationType;
import static algebra.ConfigDsl.liftToParentValidationType;
import static algebra.Dsl.liftThrowable;
import static app.declarative.RailwayValidation2.validate1Simple;
import static app.declarative.RailwayValidation2.validate2Throwable;
import static app.declarative.RailwayValidation2.validateChild31;
import static app.declarative.RailwayValidation2.validateChild32;
import static app.declarative.RailwayValidation2.validateChild4;
import static app.declarative.RailwayValidation2.validateParent3;
import static app.declarative.RailwayValidation2.validateParent41;
import static app.declarative.RailwayValidation2.validateParent42;
import static app.domain.validation.ValidationFailures.NO_CHILD_TO_VALIDATE;
import static app.domain.validation.ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD;

/**
 * Config to prepare various instances and constants.
 * gakshintala created on 3/23/20.
 */
@UtilityClass
public class Config {
    /**
     * The Validation Chains.<br>
     * If these parent-child dependencies are complex, we can make use of some graph algorithm to create a linear dependency graph of all validations.
     */
    private static final List<Validator<ImmutableEgg, ValidationFailure>> PARENT_VALIDATION_CHAIN =
            List.of(validate1Simple, liftThrowable(validate2Throwable, ValidationFailure::withThrowable), validateParent3);

    private static final List<Validator<Yolk, ValidationFailure>> CHILD_VALIDATION_CHAIN
            = List.of(validateChild31, validateChild32);

    public static final List<Validator<ImmutableEgg, ValidationFailure>> EGG_VALIDATION_CHAIN =
            PARENT_VALIDATION_CHAIN
                    .appendAll(liftAllToParentValidationType(CHILD_VALIDATION_CHAIN, ImmutableEgg::yolk, NO_PARENT_TO_VALIDATE_CHILD, NO_CHILD_TO_VALIDATE))
                    .appendAll(List.of(
                            validateParent41,
                            validateParent42,
                            liftToParentValidationType(validateChild4, ImmutableEgg::yolk, NO_PARENT_TO_VALIDATE_CHILD, NO_CHILD_TO_VALIDATE))
                    );

    /**
     * The above chain can also be achieved this way using `andThen.
     */
    private static final Function1<Either<ValidationFailure, ImmutableEgg>, Either<ValidationFailure, ImmutableEgg>>
            PARENT_VALIDATION_COMPOSITION = validate1Simple
            .andThen(liftThrowable(validate2Throwable, ValidationFailure::withThrowable))
            .andThen(validateParent3);
    private static final Function1<Either<ValidationFailure, Yolk>, Either<ValidationFailure, Yolk>>
            CHILD_VALIDATION_COMPOSITION = validateChild31.andThen(validateChild32);

    public <E> Stream<E> getStreamBySize(List<E> list) {
        return list.size() >= Constants.MAX_SIZE_FOR_PARALLEL
                ? list.toJavaParallelStream()
                : list.toJavaStream();
    }

}

