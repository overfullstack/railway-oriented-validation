/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
        List<Egg> eggList = new ArrayList<>();
        // TODO 2019-06-30 gakshintala: Prepare appropriate test data set
        // TODO 2019-07-03 gakshintala: Convert to Vavr List.of
        eggList.add(new Egg(1, new Yolk(GOOD, GOLD)));
        eggList.add(new Egg(2, new Yolk(BAD, ORANGE)));
        eggList.add(new Egg(3, new Yolk(GOOD, YELLOW)));
        eggList.add(new Egg(14, new Yolk(GOOD, GOLD)));
        eggList.add(new Egg(25, new Yolk(GOOD, GOLD)));
        eggList.add(new Egg(-1, new Yolk(GOOD, ORANGE)));
        eggList.add(new Egg(0, new Yolk(BAD, YELLOW)));
        eggList.add(new Egg(1, new Yolk(BAD, GOLD)));
        return eggList;
    }
}
