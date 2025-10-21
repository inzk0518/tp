package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

        // valid addresses
        assertTrue(ContactAddress.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(ContactAddress.isValidAddress("-")); // one character
        assertTrue(ContactAddress.isValidAddress(
                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        ContactAddress address = new ContactAddress("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new ContactAddress("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new ContactAddress("Other Valid Address")));
    }
}
