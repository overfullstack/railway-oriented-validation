package common;

import domain.ImmutableEgg;
import io.vavr.collection.List;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class Utils {

    public static final int MAX_DAYS_TO_HATCH = 21;
    public static final int MIN_DAYS_TO_HATCH = 15;
    private static final int MAX_SIZE_FOR_PARALLEL = 10000;

    public static Stream<ImmutableEgg> getImmutableEggStream(List<ImmutableEgg> immutableEggCarton) {
        return immutableEggCarton.size() >= MAX_SIZE_FOR_PARALLEL
                    ? immutableEggCarton.toJavaParallelStream()
                    : immutableEggCarton.toJavaStream();
    }
}
