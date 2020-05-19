package algebra;

import algebra.types.ThrowableValidator;
import algebra.types.Validator;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Objects;

import static io.vavr.CheckedFunction1.liftTry;
import static io.vavr.Function1.identity;

/**
 * Utility methods to be used by config.
 * gakshintala created on 3/23/20.
 */
@UtilityClass
public class ConfigDsl {
    /** ------------------------------------------- PARENT-CHILD ------------------------------------------- **/
    
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
    
    /** ------------------------------------------- THROWABLE ------------------------------------------- **/

    public static <FailureT, ValidatableT> Validator<ValidatableT, FailureT> liftThrowable(
            ThrowableValidator<ValidatableT, FailureT> toBeLifted, Function1<Throwable, FailureT> throwableMapper) {
        return validatable -> {
            val result = liftTry(toBeLifted).apply(validatable).toEither();
            return result.fold(throwable -> Either.left(throwableMapper.apply(throwable)), identity());
        };
    }

    public static <FailureT, ValidatableT> List<Validator<ValidatableT, FailureT>> liftAllThrowable(
            List<ThrowableValidator<ValidatableT, FailureT>> toBeLiftedFns, Function1<Throwable, FailureT> throwableMapper) {
        return toBeLiftedFns.map(toBeLifted -> liftThrowable(toBeLifted, throwableMapper));
    }
}
