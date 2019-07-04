/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package domain;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.Data;

import static domain.Color.GOLD;
import static domain.Color.ORANGE;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static domain.Condition.GOOD;

@Data
@AllArgsConstructor
public class Egg {
    public static final int MAX_DAYS_TO_HATCH = 21;
    int daysToHatch;
    Yolk yolk;

    public static List<Egg> getEggCarton() {
        // TODO 2019-06-30 gakshintala: Prepare appropriate test data set
        return List.of(
                new Egg(1, new Yolk(GOOD, GOLD)),
                new Egg(8, new Yolk(BAD, ORANGE)),
                new Egg(7, null), // Null Yolk
                new Egg(14, new Yolk(GOOD, GOLD)),
                new Egg(25, new Yolk(GOOD, GOLD)),
                new Egg(-1, new Yolk(GOOD, ORANGE)),
                new Egg(0, new Yolk(BAD, YELLOW)),
                new Egg(6, new Yolk(BAD, GOLD))
        );

    }
}
