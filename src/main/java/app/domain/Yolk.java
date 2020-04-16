package app.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Yolk {
    Condition condition;
    Color color;
}
