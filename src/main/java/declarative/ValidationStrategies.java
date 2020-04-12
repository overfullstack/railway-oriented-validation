package declarative;

import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * gakshintala created on 3/21/20.
 */
@UtilityClass
public class ValidationStrategies {

    public static <FailureT, ValidatableT> java.util.List<Either<FailureT, ValidatableT>> runAllValidationsFailFastImperative(
            List<ValidatableT> toBeValidatedList, List<UnaryOperator<Either<FailureT, ValidatableT>>> validations) {
        var validationResults = new ArrayList<Either<FailureT, ValidatableT>>();
        for (var toBeValidated : toBeValidatedList) {
            Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
            for (var validation : validations) {
                toBeValidatedRight = validation.apply(toBeValidatedRight);
                if (toBeValidatedRight.isLeft()) {
                    break;
                }
            }
            validationResults.add(toBeValidatedRight); // mutation
        }
        return validationResults;
    }

    /* ---------------------------FAIL FAST--------------------------- */

    public static <FailureT, ValidatableT> Function<ValidatableT, Either<FailureT, ValidatableT>> getFailFastStrategy(
            List<UnaryOperator<Either<FailureT, ValidatableT>>> validations) {
        return toBeValidated -> validations.foldLeft(Either.right(toBeValidated),
                (validated, currentValidation) -> validated.isRight() ? currentValidation.apply(validated) : validated
        );
    }

    private static <FailureT, ValidatableT> Function<ValidatableT, Either<FailureT, ValidatableT>> getFailFastStrategy2(
            List<UnaryOperator<Either<FailureT, ValidatableT>>> validations) {
        return toBeValidated -> {
            Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
            return validations.iterator()
                    .map(validation -> validation.apply(toBeValidatedRight))
                    .filter(Either::isLeft)
                    .getOrElse(toBeValidatedRight);
        };
    }

    /* ---------------------------ERROR ACCUMULATION--------------------------- */

    public static <FailureT, ValidatableT> Function<ValidatableT, List<Either<FailureT, ValidatableT>>> getErrorAccumulationStrategy(
            List<UnaryOperator<Either<FailureT, ValidatableT>>> validations) {
        return toBeValidated ->
                validations.foldLeft(List.<Either<FailureT, ValidatableT>>empty(),
                        (allValidationResults, currentValidation) -> allValidationResults.append(currentValidation.apply(Either.right(toBeValidated)))
                );
    }

    private static <FailureT, ValidatableT> Function<ValidatableT, List<Either<FailureT, ValidatableT>>> getErrorAccumulationStrategy2(
            List<UnaryOperator<Either<FailureT, ValidatableT>>> validations) {
        return toBeValidated -> {
            Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
            return validations.map(validation -> validation.apply(toBeValidatedRight));
        };
    }

}
