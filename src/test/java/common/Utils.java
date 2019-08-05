package common;

import domain.ImmutableEgg;
import io.vavr.collection.List;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class Utils {

    public static final int MAX_DAYS_TO_HATCH = 21;
    public static final int MIN_DAYS_TO_HATCH = 15;

    public static Stream<ImmutableEgg> getImmutableEggStream(List<ImmutableEgg> immutableEggCarton) {
        return immutableEggCarton.size() >= 10000
                    ? immutableEggCarton.toJavaStream()
                    : immutableEggCarton.toJavaParallelStream();
    }
}
