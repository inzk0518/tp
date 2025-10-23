package seedu.address.model.property;

import java.util.function.Predicate;

import seedu.address.model.uuid.Uuid;

/**
 * Tests that a {@code Property} is associated with a given client UUID,
 * either as an owner, buyer, or seller.
 */
public class AssociatedWithClientPredicate implements Predicate<Property> {

    private final Uuid clientUuid;

    public AssociatedWithClientPredicate(Uuid clientUuid) {
        this.clientUuid = clientUuid;
    }

    @Override
    public boolean test(Property property) {
        if (property == null) {
            return false;
        }

        // Compare owner
        boolean isOwner = property.getOwner() != null
                && property.getOwner().value.equals(String.valueOf(clientUuid.getValue()));

        // Check buyer and seller UUID lists
        boolean isBuyer = property.getBuyingPersonIds() != null
                && property.getBuyingPersonIds().contains(clientUuid);

        boolean isSeller = property.getSellingPersonIds() != null
                && property.getSellingPersonIds().contains(clientUuid);

        return isOwner || isBuyer || isSeller;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AssociatedWithClientPredicate
                && clientUuid.equals(((AssociatedWithClientPredicate) other).clientUuid));
    }
}
