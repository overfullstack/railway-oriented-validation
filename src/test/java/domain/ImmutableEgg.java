/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package domain;

import lombok.Value;

@Value(staticConstructor="of")
public class ImmutableEgg {
    int daysToHatch;
    Yolk yolk;
}
