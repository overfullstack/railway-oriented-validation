package common;/* gakshintala created on 8/20/19 */

import domain.ImmutableEgg;
import domain.Yolk;
import domain.validation.ValidationFailure;
import io.vavr.collection.List;
import io.vavr.control.Either;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import static common.Utils.liftAllToParentValidationType;
import static common.Utils.liftToParentValidationType;
import static railwayoriented.RailwayEggValidation2.validate1;
import static railwayoriented.RailwayEggValidation2.validate2;
import static railwayoriented.RailwayEggValidation2.validateChild31;
import static railwayoriented.RailwayEggValidation2.validateChild32;
import static railwayoriented.RailwayEggValidation2.validateChild4;
import static railwayoriented.RailwayEggValidation2.validateParent3;
import static railwayoriented.RailwayEggValidation2.validateParent41;
import static railwayoriented.RailwayEggValidation2.validateParent42;

public class ValidationConfig {
    private static List<UnaryOperator<Either<ValidationFailure, ImmutableEgg>>> PARENT_VALIDATION_CHAIN =
            List.of(validate1, validate2, validateParent3);
    
    private static List<UnaryOperator<Either<ValidationFailure, Yolk>>> CHILD_VALIDATION_CHAIN
            = List.of(validateChild31, validateChild32);
    
    /* Final Validation Chain */
    public static List<UnaryOperator<Either<ValidationFailure, ImmutableEgg>>> EGG_VALIDATION_CHAIN =
                    PARENT_VALIDATION_CHAIN
                    .appendAll(liftAllToParentValidationType(CHILD_VALIDATION_CHAIN, ImmutableEgg::getYolk))
                    .appendAll(List.of(validateParent41, validateParent42, liftToParentValidationType(validateChild4, ImmutableEgg::getYolk)));
    
    /* If these parent-child dependencies are complex, we can make use of some graph algorithm to create a linear dependency graph of all validations */

    private static Function<Either<ValidationFailure, ImmutableEgg>, Either<ValidationFailure, ImmutableEgg>>
            PARENT_VALIDATION_COMPOSITION = validate1.andThen(validate2).andThen(validateParent3);

    private static Function<Either<ValidationFailure, Yolk>, Either<ValidationFailure, Yolk>>
            CHILD_VALIDATION_COMPOSITION = validateChild31.andThen(validateChild32);
}
