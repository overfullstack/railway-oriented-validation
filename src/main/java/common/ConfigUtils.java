package common;

import declarative.Validator;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Function;

/**
 * Utility methods to be used by config.
 * gakshintala created on 3/23/20.
 */
@UtilityClass
public class ConfigUtils {
    static <ParentT, ChildT, FailureT> List<Validator<FailureT, ParentT>> liftAllToParentValidationType(
            List<Validator<FailureT, ChildT>> childValidations,
            Function<ParentT, ChildT> toChildMapper, FailureT invalidParent, FailureT invalidChild) {
        return childValidations.map(childValidation -> // Function taking a function and returning a function, Higher-order function
                liftToParentValidationType(childValidation, toChildMapper, invalidParent, invalidChild));
    }

    static <ParentT, ChildT, FailureT> Validator<FailureT, ParentT> liftToParentValidationType(
            Validator<FailureT, ChildT> childValidation,
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
