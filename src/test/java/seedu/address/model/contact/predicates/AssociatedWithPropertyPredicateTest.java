package seedu.address.model.contact.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.ContactBuilderUtil;
import seedu.address.testutil.PropertyBuilderUtil;

/**
 * Unit tests for {@link AssociatedWithPropertyPredicate}.
 */
public class AssociatedWithPropertyPredicateTest {

    private static final Uuid PROPERTY_UUID_1 = new Uuid(1, PROPERTY);
    private static final Uuid PROPERTY_UUID_2 = new Uuid(2, PROPERTY);
    private static final Uuid CONTACT_UUID_1 = new Uuid(10, CONTACT);
    private static final Uuid CONTACT_UUID_2 = new Uuid(11, CONTACT);

    @Test
    public void test_contactIsOwner_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner(String.valueOf(CONTACT_UUID_1.getValue()))
                .withUuid(PROPERTY_UUID_1.getValue())
                .build();

        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(contact));
    }

    @Test
    public void test_contactIsBuyer_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withBuyingContactIds(CONTACT_UUID_1)
                .build();

        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(contact));
    }

    @Test
    public void test_contactIsSeller_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withSellingContactIds(CONTACT_UUID_1)
                .build();

        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(contact));
    }

    @Test
    public void test_contactMultipleRoles_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withOwner(String.valueOf(CONTACT_UUID_1.getValue()))
                .withBuyingContactIds(CONTACT_UUID_1)
                .withSellingContactIds(CONTACT_UUID_1)
                .build();

        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(contact));
    }

    @Test
    public void test_propertyUuidMismatch_returnsFalse() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_2.getValue())
                .withOwner(String.valueOf(CONTACT_UUID_1.getValue()))
                .build();

        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertFalse(predicate.test(contact));
    }

    @Test
    public void test_contactNotAssociated_returnsFalse() {
        Property property = new PropertyBuilderUtil(PROPERTY_BETA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withOwner(String.valueOf(CONTACT_UUID_2.getValue()))
                .build();

        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertFalse(predicate.test(contact));
    }

    @Test
    public void test_nullContact_returnsFalse() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertFalse(predicate.test(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());
        assertEquals(predicate, predicate);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AssociatedWithPropertyPredicate first =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());
        AssociatedWithPropertyPredicate second =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());
        assertEquals(first, second);
    }

    @Test
    public void equals_differentUuid_returnsFalse() {
        AssociatedWithPropertyPredicate first =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());
        AssociatedWithPropertyPredicate second =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_2, List.of());
        assertNotEquals(first, second);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());
        assertNotEquals("not a predicate", predicate);
    }

    @Test
    public void equals_null_returnsFalse() {
        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());
        assertNotEquals(null, predicate);
    }

    @Test
    public void test_emptyPropertyList_returnsFalse() {
        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());

        assertFalse(predicate.test(contact));
    }

    @Test
    public void test_propertyUuidMatchesButNoAssociations_returnsFalse() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .build();

        Contact contact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertFalse(predicate.test(contact));
    }

    @Test
    public void test_differentPropertyType_returnsFalse() {
        Property unrelatedProperty = new PropertyBuilderUtil(PROPERTY_BETA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withOwner(String.valueOf(CONTACT_UUID_2.getValue()))
                .build();

        Contact unrelatedContact = new ContactBuilderUtil().withUuid(CONTACT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(unrelatedProperty));

        assertFalse(predicate.test(unrelatedContact));
    }

    @Test
    public void toString_containsUuid() {
        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of());

        String result = predicate.toString();

        assertTrue(result.contains(PROPERTY_UUID_1.toString()));
    }
}
