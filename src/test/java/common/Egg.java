/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package common;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static common.Egg.Condition.BAD;
import static common.Egg.Condition.GOOD;

@Value
public class Egg {
    int daysToHatch;

    Yellow yellow;

    public static List<Egg> getEggCarton() {
        List<Egg> eggList = new ArrayList<>();
        eggList.add(new Egg(1, new Yellow(GOOD)));
        eggList.add(new Egg(2, new Yellow(BAD)));
        eggList.add(new Egg(3, new Yellow(GOOD)));
        eggList.add(new Egg(4, new Yellow(GOOD)));
        eggList.add(new Egg(5, new Yellow(GOOD)));
        eggList.add(new Egg(-1, new Yellow(GOOD)));
        eggList.add(new Egg(0, new Yellow(BAD)));
        eggList.add(new Egg(1, new Yellow(BAD)));
        return eggList;
    }

    @Override
    public String toString() {
        return "Egg{" +
                "daysToHatch=" + daysToHatch +
                '}';
    }

    public enum Condition {
        GOOD, BAD
    }

    @Value
    public static class Yellow {
        private Condition condition;

    }
}
