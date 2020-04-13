package declarative;

import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * gakshintala created on 3/21/20.
 */
@UtilityClass
public class ValidationStrategies {

    public static <FailureT, ValidatableT> java.util.List<Either<FailureT, ValidatableT>> runAllValidationsFailFastImperative(
            List<ValidatableT> validatables, List<Validator<FailureT, ValidatableT>> validations, FailureT invalidValidatable) {
        var validationResults = new ArrayList<Either<FailureT, ValidatableT>>();
        for (var toBeValidated : validatables) {
            if (toBeValidated == null) {
                validationResults.add(Either.left(invalidValidatable)); // mutation
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                for (var validation : validations) {
                    toBeValidatedRight = validation.apply(toBeValidatedRight);
                    if (toBeValidatedRight.isLeft()) {
                        break;
                    }
                }
                validationResults.add(toBeValidatedRight); // mutation
            }
        }
        return validationResults;
    }

    /* ---------------------------FAIL FAST--------------------------- */

    public static <FailureT, ValidatableT> Function<ValidatableT, Either<FailureT, ValidatableT>> getFailFastStrategy(
            List<Validator<FailureT, ValidatableT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> toBeValidated == null ? Either.left(invalidValidatable)
                : validations.foldLeft(Either.right(toBeValidated),
                (validated, currentValidation) -> validated.isRight() ? currentValidation.apply(validated) : validated
        );
    }

    private static <FailureT, ValidatableT> Function<ValidatableT, Either<FailureT, ValidatableT>> getFailFastStrategy2(
            List<Validator<FailureT, ValidatableT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> {
            if (toBeValidated == null) {
                return Either.left(invalidValidatable);
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                return validations.iterator()
                        .map(validation -> validation.apply(toBeValidatedRight))
                        .filter(Either::isLeft)
                        .getOrElse(toBeValidatedRight);
            }
        };
    }

    /* ---------------------------ERROR ACCUMULATION--------------------------- */

    public static <FailureT, ValidatableT> Function<ValidatableT, List<Either<FailureT, ValidatableT>>> getErrorAccumulationStrategy(
            List<Validator<FailureT, ValidatableT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> toBeValidated == null ? List.of(Either.left(invalidValidatable))
                : validations.foldLeft(List.<Either<FailureT, ValidatableT>>empty(),
                (allValidationResults, currentValidation) -> allValidationResults.append(currentValidation.apply(Either.right(toBeValidated)))
        );
    }

    private static <FailureT, ValidatableT> Function<ValidatableT, List<Either<FailureT, ValidatableT>>> getErrorAccumulationStrategy2(
            List<Validator<FailureT, ValidatableT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> {
            if (toBeValidated == null) {
                return List.of(Either.left(invalidValidatable));
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                return validations.map(validation -> validation.apply(toBeValidatedRight));
            }
        };
    }

}
