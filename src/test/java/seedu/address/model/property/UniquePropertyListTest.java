package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA_VARIANT;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

class UniquePropertyListTest {

    private UniquePropertyList uniquePropertyList;

    @BeforeEach
    void setUp() {
        uniquePropertyList = new UniquePropertyList();
    }

    @Test
    void contains_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.contains(null));
    }

    @Test
    void contains_propertyNotInList_returnsFalse() {
        assertFalse(uniquePropertyList.contains(PROPERTY_ALPHA));
    }

    @Test
    void contains_propertyInList_returnsTrue() {
        uniquePropertyList.add(PROPERTY_ALPHA);
        assertTrue(uniquePropertyList.contains(PROPERTY_ALPHA));
    }

    @Test
    void contains_propertyWithSameIdentity_returnsTrue() {
        uniquePropertyList.add(PROPERTY_ALPHA);
        assertTrue(uniquePropertyList.contains(PROPERTY_ALPHA_VARIANT));
    }

    @Test
    void add_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePropertyList.add(null));
    }

    @Test
    void add_duplicateProperty_throwsDuplicatePropertyException() {
        uniquePropertyList.add(PROPERTY_ALPHA);
        assertThrows(DuplicatePropertyException.class, () -> uniquePropertyList.add(PROPERTY_ALPHA_VARIANT));
    }

    @Test
    void setProperty_missingTarget_throwsPropertyNotFoundException() {
        assertThrows(PropertyNotFoundException.class, () ->
                uniquePropertyList.setProperty(PROPERTY_ALPHA, PROPERTY_ALPHA));
    }

    @Test
    void setProperty_duplicateIdentity_throwsDuplicatePropertyException() {
        uniquePropertyList.add(PROPERTY_ALPHA);
        uniquePropertyList.add(PROPERTY_BETA);

        assertThrows(DuplicatePropertyException.class, () ->
                uniquePropertyList.setProperty(PROPERTY_BETA, PROPERTY_ALPHA_VARIANT));
    }

    @Test
    void remove_missingProperty_throwsPropertyNotFoundException() {
        uniquePropertyList.add(PROPERTY_ALPHA);

        assertThrows(PropertyNotFoundException.class, () -> uniquePropertyList.remove(PROPERTY_BETA));
    }

    @Test
    void equals_sameInstance_returnsTrue() {
        assertTrue(uniquePropertyList.equals(uniquePropertyList));
    }

    @Test
    void equals_sameContents_returnsTrue() {
        uniquePropertyList.add(PROPERTY_ALPHA);
        UniquePropertyList otherList = new UniquePropertyList();
        otherList.add(PROPERTY_ALPHA);

        assertTrue(uniquePropertyList.equals(otherList));
    }

    @Test
    void equals_differentContents_returnsFalse() {
        uniquePropertyList.add(PROPERTY_ALPHA);
        UniquePropertyList otherList = new UniquePropertyList();
        otherList.add(PROPERTY_BETA);

        assertFalse(uniquePropertyList.equals(otherList));
    }

    @Test
    void equals_differentType_returnsFalse() {
        assertFalse(uniquePropertyList.equals("not a property list"));
    }

    @Test
    void equals_null_returnsFalse() {
        assertFalse(uniquePropertyList.equals(null));
    }

    @Test
    void hashCode_matchesInternalList() {
        uniquePropertyList.add(PROPERTY_ALPHA);
        UniquePropertyList otherList = new UniquePropertyList();
        otherList.add(PROPERTY_ALPHA);

        assertEquals(uniquePropertyList.hashCode(), otherList.hashCode());
    }
}
