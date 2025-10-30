package seedu.address.model.contact.predicates;

import java.util.function.Predicate;

import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Tests that a {@code Contact} is associated with a given property UUID,
 * either as the owner, a buyer, or a seller.
 */
public class AssociatedWithPropertyPredicate implements Predicate<Contact> {

    private final Uuid propertyUuid;
    private final Iterable<Property> allProperties;

    /**
     * @param propertyUuid UUID of the property to check association against.
     * @param allProperties All properties available (usually from the PropertyBook).
     */
    public AssociatedWithPropertyPredicate(Uuid propertyUuid, Iterable<Property> allProperties) {
        this.propertyUuid = propertyUuid;
        this.allProperties = allProperties;
    }

    @Override
    public boolean test(Contact contact) {
        if (contact == null) {
            return false;
        }

        // Find the property with matching UUID and check if contact is associated
        for (Property property : allProperties) {
            if (property.getUuid().equals(propertyUuid)) {
                return property.isAssociatedWith(contact.getUuid());
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AssociatedWithPropertyPredicate
                && propertyUuid.equals(((AssociatedWithPropertyPredicate) other).propertyUuid));
    }

    @Override
    public String toString() {
        return "AssociatedWithPropertyPredicate{propertyUuid=" + propertyUuid + "}";
    }
}
