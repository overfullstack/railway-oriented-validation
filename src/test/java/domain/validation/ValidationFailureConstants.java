package domain.validation;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum ValidationFailureConstants implements ValidationFailure {
    NONE("NONE"),
    VALIDATION_FAILURE_1("VF1: No egg to validate 🧐"),
    VALIDATION_FAILURE_2("VF2: Too late to hatch ⏳"),
    VALIDATION_FAILURE_PARENT_3("VF-Parent-3: About to hatch 🥚"),
    VALIDATION_FAILURE_CHILD_3("VF-Child-3: Yolk is in wrong color ✴️"),
    ;
    private final String failureMessage;

}
