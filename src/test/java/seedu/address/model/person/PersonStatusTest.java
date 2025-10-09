package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonStatus(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PersonStatus("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new PersonStatus(" "));
        assertThrows(IllegalArgumentException.class, () -> new PersonStatus("Actve"));
    }

    @Test
    public void isValidStatus() {
        assertTrue(PersonStatus.isValidStatus("Active"));
        assertTrue(PersonStatus.isValidStatus("Inactive"));
        assertTrue(PersonStatus.isValidStatus("")); // empty string allowed
        assertTrue(PersonStatus.isValidStatus("active")); // case-insensitive
        assertTrue(PersonStatus.isValidStatus("INACTIVE"));
        assertFalse(PersonStatus.isValidStatus("Invalid"));
    }

    @Test
    public void equals() {
        PersonStatus s1 = new PersonStatus("Active");
        assertEquals(new PersonStatus("Active"), s1);
        assertEquals(s1, s1);
        assertNotEquals(null, s1);
        assertNotEquals("string", s1);
        assertNotEquals(new PersonStatus("Inactive"), s1);
    }
}
