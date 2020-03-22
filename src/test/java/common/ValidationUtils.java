package common;

import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * gakshintala created on 3/21/20.
 */
@UtilityClass
public class ValidationUtils {

    public static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, Either<FailureT, ToBeValidatedT>> runAllValidationsFailFast(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated -> validations.foldLeft(Either.<FailureT, ToBeValidatedT>right(toBeValidated),
                (validated, currentValidation) -> validated.isRight() ? currentValidation.apply(validated) : validated
        );
    }

    public static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, Either<FailureT, ToBeValidatedT>> runAllValidationsFailFast2(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated -> {
            Either<FailureT, ToBeValidatedT> toBeValidatedRight = Either.right(toBeValidated);
            return validations.iterator()
                    .map(validation -> validation.apply(toBeValidatedRight))
                    .filter(Either::isLeft)
                    .getOrElse(toBeValidatedRight);
        };
    }

    public static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, List<Either<FailureT, ToBeValidatedT>>> runAllValidationsErrorAccumulation(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated ->
                validations.foldLeft(List.<Either<FailureT, ToBeValidatedT>>empty(),
                        (allValidationResults, currentValidation) -> allValidationResults.append(currentValidation.apply(Either.right(toBeValidated)))
                );
    }

    public static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, List<Either<FailureT, ToBeValidatedT>>> runAllValidationsErrorAccumulation2(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated -> {
            Either<FailureT, ToBeValidatedT> toBeValidatedRight = Either.right(toBeValidated);
            return validations.map(validation -> validation.apply(toBeValidatedRight));
        };
    }

    static <ParentT, ChildT, FailureT> List<UnaryOperator<Either<FailureT, ParentT>>> liftAllToParentValidationType(
            List<UnaryOperator<Either<FailureT, ChildT>>> childValidations,
            Function<ParentT, ChildT> toChildMapper, FailureT invalidParent) {
        return childValidations.map(childValidation -> // Function taking a function and returning a function, Higher-order function
                liftToParentValidationType(childValidation, toChildMapper, invalidParent));
    }

    static <ParentT, ChildT, FailureT> UnaryOperator<Either<FailureT, ParentT>> liftToParentValidationType(
            UnaryOperator<Either<FailureT, ChildT>> childValidation,
            Function<ParentT, ChildT> toChildMapper, FailureT invalidParent) {
        return validatedParent -> {
            final var child = validatedParent
                    .flatMap(parent -> parent == null ? Either.left(invalidParent) : Either.right(parent))
                    .map(toChildMapper);
            return childValidation.apply(child)
                    .flatMap(ignore -> validatedParent);
        };
    }
}
