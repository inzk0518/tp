package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's maximum budget in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBudgetMax(int)}.
 */
public class BudgetMax {

    public static final String MESSAGE_CONSTRAINTS = "BudgetMax should be a non-negative integer";

    public final int value;

    /**
     * Constructs a {@code BudgetMax}.
     *
     * @param budgetMax A valid maximum budget.
     */
    public BudgetMax(int budgetMax) {
        requireNonNull(budgetMax);
        checkArgument(isValidBudgetMax(budgetMax), MESSAGE_CONSTRAINTS);
        value = budgetMax;
    }

    /**
     * Returns true if a given integer is a valid maximum budget.
     */
    public static boolean isValidBudgetMax(int test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BudgetMax
                && value == ((BudgetMax) other).value);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
