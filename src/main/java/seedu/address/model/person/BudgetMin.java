package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's minimum budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudgetMin(int)}.
 */
public class BudgetMin {

    public static final String MESSAGE_CONSTRAINTS = "BudgetMin should be a non-negative integer";

    public final int value;

    /**
     * Constructs a {@code BudgetMin}.
     *
     * @param budgetMin A valid minimum budget.
     */
    public BudgetMin(int budgetMin) {
        requireNonNull(budgetMin);
        checkArgument(isValidBudgetMin(budgetMin), MESSAGE_CONSTRAINTS);
        value = budgetMin;
    }

    /**
     * Returns true if a given integer is a valid minimum budget.
     */
    public static boolean isValidBudgetMin(int test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BudgetMin
                && value == ((BudgetMin) other).value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
