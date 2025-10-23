package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;

/**
 * Tests that a {@code Person} is associated with a given property UUID,
 * either as the owner, a buyer, or a seller.
 */
public class AssociatedWithPropertyPredicate implements Predicate<Person> {

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
    public boolean test(Person person) {
        if (person == null) {
            return false;
        }

        for (Property property : allProperties) {
            // Match the property we’re filtering against
            if (property.getUuid().equals(propertyUuid)) {

                boolean isOwner = property.getOwner() != null
                        && property.getOwner().value.equals(String.valueOf(person.getUuid().getValue()));

                boolean isBuyer = property.getBuyingPersonIds().contains(person.getUuid());

                boolean isSeller = property.getSellingPersonIds().contains(person.getUuid());

                return isOwner || isBuyer || isSeller;
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
