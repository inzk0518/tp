package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilderUtil;

public class PersonRegistryTest {

    private PersonRegistry personRegistry;
    private Person alice;
    private Person bob;

    @BeforeEach
    public void setUp() {
        personRegistry = new PersonRegistry();
        alice = new PersonBuilderUtil().withUuid(1).withName("Alice").withPhone("12345678").build();
        bob = new PersonBuilderUtil().withUuid(2).withName("Bob").withPhone("87654321").build();
    }

    @Test
    public void addPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personRegistry.addPerson(null));
    }

    @Test
    public void addPerson_newPerson_success() {
        personRegistry.addPerson(alice);
        assertTrue(personRegistry.containsId(alice.getUuid().getValue()));
        assertEquals(alice, personRegistry.getPerson(alice.getUuid().getValue()));
    }

    @Test
    public void addPerson_duplicateUuid_replacesOldPerson() {
        personRegistry.addPerson(alice);
        Person newAlice = new PersonBuilderUtil().withUuid(alice.getUuid().getValue())
                .withName("Alice New").build();
        personRegistry.addPerson(newAlice);
        assertEquals(newAlice, personRegistry.getPerson(alice.getUuid().getValue()));
    }

    @Test
    public void getPerson_existingId_returnsPerson() {
        personRegistry.addPerson(bob);
        assertEquals(bob, personRegistry.getPerson(bob.getUuid().getValue()));
    }

    @Test
    public void getPerson_nonExistingId_returnsNull() {
        assertNull(personRegistry.getPerson(999));
    }

    @Test
    public void containsId_existingId_returnsTrue() {
        personRegistry.addPerson(alice);
        assertTrue(personRegistry.containsId(alice.getUuid().getValue()));
    }

    @Test
    public void containsId_nonExistingId_returnsFalse() {
        assertFalse(personRegistry.containsId(999));
    }

    @Test
    public void removePerson_existingId_removesPerson() {
        personRegistry.addPerson(alice);
        personRegistry.removePerson(alice.getUuid().getValue());
        assertFalse(personRegistry.containsId(alice.getUuid().getValue()));
    }

    @Test
    public void removePerson_nonExistingId_noExceptionThrown() {
        // Removing a non-existing ID should not throw
        personRegistry.removePerson(999);
    }
}
