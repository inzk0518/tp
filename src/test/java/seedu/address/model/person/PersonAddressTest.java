package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonAddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonAddress(null));
    }

    @Test
    public void constructor_blankAddress_isAllowed() {
        assertTrue(PersonAddress.isValidAddress(""));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> PersonAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(PersonAddress.isValidAddress(" ")); // spaces only
        assertFalse(PersonAddress.isValidAddress("a".repeat(201))); // exactly 201 char

        // valid addresses
        assertTrue(PersonAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(PersonAddress.isValidAddress("-")); // one character
        assertTrue(PersonAddress.isValidAddress("")); // empty string
        assertTrue(PersonAddress.isValidAddress(
                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
        assertTrue(PersonAddress.isValidAddress("a".repeat(200))); // exactly 200 char
    }

    @Test
    public void equals() {
        PersonAddress address = new PersonAddress("Valid Address");

        // same values -> returns true
        assertEquals(new PersonAddress("Valid Address"), address);

        // same object -> returns true
        assertEquals(address, address);

        // null -> returns false
        assertNotEquals(null, address);

        // different types -> returns false
        assertNotEquals(5.0f, address);

        // different values -> returns false
        assertNotEquals(new PersonAddress("Other Valid Address"), address);
    }
}
