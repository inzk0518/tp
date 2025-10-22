package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for PropertyCard.
 * Verify basic construction.
 */
public class PropertyCardTest {

    @BeforeAll
    public static void initJavaFx() {
        JavaFxInitializer.initialize();
    }

    @Test
    public void constructor_success() {
        assertDoesNotThrow(() -> new PropertyCard(PROPERTY_ALPHA, 1));
    }

    @Test
    public void constructor_initializesPropertyField() {
        PropertyCard propertyCard = new PropertyCard(PROPERTY_ALPHA, 1);
        assertNotNull(propertyCard.property);
        assertEquals(PROPERTY_ALPHA, propertyCard.property);
    }

    @Test
    public void constructor_initializesRoot() {
        PropertyCard propertyCard = new PropertyCard(PROPERTY_ALPHA, 1);
        assertNotNull(propertyCard.getRoot());
    }

    @Test
    public void constructor_differentIndex_success() {
        assertDoesNotThrow(() -> new PropertyCard(PROPERTY_BETA, 5));
    }

    @Test
    public void constructor_multipleInstances_success() {
        PropertyCard card1 = new PropertyCard(PROPERTY_ALPHA, 1);
        PropertyCard card2 = new PropertyCard(PROPERTY_BETA, 2);

        assertNotNull(card1);
        assertNotNull(card2);
        assertEquals(PROPERTY_ALPHA, card1.property);
        assertEquals(PROPERTY_BETA, card2.property);
    }
}
