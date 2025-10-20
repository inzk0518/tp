package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NotesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null));
    }

    @Test
    public void constructor_invalidNotes_throwsIllegalArgumentException() {
        // Notes accept anything up to 500 chars, so test exceeding length
        String tooLong = "a".repeat(501);
        assertThrows(IllegalArgumentException.class, () -> new Notes(tooLong));
    }

    @Test
    public void isValidNotes() {
        assertTrue(Notes.isValidNotes("")); // empty notes
        assertTrue(Notes.isValidNotes("any notes here"));
        assertTrue(Notes.isValidNotes("a".repeat(500))); // exactly 500 characters
        assertFalse(Notes.isValidNotes("a".repeat(501))); // exactly 501 characters
    }

    @Test
    public void equals() {
        Notes n1 = new Notes("note1");
        assertEquals(new Notes("note1"), n1);
        assertEquals(n1, n1);
        assertNotEquals(null, n1);
        assertNotEquals("string", n1);
        assertNotEquals(new Notes("different"), n1);
    }
}
