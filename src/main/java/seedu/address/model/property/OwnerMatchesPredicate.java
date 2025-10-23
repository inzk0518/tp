package seedu.address.model.property;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.uuid.Uuid;

/**
 * Test that a {@code Property}'s {@code Owner} matches the given contact UUID.
 */
public class OwnerMatchesPredicate implements Predicate<Property> {
    private final Uuid contactUuid;

    public OwnerMatchesPredicate(Uuid contactUuid) {
        this.contactUuid = contactUuid;
    }

    @Override
    public boolean test(Property property) {
        // Compare the property's owner UUID string with the target contact UUID
        // Owner.value is a String, Uuid is an int, so compare string representations
        return property.getOwner().value.equals(String.valueOf(contactUuid.getValue()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OwnerMatchesPredicate)) {
            return false;
        }

        OwnerMatchesPredicate otherPredicate = (OwnerMatchesPredicate) other;
        return contactUuid.equals(otherPredicate.contactUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("contactUuid", contactUuid)
                .toString();
    }
}
