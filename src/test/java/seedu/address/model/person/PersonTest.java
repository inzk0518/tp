package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilderUtil;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilderUtil().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilderUtil(ALICE)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(ALICE.equals(editedAlice));

        // same name, different phone -> returns false
        editedAlice = new PersonBuilderUtil(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different name, same phone -> returns false
        editedAlice = new PersonBuilderUtil(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // name differs in case but phone same -> returns false (case-sensitive check)
        Person editedBob = new PersonBuilderUtil(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.equals(editedBob));

        // name has trailing spaces but phone same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilderUtil(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.equals(editedBob));
    }

    @Test
    public void equals() { // only when same phone and name
        // same values -> returns true
        Person aliceCopy = new PersonBuilderUtil(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilderUtil(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilderUtil(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns true
        editedAlice = new PersonBuilderUtil(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different address -> returns true
        editedAlice = new PersonBuilderUtil(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different tags -> returns true
        editedAlice = new PersonBuilderUtil(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName()
                + "{uuid=" + ALICE.getUuid()
                + ", name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags()
                + ", budgetMin=" + ALICE.getBudgetMin()
                + ", budgetMax=" + ALICE.getBudgetMax()
                + ", notes=" + ALICE.getNotes()
                + ", status=" + ALICE.getStatus()
                + ", buyingPropertyIds=" + ALICE.getBuyingPropertyIds()
                + ", sellingPropertyIds=" + ALICE.getSellingPropertyIds()
                + "}";
        assertEquals(expected, ALICE.toString());
    }
}
