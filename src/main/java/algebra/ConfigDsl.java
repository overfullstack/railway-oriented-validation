package algebra;

import algebra.types.Validator;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Objects;

/**
 * Utility methods to be used by config.
 * gakshintala created on 3/23/20.
 */
@UtilityClass
public class ConfigDsl {
    public static <ParentT, ChildT, FailureT> List<Validator<ParentT, FailureT>> liftAllToParentValidationType(
            List<Validator<ChildT, FailureT>> childValidations,
            Function1<ParentT, ChildT> toChildMapper, FailureT invalidParent, FailureT invalidChild) {
        return childValidations.map(childValidation -> // Function taking a function and returning a function, Higher-order function
                liftToParentValidationType(childValidation, toChildMapper, invalidParent, invalidChild));
    }

    public static <ParentT, ChildT, FailureT> Validator<ParentT, FailureT> liftToParentValidationType(
            Validator<ChildT, FailureT> childValidation,
            Function1<ParentT, ChildT> toChildMapper, FailureT invalidParent, FailureT invalidChild) {
        return validatedParent -> {
            val child = validatedParent
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
