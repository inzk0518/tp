package seedu.address.model.property;

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
}
