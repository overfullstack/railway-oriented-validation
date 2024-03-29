package app.imperative;

import static app.domain.validation.ValidationFailures.ABOUT_TO_HATCH_P_3;
import static app.domain.validation.ValidationFailures.NO_EGG_TO_VALIDATE_1;
import static app.domain.validation.ValidationFailures.TOO_LATE_TO_HATCH_2;
import static app.domain.validation.ValidationFailures.YOLK_IS_IN_WRONG_COLOR_C_3;
import static app.imperative.Rules.simpleOperation1;
import static app.imperative.Rules.throwableNestedOperation3;
import static app.imperative.Rules.throwableOperation2;
import static app.imperative.Rules.throwableOperation3;

import app.domain.Egg;
import app.domain.Yolk;
import app.domain.validation.ValidationFailure;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * Validations are broken down to separate functions.
 *
 * Problems:
 * - Octopus Orchestration
 * - Mutation
 * - Unit-Testability
 * . Non-sharable
 * - Don't attempt to run in Parallel
 *
 * Major Problems
 * - Management of Validation Order - how-to-do
 * - Complexity
 * - Chaos
 * </pre>
 */
@Slf4j
@UtilityClass
public class ImperativeValidation2 {
  // Can't ensure the uniformity of signature among validations, which can increase the complexity.
  private static boolean validate1Simple(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Egg eggToBeValidated) {
    if (!simpleOperation1(eggToBeValidated)) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, NO_EGG_TO_VALIDATE_1);
      return false;
    }
    return true;
  }

  private static boolean validate2Throwable(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Egg eggToBeValidated) {
    try {
      if (!throwableOperation2(eggToBeValidated)) {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, TOO_LATE_TO_HATCH_2);
        return false;
      }
    } catch (Exception e) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
      return false;
    }
    return true;
  }

  // Parent with multiple Child Validations
  private static boolean validateParent3(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Egg eggToBeValidated) {
    try {
      if (throwableOperation3(eggToBeValidated)) {
        var yolkTobeValidated = eggToBeValidated.getYolk();
        if (!validateChild31(badEggFailureBucketMap, eggIndex, iterator, yolkTobeValidated)) {
          return false;
        }
        if (!validateChild32(badEggFailureBucketMap, eggIndex, iterator, yolkTobeValidated)) {
          return false;
        }
      } else {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, ABOUT_TO_HATCH_P_3);
        return false;
      }
    } catch (Exception e) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
      return false;
    }
    return true;
  }

  private static boolean validateChild31(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Yolk yolkTobeValidated) {
    try {
      if (!throwableNestedOperation3(yolkTobeValidated)) {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, YOLK_IS_IN_WRONG_COLOR_C_3);
        return false;
      }
    } catch (Exception e) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
      return false;
    }
    return true;
  }

  private static boolean validateChild32(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Yolk yolkTobeValidated) {
    try {
      if (!throwableNestedOperation3(yolkTobeValidated)) {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, YOLK_IS_IN_WRONG_COLOR_C_3);
        return false;
      }
    } catch (Exception e) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
      return false;
    }
    return true;
  }

  // Child with multiple Parent Validations
  private static boolean validateChild4(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Egg eggToBeValidated) {
    if (!validateParent41(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
      return false;
    }
    if (!validateParent42(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
      return false;
    }
    var yolkTobeValidated = eggToBeValidated.getYolk();
    try {
      if (!throwableNestedOperation3(yolkTobeValidated)) {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, YOLK_IS_IN_WRONG_COLOR_C_3);
        return false;
      }
    } catch (Exception e) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
      return false;
    }
    return true;
  }

  private static boolean validateParent41(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Egg eggToBeValidated) {
    try {
      if (!throwableOperation3(eggToBeValidated)) {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, ABOUT_TO_HATCH_P_3);
        return false;
      }
    } catch (Exception e) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
      return false;
    }
    return true;
  }

  private static boolean validateParent42(Map<Integer, ValidationFailure> badEggFailureBucketMap, int eggIndex,
      Iterator<Egg> iterator, Egg eggToBeValidated) {
    try {
      if (!throwableOperation3(eggToBeValidated)) {
        iterator.remove();
        badEggFailureBucketMap.put(eggIndex, ABOUT_TO_HATCH_P_3);
        return false;
      }
    } catch (Exception e) {
      iterator.remove();
      badEggFailureBucketMap.put(eggIndex, ValidationFailure.withErrorMessage(e.getMessage()));
      return false;
    }
    return true;
  }

  /**
   * This Octopus turns into a monster someday
   */
  static HashMap<Integer, ValidationFailure> validateEggCartonImperatively(List<Egg> eggList) {
    // R3 - Trying to be the owner of all state.
    var badEggFailureBucketMap = new HashMap<Integer, ValidationFailure>();
    int eggIndex = 0;
    for (Iterator<Egg> iterator = eggList.iterator(); iterator.hasNext(); eggIndex++) { // R-1: Iterate through eggs
      var eggToBeValidated = iterator.next();

      // Global state is dangerous. badEggFailureBucketMap and iterator being passed to each and every function, difficult to keep track of how they are being mutated during debugging.
      if (!validate1Simple(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
        continue; // R-2: Manage fail-fast
      }

      // Adding a new validation in-between requires you to understand all the validations above and below, which slows down development and makes it prone to bugs.
      if (!validate2Throwable(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
        continue;
      }

      // Parent with multiple Child Validations
      if (!validateParent3(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
        continue;
      }

      // Child with multiple Parent Validations
      if (!validateChild4(badEggFailureBucketMap, eggIndex, iterator, eggToBeValidated)) {
        continue;
      }
    }

    for (Map.Entry<Integer, ValidationFailure> entry : badEggFailureBucketMap.entrySet()) {
      log.info(entry.toString());
    }
    return badEggFailureBucketMap;
  }

}
