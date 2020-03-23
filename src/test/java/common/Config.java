package common;/* gakshintala created on 8/20/19 */

import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.collection.List;
import io.vavr.control.Either;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static domain.validation.ValidationFailureConstants.NO_PARENT_TO_VALIDATE_CHILD;
import static railwayoriented.RailwayEggValidation2.validate1Simple;
import static railwayoriented.RailwayEggValidation2.validate2Throwable;
import static railwayoriented.RailwayEggValidation2.validateChild31;
import static railwayoriented.RailwayEggValidation2.validateChild32;
import static railwayoriented.RailwayEggValidation2.validateChild4;
import static railwayoriented.RailwayEggValidation2.validateParent3;
import static railwayoriented.RailwayEggValidation2.validateParent41;
import static railwayoriented.RailwayEggValidation2.validateParent42;

public class Config {
    public static final int MAX_DAYS_TO_HATCH = 21;
    public static final int MIN_DAYS_TO_HATCH = 15;
    static final int MAX_SIZE_FOR_PARALLEL = 10000;

    /**
     * The Validation Chains.<br>
     * If these parent-child dependencies are complex, we can make use of some graph algorithm to create a linear dependency graph of all validations.
     */
    private static final List<UnaryOperator<Either<ValidationFailure, ImmutableEgg>>> PARENT_VALIDATION_CHAIN =
            List.of(validate1Simple, validate2Throwable, validateParent3);
    private static final List<UnaryOperator<Either<ValidationFailure, Yolk>>> CHILD_VALIDATION_CHAIN
            = List.of(validateChild31, validateChild32);
    public static final List<UnaryOperator<Either<ValidationFailure, ImmutableEgg>>> EGG_VALIDATION_CHAIN =
            PARENT_VALIDATION_CHAIN
                    .appendAll(ConfigUtils.liftAllToParentValidationType(CHILD_VALIDATION_CHAIN, ImmutableEgg::getYolk, NO_PARENT_TO_VALIDATE_CHILD))
                    .appendAll(List.of(
                            validateParent41,
                            validateParent42,
                            ConfigUtils.liftToParentValidationType(validateChild4, ImmutableEgg::getYolk, NO_PARENT_TO_VALIDATE_CHILD))
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

