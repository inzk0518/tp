package seedu.address.model.property;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.uuid.Uuid;

/**
 * Test that a {@code Property}'s {@code Owner} matches the given client UUID.
 */
public class OwnerMatchesPredicate implements Predicate<Property> {
    private final Uuid clientUuid;

    public OwnerMatchesPredicate(Uuid clientUuid) {
        this.clientUuid = clientUuid;
    }

    @Override
    public boolean test(Property property) {
        // Compare the property's owner UUID string with the target client UUID
        // Owner.value is a String, Uuid is an int, so compare string representations
        return property.getOwner().value.equals(String.valueOf(clientUuid.getValue()));
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
        return clientUuid.equals(otherPredicate.clientUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientUuid", clientUuid)
                .toString();
    }
}
