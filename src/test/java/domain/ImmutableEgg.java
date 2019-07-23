/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package domain;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import static domain.Color.GOLD;
import static domain.Color.ORANGE;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static domain.Condition.GOOD;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImmutableEgg {
    public static final int MAX_DAYS_TO_HATCH = 21;
    int daysToHatch;
    Yolk yolk;

    public static List<ImmutableEgg> getEggCarton() {
        return List.of(
                new ImmutableEgg(1, new Yolk(GOOD, GOLD)),
                new ImmutableEgg(8, new Yolk(BAD, ORANGE)),
                new ImmutableEgg(7, null), // Null Yolk
                new ImmutableEgg(14, new Yolk(GOOD, GOLD)),
                new ImmutableEgg(25, new Yolk(GOOD, GOLD)),
                new ImmutableEgg(-1, new Yolk(GOOD, ORANGE)),
                new ImmutableEgg(0, new Yolk(BAD, YELLOW)),
                new ImmutableEgg(6, new Yolk(BAD, GOLD))
        );
    }

}
