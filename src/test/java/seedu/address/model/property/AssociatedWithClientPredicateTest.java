package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import org.junit.jupiter.api.Test;

import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.PropertyBuilderUtil;

public class AssociatedWithClientPredicateTest {

    private static final Uuid CLIENT_UUID_1 = new Uuid(1, PERSON);
    private static final Uuid CLIENT_UUID_2 = new Uuid(2, PERSON);

    @Test
    public void test_propertyOwnedByClient_returnsTrue() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);

        // Property owned by client UUID 1
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("1").build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_propertyNotOwnedByClient_returnsFalse() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);

        // Property owned by different client
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA).withOwner("2").build();

        assertFalse(predicate.test(property));
    }

    @Test
    public void test_clientIsBuyer_returnsTrue() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);

        // Property where client 1 is buyer
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner("2")
                .withBuyingPersonIds(1)
                .build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_clientIsSeller_returnsTrue() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);

        // Property where client 1 is seller
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner("2")
                .withSellingPersonIds(1)
                .build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_clientMultipleRoles_returnsTrue() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);

        // Property where client is both buyer and seller
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner("2")
                .withBuyingPersonIds(1)
                .withSellingPersonIds(1)
                .build();

        assertTrue(predicate.test(property));
    }

    @Test
    public void test_clientNotAssociated_returnsFalse() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);

        // Property with different owner and no buyer/seller links
        Property property = new PropertyBuilderUtil(PROPERTY_BETA).withOwner("2").build();

        assertFalse(predicate.test(property));
    }

    @Test
    public void test_nullProperty_returnsFalse() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);
        assertFalse(predicate.test(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);
        assertEquals(predicate, predicate);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AssociatedWithClientPredicate firstPredicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);
        AssociatedWithClientPredicate secondPredicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);

        assertEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        AssociatedWithClientPredicate firstPredicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);
        AssociatedWithClientPredicate secondPredicate = new AssociatedWithClientPredicate(CLIENT_UUID_2);

        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);
        assertNotEquals("not a predicate", predicate);
    }

    @Test
    public void equals_null_returnsFalse() {
        AssociatedWithClientPredicate predicate = new AssociatedWithClientPredicate(CLIENT_UUID_1);
        assertNotEquals(null, predicate);
    }
}
