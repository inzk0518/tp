package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactStatus(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ContactStatus("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new ContactStatus(" "));
        assertThrows(IllegalArgumentException.class, () -> new ContactStatus("Actve"));
    }

    @Test
    public void isValidStatus() {
        // equivalence partition: valid input
        assertTrue(ContactStatus.isValidStatus("Active"));
        assertTrue(ContactStatus.isValidStatus("Inactive"));
        assertTrue(ContactStatus.isValidStatus("")); // empty string allowed
        assertTrue(ContactStatus.isValidStatus("InActive")); // case-insensitive

        // equivalence partition: invalid input
        assertFalse(ContactStatus.isValidStatus("Invalid"));
    }

    @Test
    public void equals() {
        ContactStatus s1 = new ContactStatus("Active");
        assertEquals(new ContactStatus("Active"), s1);
        assertEquals(s1, s1);
        assertNotEquals(null, s1);
        assertNotEquals("string", s1);
        assertNotEquals(new ContactStatus("Inactive"), s1);
    }
}
