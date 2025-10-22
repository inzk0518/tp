package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for PersonCard.
 */
public class PersonCardTest {

    @BeforeAll
    public static void initJavaFx() {
        JavaFxInitializer.initialize();
    }

    @Test
    public void constructor_success() {
        assertDoesNotThrow(() -> new PersonCard(ALICE, 1));
    }

    @Test
    public void constructor_initializesPersonField() {
        PersonCard personCard = new PersonCard(ALICE, 1);
        assertNotNull(personCard.person);
        assertEquals(ALICE, personCard.person);
    }

    @Test
    public void constructor_initializesRoot() {
        PersonCard personCard = new PersonCard(ALICE, 1);
        assertNotNull(personCard.getRoot());
    }

    @Test
    public void constructor_differentIndex_success() {
        assertDoesNotThrow(() -> new PersonCard(BOB, 5));
    }

    @Test
    public void constructor_multipleInstances_success() {
        PersonCard card1 = new PersonCard(ALICE, 1);
        PersonCard card2 = new PersonCard(BOB, 2);

        assertNotNull(card1);
        assertNotNull(card2);
        assertEquals(ALICE, card1.person);
        assertEquals(BOB, card2.person);
    }
}
