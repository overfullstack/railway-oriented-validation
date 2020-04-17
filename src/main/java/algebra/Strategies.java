package algebra;

import algebra.types.AccumulationStrategy;
import algebra.types.FailFastStrategy;
import algebra.types.Validator;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.ArrayList;

/**
 * gakshintala created on 3/21/20.
 */
@UtilityClass
public class Strategies {

    public <FailureT, ValidatableT> java.util.List<Either<FailureT, ValidatableT>> runAllValidationsFailFastImperative(
            List<ValidatableT> validatables, List<Validator<ValidatableT, FailureT>> validations, FailureT invalidValidatable) {
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

    public <FailureT, ValidatableT> FailFastStrategy<ValidatableT, FailureT> failFastStrategy(
            List<Validator<ValidatableT, FailureT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> {
            if (toBeValidated == null) {
                return Either.left(invalidValidatable);
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                return validations.foldLeft(toBeValidatedRight,
                        (validated, currentValidation) -> validated.isRight() ? currentValidation.apply(toBeValidatedRight) : validated);
            }
        };
    }

    private <FailureT, ValidatableT> FailFastStrategy<ValidatableT, FailureT> failFastStrategy2(
            List<Validator<ValidatableT, FailureT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> toBeValidated == null
                ? Either.left(invalidValidatable)
                : applyValidations(validations, toBeValidated).getOrElse(Either.right(toBeValidated));
    }

    private <FailureT, ValidatableT> Iterator<Either<FailureT, ValidatableT>> applyValidations(
            List<Validator<ValidatableT, FailureT>> validations, ValidatableT toBeValidated) {
        Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
        return validations.iterator()
                .map(validation -> validation.apply(toBeValidatedRight))
                .filter(Either::isLeft);
    }

    /* ---------------------------ACCUMULATION--------------------------- */

    public <FailureT, ValidatableT> AccumulationStrategy<ValidatableT, FailureT> accumulationStrategy(
            List<Validator<ValidatableT, FailureT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> {
            if (toBeValidated == null) {
                return List.of(Either.left(invalidValidatable));
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                return validations.foldLeft(List.<Either<FailureT, ValidatableT>>empty(),
                        (failureResults, currentValidation) -> {
                            val result = currentValidation.apply(toBeValidatedRight);
                            return result.isLeft() ? failureResults.append(result) : failureResults;
                        });
            }
        };
    }

    private <FailureT, ValidatableT> AccumulationStrategy<ValidatableT, FailureT> accumulationStrategy2(
            List<Validator<ValidatableT, FailureT>> validations, FailureT invalidValidatable) {
        return toBeValidated -> toBeValidated == null
                ? List.of(Either.left(invalidValidatable))
                : applyValidations(validations, toBeValidated).toList();
    }

}
