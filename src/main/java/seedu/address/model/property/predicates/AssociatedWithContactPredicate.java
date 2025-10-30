package seedu.address.model.property.predicates;

import java.util.function.Predicate;

import seedu.address.model.property.Property;
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
        return property != null && property.isAssociatedWith(contactUuid);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AssociatedWithContactPredicate
                && contactUuid.equals(((AssociatedWithContactPredicate) other).contactUuid));
    }
}
