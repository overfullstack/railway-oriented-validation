package common;

import domain.ImmutableEgg;
import domain.validation.ValidationFailure;
import io.vavr.control.Validation;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@UtilityClass
public class Utils {

    public static final int MAX_DAYS_TO_HATCH = 21;
    public static final int MIN_DAYS_TO_HATCH = 15;
    private static final int MAX_SIZE_FOR_PARALLEL = 10000;

    public static Stream<ImmutableEgg> getImmutableEggStream(List<ImmutableEgg> immutableEggCarton) {
        return immutableEggCarton.size() >= MAX_SIZE_FOR_PARALLEL
                ? immutableEggCarton.parallelStream()
                : immutableEggCarton.stream();
    }

    /*These lift functions are generic and these can be extended to support different kinds of Eggs.*/
    static <T, R> io.vavr.collection.List<UnaryOperator<Validation<ValidationFailure, T>>> liftAllToParentValidationType(
            io.vavr.collection.List<UnaryOperator<Validation<ValidationFailure, R>>> childValidations,
            Function<T, R> toChildMapper) {
        return childValidations.map(childValidation -> // Function taking a function and returning a function, Higher-order function
                liftToParentValidationType(childValidation, toChildMapper));
    }

    static <T, R> UnaryOperator<Validation<ValidationFailure, T>> liftToParentValidationType(
            UnaryOperator<Validation<ValidationFailure, R>> childValidation,
            Function<T, R> toChildMapper) {
        return eggToBeValidated -> childValidation
                .apply(eggToBeValidated.map(toChildMapper))
                .flatMap(ignore -> eggToBeValidated);
    }
}
