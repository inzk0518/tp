package seedu.address.model.property;

import java.util.function.Predicate;

import seedu.address.model.uuid.Uuid;

/**
 * Tests that a {@code Property} is associated with a given contact UUID,
 * either as an owner, buyer, or seller.
 */
public class AssociatedWithContactPredicate implements Predicate<Property> {

    private final Uuid contactUuid;

    public AssociatedWithContactPredicate(Uuid contactUuid) {
        this.contactUuid = contactUuid;
    }

    @Override
    public boolean test(Property property) {
        if (property == null) {
            return false;
        }

        // Compare owner
        boolean isOwner = property.getOwner() != null
                && property.getOwner().value.equals(String.valueOf(contactUuid.getValue()));

        // Check buyer and seller UUID lists
        boolean isBuyer = property.getBuyingContactIds() != null
                && property.getBuyingContactIds().contains(contactUuid);

        boolean isSeller = property.getSellingContactIds() != null
                && property.getSellingContactIds().contains(contactUuid);

        return isOwner || isBuyer || isSeller;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AssociatedWithContactPredicate
                && contactUuid.equals(((AssociatedWithContactPredicate) other).contactUuid));
    }
}
