package common;

import domain.ImmutableEgg;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Stream;

import static common.ValidationConfig.MAX_SIZE_FOR_PARALLEL;

/**
 * gakshintala created on 3/21/20.
 */
@UtilityClass
public class ValidationUtils {
    public static Stream<ImmutableEgg> getImmutableEggStream(List<ImmutableEgg> immutableEggCarton) {
        return immutableEggCarton.size() >= MAX_SIZE_FOR_PARALLEL
                ? immutableEggCarton.parallelStream()
                : immutableEggCarton.stream();
    }
}
