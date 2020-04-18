package algebra;

import app.domain.Color;
import app.domain.Condition;
import app.domain.ImmutableEgg;
import app.domain.Yolk;
import app.domain.validation.ValidationFailure;
import io.vavr.collection.List;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.junit.jupiter.api.Test;

import static algebra.Strategies.failFastStrategy;
import static app.declarative.RailwayValidation2.validate;
import static app.declarative.RailwayValidation2.validateThrowable;
import static app.domain.validation.ValidationFailures.NONE;
import static app.domain.validation.ValidationFailures.NOTHING_TO_VALIDATE;

@Log4j2
class DslTest {
    @Test
    void lifting() {
        val simples = Dsl.liftAll(List.of(validate), NONE);
        val throwables = Dsl.liftAllThrowable(List.of(validateThrowable), NONE, ValidationFailure::withThrowable);
        final var simplesFirst = simples.appendAll(throwables);
        final var throwablesFirst = throwables.appendAll(simples);
        val validationResult1 = failFastStrategy(simplesFirst, NOTHING_TO_VALIDATE)
                .apply(new ImmutableEgg(20, new Yolk(Condition.BAD, Color.GOLD)));
        log.info(validationResult1);
        val validationResult2 = failFastStrategy(throwablesFirst, NOTHING_TO_VALIDATE)
                .apply(new ImmutableEgg(20, new Yolk(Condition.BAD, Color.GOLD)));
        log.info(validationResult2);
    }
}
