package common;/* gakshintala created on 8/20/19 */

import declarative.Validator;
import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.stream.Stream;

import static common.ConfigUtils.liftAllToParentValidationType;
import static common.ConfigUtils.liftToParentValidationType;
import static declarative.RailwayValidation2.validate1Simple;
import static declarative.RailwayValidation2.validate2Throwable;
import static declarative.RailwayValidation2.validateChild31;
import static declarative.RailwayValidation2.validateChild32;
import static declarative.RailwayValidation2.validateChild4;
import static declarative.RailwayValidation2.validateParent3;
import static declarative.RailwayValidation2.validateParent41;
import static declarative.RailwayValidation2.validateParent42;
import static domain.validation.ValidationFailures.NO_CHILD_TO_VALIDATE;
import static domain.validation.ValidationFailures.NO_PARENT_TO_VALIDATE_CHILD;

/**
 * Config to prepare various instances and constants.
 * gakshintala created on 3/23/20.
 */
@UtilityClass
public class Config {
    public static final int MIN_DAYS_TO_HATCH = 15;
    public static final int MAX_DAYS_TO_HATCH = 21;
    public static final int MAX_DAYS_TO_SHIP = 5;
    static final int MAX_SIZE_FOR_PARALLEL = 10000;

    /**
     * The Validation Chains.<br>
     * If these parent-child dependencies are complex, we can make use of some graph algorithm to create a linear dependency graph of all validations.
     */
    private static final List<Validator<ValidationFailure,ImmutableEgg>> PARENT_VALIDATION_CHAIN =
            List.of(validate1Simple, validate2Throwable, validateParent3);
    private static final List<Validator<ValidationFailure,Yolk>> CHILD_VALIDATION_CHAIN
            = List.of(validateChild31, validateChild32);
    public static final List<Validator<ValidationFailure, ImmutableEgg>> EGG_VALIDATION_CHAIN =
            PARENT_VALIDATION_CHAIN
                    .appendAll(liftAllToParentValidationType(CHILD_VALIDATION_CHAIN, ImmutableEgg::getYolk, NO_PARENT_TO_VALIDATE_CHILD, NO_CHILD_TO_VALIDATE))
                    .appendAll(List.of(
                            validateParent41,
                            validateParent42,
                            liftToParentValidationType(validateChild4, ImmutableEgg::getYolk, NO_PARENT_TO_VALIDATE_CHILD, NO_CHILD_TO_VALIDATE))
                    );

    /**
     * The above chain can also be achieved this way using `andThen.
     */
    private static final Function<Either<ValidationFailure, ImmutableEgg>, Either<ValidationFailure, ImmutableEgg>>
            PARENT_VALIDATION_COMPOSITION = validate1Simple.andThen(validate2Throwable).andThen(validateParent3);
    private static final Function<Either<ValidationFailure, Yolk>, Either<ValidationFailure, Yolk>>
            CHILD_VALIDATION_COMPOSITION = validateChild31.andThen(validateChild32);

    public static <E> Stream<E> getStreamBySize(List<E> list) {
        return list.size() >= MAX_SIZE_FOR_PARALLEL
                ? list.toJavaParallelStream()
                : list.toJavaStream();
    }

}

