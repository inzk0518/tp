package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA_VARIANT;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.property.Property;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.uuid.Uuid;

public class PropertyBookTest {

    private final PropertyBook propertyBook = new PropertyBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), propertyBook.getPropertyList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> propertyBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPropertyBook_replacesData() {
        PropertyBook newData = getTypicalPropertyBook();
        propertyBook.resetData(newData);
        assertEquals(newData, propertyBook);
    }

    @Test
    public void resetData_withDuplicateProperties_throwsDuplicatePropertyException() {
        List<Property> newProperties = Arrays.asList(PROPERTY_ALPHA, PROPERTY_ALPHA_VARIANT);
        PropertyBookStub newData = new PropertyBookStub(newProperties);
        assertThrows(DuplicatePropertyException.class, () -> propertyBook.resetData(newData));
    }

    @Test
    public void hasProperty_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> propertyBook.hasProperty(null));
    }

    @Test
    public void hasProperty_propertyNotInPropertyBook_returnsFalse() {
        assertFalse(propertyBook.hasProperty(PROPERTY_ALPHA));
    }

    @Test
    public void hasProperty_propertyInPropertyBook_returnsTrue() {
        propertyBook.addProperty(PROPERTY_ALPHA);
        assertTrue(propertyBook.hasProperty(PROPERTY_ALPHA));
    }

    @Test
    public void hasProperty_propertyWithSameIdentityFieldsInPropertyBook_returnsTrue() {
        propertyBook.addProperty(PROPERTY_ALPHA);
        assertTrue(propertyBook.hasProperty(PROPERTY_ALPHA_VARIANT));
    }

    @Test
    public void getPropertyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> propertyBook.getPropertyList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = PropertyBook.class.getCanonicalName() + "{properties=" + propertyBook.getPropertyList()
                + "}";
        assertEquals(expected, propertyBook.toString());
    }

    /**
     * A stub ReadOnlyPropertyBook whose properties list can violate interface constraints.
     */
    private static class PropertyBookStub implements ReadOnlyPropertyBook {
        private final ObservableList<Property> properties = FXCollections.observableArrayList();

        PropertyBookStub(Collection<Property> properties) {
            this.properties.setAll(properties);
        }

        @Override
        public ObservableList<Property> getPropertyList() {
            return properties;
        }

        @Override
        public Uuid generateNextUuid() {
            return new Uuid(1, PROPERTY);
        }

        @Override
        public int getNextUuid() {
            return 1;
        }
    }
}
