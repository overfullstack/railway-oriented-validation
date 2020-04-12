package common;

import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Utility methods to be used by config.
 * gakshintala created on 3/23/20.
 */
@UtilityClass
public class ConfigUtils {
    static <ParentT, ChildT, FailureT> List<UnaryOperator<Either<FailureT, ParentT>>> liftAllToParentValidationType(
            List<UnaryOperator<Either<FailureT, ChildT>>> childValidations,
            Function<ParentT, ChildT> toChildMapper, FailureT invalidParent, FailureT invalidChild) {
        return childValidations.map(childValidation -> // Function taking a function and returning a function, Higher-order function
                liftToParentValidationType(childValidation, toChildMapper, invalidParent, invalidChild));
    }

    static <ParentT, ChildT, FailureT> UnaryOperator<Either<FailureT, ParentT>> liftToParentValidationType(
            UnaryOperator<Either<FailureT, ChildT>> childValidation,
            Function<ParentT, ChildT> toChildMapper, FailureT invalidParent, FailureT invalidChild) {
        return validatedParent -> {
            final var child = validatedParent
                    .flatMap(parent -> parent == null ? Either.left(invalidParent) : Either.right(parent))
                    .map(toChildMapper);
            return child
                    .filter(Objects::nonNull)
                    .map(childValidation)
                    .getOrElse(Either.left(invalidChild))
                    .flatMap(ignore -> validatedParent);
        };
    }
}
