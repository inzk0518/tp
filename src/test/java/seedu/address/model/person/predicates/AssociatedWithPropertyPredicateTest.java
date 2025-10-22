package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.PersonBuilderUtil;
import seedu.address.testutil.PropertyBuilderUtil;

/**
 * Unit tests for {@link AssociatedWithPropertyPredicate}.
 */
public class AssociatedWithPropertyPredicateTest {

    private static final Uuid PROPERTY_UUID_1 = new Uuid(1, PROPERTY);
    private static final Uuid PROPERTY_UUID_2 = new Uuid(2, PROPERTY);
    private static final Uuid CLIENT_UUID_1 = new Uuid(10, PERSON);
    private static final Uuid CLIENT_UUID_2 = new Uuid(11, PERSON);

    @Test
    public void test_clientIsOwner_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withOwner(String.valueOf(CLIENT_UUID_1.getValue()))
                .withUuid(PROPERTY_UUID_1.getValue())
                .build();

        Person person = new PersonBuilderUtil().withUuid(CLIENT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_clientIsBuyer_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withBuyingPersonIds(CLIENT_UUID_1)
                .build();

        Person person = new PersonBuilderUtil().withUuid(CLIENT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_clientIsSeller_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withSellingPersonIds(CLIENT_UUID_1)
                .build();

        Person person = new PersonBuilderUtil().withUuid(CLIENT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_clientMultipleRoles_returnsTrue() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withOwner(String.valueOf(CLIENT_UUID_1.getValue()))
                .withBuyingPersonIds(CLIENT_UUID_1)
                .withSellingPersonIds(CLIENT_UUID_1)
                .build();

        Person person = new PersonBuilderUtil().withUuid(CLIENT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_propertyUuidMismatch_returnsFalse() {
        Property property = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withUuid(PROPERTY_UUID_2.getValue())
                .withOwner(String.valueOf(CLIENT_UUID_1.getValue()))
                .build();

        Person person = new PersonBuilderUtil().withUuid(CLIENT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_clientNotAssociated_returnsFalse() {
        Property property = new PropertyBuilderUtil(PROPERTY_BETA)
                .withUuid(PROPERTY_UUID_1.getValue())
                .withOwner(String.valueOf(CLIENT_UUID_2.getValue()))
                .build();

        Person person = new PersonBuilderUtil().withUuid(CLIENT_UUID_1.getValue()).build();

        AssociatedWithPropertyPredicate predicate =
                new AssociatedWithPropertyPredicate(PROPERTY_UUID_1, List.of(property));

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_nullPerson_returnsFalse() {
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
}
