package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactBuilderUtil;

public class ContactRegistryTest {

    private ContactRegistry contactRegistry;
    private Contact alice;
    private Contact bob;

    @BeforeEach
    public void setUp() {
        contactRegistry = new ContactRegistry();
        alice = new ContactBuilderUtil().withUuid(1).withName("Alice").withPhone("12345678").build();
        bob = new ContactBuilderUtil().withUuid(2).withName("Bob").withPhone("87654321").build();
    }

    @Test
    public void addContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> contactRegistry.addContact(null));
    }

    @Test
    public void addContact_newContact_success() {
        contactRegistry.addContact(alice);
        assertTrue(contactRegistry.containsId(alice.getUuid().getValue()));
        assertEquals(alice, contactRegistry.getContact(alice.getUuid().getValue()));
    }

    @Test
    public void addContact_duplicateUuid_replacesOldContact() {
        contactRegistry.addContact(alice);
        Contact newAlice = new ContactBuilderUtil().withUuid(alice.getUuid().getValue())
                .withName("Alice New").build();
        contactRegistry.addContact(newAlice);
        assertEquals(newAlice, contactRegistry.getContact(alice.getUuid().getValue()));
    }

    @Test
    public void getContact_existingId_returnsContact() {
        contactRegistry.addContact(bob);
        assertEquals(bob, contactRegistry.getContact(bob.getUuid().getValue()));
    }

    @Test
    public void getContact_nonExistingId_returnsNull() {
        assertNull(contactRegistry.getContact(999));
    }

    @Test
    public void containsId_existingId_returnsTrue() {
        contactRegistry.addContact(alice);
        assertTrue(contactRegistry.containsId(alice.getUuid().getValue()));
    }

    @Test
    public void containsId_nonExistingId_returnsFalse() {
        assertFalse(contactRegistry.containsId(999));
    }

    @Test
    public void removeContact_existingId_removesContact() {
        contactRegistry.addContact(alice);
        contactRegistry.removeContact(alice.getUuid().getValue());
        assertFalse(contactRegistry.containsId(alice.getUuid().getValue()));
    }

    @Test
    public void removeContact_nonExistingId_noExceptionThrown() {
        // Removing a non-existing ID should not throw
        contactRegistry.removeContact(999);
    }
}
