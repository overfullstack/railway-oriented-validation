package algebra;

import algebra.types.Validator;
import io.vavr.Function1;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.ArrayList;
import java.util.function.BiFunction;

import static io.vavr.Function1.identity;

/**
 * These Strategies compose multiple validations and return a single function which can be applied on the Validatable.
 * 
 * gakshintala created on 3/21/20.
 */
@UtilityClass
public class Strategies {

    public static <FailureT, ValidatableT> java.util.List<Either<FailureT, ValidatableT>> runAllValidationsFailFastImperative(
            List<ValidatableT> validatables, List<Validator<ValidatableT, FailureT>> validations, 
            FailureT invalidValidatable,
            Function1<Throwable, FailureT> throwableMapper) {
        var validationResults = new ArrayList<Either<FailureT, ValidatableT>>();
        for (var toBeValidated : validatables) {
            if (toBeValidated == null) {
                validationResults.add(Either.left(invalidValidatable)); // mutation
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                Either<FailureT, ValidatableT> validationResult = null;
                for (var currentValidation : validations) {
                    validationResult = fireValidation(currentValidation, toBeValidatedRight, throwableMapper).map(ignore -> toBeValidated);
                    if (validationResult.isLeft()) {
                        break;
                    }
                }
                validationResults.add(validationResult); // mutation
            }
        }
        return validationResults;
    }

    /* ---------------------------FAIL FAST--------------------------- */

    /**
     * - No need to comprehend every-time, like the nested for-each loop
     * - No need to Unit-test
     * - Shared Vocab within the team
     * - Universal Vocab across languages
     */
    public static <FailureT, ValidatableT> FailFastStrategy<ValidatableT, FailureT> failFastStrategy(
            List<Validator<ValidatableT, FailureT>> validations, 
            FailureT invalidValidatable, 
            Function1<Throwable, FailureT> throwableMapper) {
        return toBeValidated -> {
            if (toBeValidated == null) {
                return Either.left(invalidValidatable);
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                final BiFunction<Either<FailureT, ?>, Validator<ValidatableT, FailureT>, Either<FailureT, ?>> applyValidation
                        = (validated, currentValidation) -> validated.isRight() ? fireValidation(currentValidation, toBeValidatedRight, throwableMapper) : validated;
                return validations.foldLeft(toBeValidatedRight, applyValidation).map(ignore -> toBeValidated);
            }
        };
    }

    public static <FailureT, ValidatableT> AccumulationStrategy<ValidatableT, FailureT> accumulationStrategy(
            List<Validator<ValidatableT, FailureT>> validations,
            FailureT invalidValidatable,
            Function1<Throwable, FailureT> throwableMapper) {
        return toBeValidated -> {
            if (toBeValidated == null) {
                return List.of(Either.left(invalidValidatable));
            } else {
                Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
                return validations.foldLeft(List.<Either<FailureT, ?>>empty(),
                        (failureResults, currentValidation) -> {
                            val result = fireValidation(currentValidation, toBeValidatedRight, throwableMapper);
                            return result.isLeft() ? failureResults.append(result) : failureResults;
                        }).map(result -> result.map(ignore -> toBeValidated));
            }
        };
    }

    private static <FailureT, ValidatableT> Either<FailureT, ?> fireValidation(
            Validator<ValidatableT, FailureT> validation,
            Either<FailureT, ValidatableT> validatable,
            Function1<Throwable, FailureT> throwableMapper) {
        return Try.of(() -> validation.apply(validatable))
                .fold(throwable -> Either.left(throwableMapper.apply(throwable)), identity());
    }

    public static <FailureT, ValidatableT> FailFastStrategy<ValidatableT, FailureT> failFastStrategy2(
            List<Validator<ValidatableT, FailureT>> validations, 
            FailureT invalidValidatable, 
            Function1<Throwable, FailureT> throwableMapper) {
        return toBeValidated -> {
            if (toBeValidated == null) return Either.left(invalidValidatable);
            return applyValidations(toBeValidated, validations, throwableMapper)
                    .filter(Either::isLeft)
                    .getOrElse(Either.right(toBeValidated)).map(ignore -> toBeValidated);
        };
    }

    private <FailureT, ValidatableT> AccumulationStrategy<ValidatableT, FailureT> accumulationStrategy2(
            List<Validator<ValidatableT, FailureT>> validations,
            FailureT invalidValidatable,
            Function1<Throwable, FailureT> throwableMapper) {
        return toBeValidated -> toBeValidated == null
                ? List.of(Either.left(invalidValidatable))
                : applyValidations(toBeValidated, validations, throwableMapper)
                .map(result -> result.map(ignore -> toBeValidated)) // replacing wildcard on right state with toBeValidated.
                .toList();
    }

    private static <FailureT, ValidatableT> Iterator<Either<FailureT, ?>> applyValidations(
            ValidatableT toBeValidated, 
            List<Validator<ValidatableT, FailureT>> validations, 
            Function1<Throwable, FailureT> throwableMapper) {
        Either<FailureT, ValidatableT> toBeValidatedRight = Either.right(toBeValidated);
        return validations.iterator() // This is vavr iterator, which is lazy by default.
                        .map(currentValidation -> fireValidation(currentValidation, toBeValidatedRight, throwableMapper));
    }

    /* ---------------------------ACCUMULATION--------------------------- */

}

