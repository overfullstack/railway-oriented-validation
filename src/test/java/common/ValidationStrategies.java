package common;

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

    public static <FailureT, ToBeValidatedT> java.util.List<Either<FailureT, ToBeValidatedT>> runAllValidationsFailFastImperative(
            List<ToBeValidatedT> toBeValidatedList, List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        var validationResults = new ArrayList<Either<FailureT, ToBeValidatedT>>();
        for (var toBeValidated : toBeValidatedList) {
            Either<FailureT, ToBeValidatedT> toBeValidatedRight = Either.right(toBeValidated);
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

    public static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, Either<FailureT, ToBeValidatedT>> getFailFastStrategy(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return runAllValidationsFailFast(validations);
    }

    private static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, Either<FailureT, ToBeValidatedT>> runAllValidationsFailFast(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated -> validations.foldLeft(Either.<FailureT, ToBeValidatedT>right(toBeValidated),
                (validated, currentValidation) -> validated.isRight() ? currentValidation.apply(validated) : validated
        );
    }

    private static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, Either<FailureT, ToBeValidatedT>> runAllValidationsFailFast2(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated -> {
            Either<FailureT, ToBeValidatedT> toBeValidatedRight = Either.right(toBeValidated);
            return validations.iterator()
                    .map(validation -> validation.apply(toBeValidatedRight))
                    .filter(Either::isLeft)
                    .getOrElse(toBeValidatedRight);
        };
    }

    /* ---------------------------ERROR ACCUMULATION--------------------------- */

    public static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, List<Either<FailureT, ToBeValidatedT>>> getErrorAccumulationStrategy(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return runAllValidationsErrorAccumulation(validations);
    }

    private static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, List<Either<FailureT, ToBeValidatedT>>> runAllValidationsErrorAccumulation(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated ->
                validations.foldLeft(List.<Either<FailureT, ToBeValidatedT>>empty(),
                        (allValidationResults, currentValidation) -> allValidationResults.append(currentValidation.apply(Either.right(toBeValidated)))
                );
    }

    private static <FailureT, ToBeValidatedT> Function<ToBeValidatedT, List<Either<FailureT, ToBeValidatedT>>> runAllValidationsErrorAccumulation2(
            List<UnaryOperator<Either<FailureT, ToBeValidatedT>>> validations) {
        return toBeValidated -> {
            Either<FailureT, ToBeValidatedT> toBeValidatedRight = Either.right(toBeValidated);
            return validations.map(validation -> validation.apply(toBeValidatedRight));
        };
    }
    
}
