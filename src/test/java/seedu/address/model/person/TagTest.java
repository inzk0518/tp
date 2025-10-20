package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName("  ")); // spaces only
        assertFalse(Tag.isValidTagName("abc")); // invalid tag

        // valid tag
        assertTrue(Tag.isValidTagName("buyer")); // valid tag buyer
        assertTrue(Tag.isValidTagName("seller")); // valid tag seller
        assertTrue(Tag.isValidTagName("tenant")); // valid tag tenant
        assertTrue(Tag.isValidTagName("landlord")); // valid tag landlord
        assertTrue(Tag.isValidTagName("bUyER")); // valid tag non-sensitive case
    }

    @Test
    public void equals() {
        Tag tag = new Tag("buyer");

        // same values -> returns true
        assertEquals(new Tag("buyer"), tag);

        // same object -> returns true
        assertEquals(tag, tag);

        // null -> returns false
        assertNotEquals(null, tag);

        // different types -> returns false
        assertNotEquals(5.0f, tag);

        // different values -> returns false
        assertNotEquals(new Tag("tenant"), tag);
    }

    @Test
    public void toStringMethod() {
        Tag tag = new Tag("buyer");
        String expected = tag.toString();
        assertEquals("[buyer]", expected);
    }

}
