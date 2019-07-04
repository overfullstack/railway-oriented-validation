/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package domain;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static domain.Color.GOLD;
import static domain.Color.ORANGE;
import static domain.Color.YELLOW;
import static domain.Condition.BAD;
import static domain.Condition.GOOD;

@Value
public class ImmutableEgg {
    public static final int MAX_DAYS_TO_HATCH = 21;
    int daysToHatch;
    Yolk yolk;

    public static List<ImmutableEgg> getEggCarton() {
        List<ImmutableEgg> eggList = new ArrayList<>();
        // TODO 2019-06-30 gakshintala: Prepare appropriate test data set
        eggList.add(new ImmutableEgg(1, new Yolk(GOOD, GOLD)));
        eggList.add(new ImmutableEgg(2, new Yolk(BAD, ORANGE)));
        eggList.add(new ImmutableEgg(3, new Yolk(GOOD, YELLOW)));
        eggList.add(new ImmutableEgg(14, new Yolk(GOOD, GOLD)));
        eggList.add(new ImmutableEgg(25, new Yolk(GOOD, GOLD)));
        eggList.add(new ImmutableEgg(-1, new Yolk(GOOD, ORANGE)));
        eggList.add(new ImmutableEgg(0, new Yolk(BAD, YELLOW)));
        eggList.add(new ImmutableEgg(1, new Yolk(BAD, GOLD)));
        return eggList;
    }

    @Override
    public String toString() {
        return "Egg{" +
                "daysToHatch=" + daysToHatch +
                '}';
    }

}
