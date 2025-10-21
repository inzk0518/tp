package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactAddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactAddress(null));
    }

    @Test
    public void constructor_blankAddress_isAllowed() {
        assertTrue(ContactAddress.isValidAddress(""));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> ContactAddress.isValidAddress(null));

        // invalid addresses
        assertFalse(ContactAddress.isValidAddress(" ")); // spaces only
        assertFalse(ContactAddress.isValidAddress("a".repeat(201))); // exactly 201 char

        // valid addresses
        assertTrue(ContactAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(ContactAddress.isValidAddress("-")); // one character
        assertTrue(ContactAddress.isValidAddress("")); // empty string
        assertTrue(ContactAddress.isValidAddress(
                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
        assertTrue(ContactAddress.isValidAddress("a".repeat(200))); // exactly 200 char
    }

    @Test
    public void equals() {
        ContactAddress address = new ContactAddress("Valid Address");

        // same values -> returns true
        assertEquals(new ContactAddress("Valid Address"), address);

        // same object -> returns true
        assertEquals(address, address);

        // null -> returns false
        assertNotEquals(null, address);

        // different types -> returns false
        assertNotEquals(5.0f, address);

        // different values -> returns false
        assertNotEquals(new ContactAddress("Other Valid Address"), address);
    }
}
