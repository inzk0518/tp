package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import org.junit.jupiter.api.Test;

import seedu.address.model.property.predicates.AssociatedWithContactPredicate;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.PropertyBuilderUtil;

public class AssociatedWithContactPredicateTest {

    private static final Uuid CONTACT_UUID_1 = new Uuid(1, CONTACT);
    private static final Uuid CONTACT_UUID_2 = new Uuid(2, CONTACT);

    @Test
    public void test_propertyOwnedByContact_returnsTrue() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);

        // Property owned by contact UUID 1
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("1").build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_propertyNotOwnedByContact_returnsFalse() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);

        // Property owned by different contact
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("2").build();

        assertFalse(predicate.test(property));
    }

    @Test
    public void test_contactIsBuyer_returnsTrue() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);

        // Property where contact 1 is buyer
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner("2")
                .withBuyingContactIds(1)
                .build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_contactIsSeller_returnsTrue() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);

        // Property where contact 1 is seller
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner("2")
                .withSellingContactIds(1)
                .build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_contactMultipleRoles_returnsTrue() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);

        // Property where contact is both buyer and seller
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner("2")
                .withBuyingContactIds(1)
                .withSellingContactIds(1)
                .build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_contactNotAssociated_returnsFalse() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);

        // Property with different owner and no buyer/seller links
        Property property = new PropertyBuilderUtil(PROPERTY_BETA).withOwner("2").build();

        assertFalse(predicate.test(property));
    }

    @Test
    public void test_nullProperty_returnsFalse() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);
        assertFalse(predicate.test(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);
        assertEquals(predicate, predicate);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AssociatedWithContactPredicate firstPredicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);
        AssociatedWithContactPredicate secondPredicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);

        assertEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        AssociatedWithContactPredicate firstPredicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);
        AssociatedWithContactPredicate secondPredicate = new AssociatedWithContactPredicate(CONTACT_UUID_2);

        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);
        assertNotEquals("not a predicate", predicate);
    }

    @Test
    public void equals_null_returnsFalse() {
        AssociatedWithContactPredicate predicate = new AssociatedWithContactPredicate(CONTACT_UUID_1);
        assertNotEquals(null, predicate);
    }
}
