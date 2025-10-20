package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUYER;
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
        assertEquals(ALICE, ALICE);

        // null -> returns false
        assertNotEquals(null, ALICE);

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilderUtil(ALICE)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_BUYER)
                .build();
        assertEquals(ALICE, editedAlice);

        // same name, different phone -> returns false
        editedAlice = new PersonBuilderUtil(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different name, same phone -> returns false
        editedAlice = new PersonBuilderUtil(ALICE).withName(VALID_NAME_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // name differs in case but phone same -> returns true (case-insensitive check of duplicates)
        Person editedBob = new PersonBuilderUtil(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertEquals(BOB, editedBob);

        // name has trailing spaces but phone same -> returns true (because trailing spaces are trimmed)
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilderUtil(BOB).withName(nameWithTrailingSpaces).build();
        assertEquals(BOB, editedBob);
    }

    @Test
    public void equals() { // only when same phone and name
        // same values -> returns true
        Person aliceCopy = new PersonBuilderUtil(ALICE).build();
        assertEquals(ALICE, aliceCopy);

        // same object -> returns true
        assertEquals(ALICE, ALICE);

        // null -> returns false
        assertNotEquals(null, ALICE);

        // different type -> returns false
        assertNotEquals(5, ALICE);

        // different person -> returns false
        assertNotEquals(ALICE, BOB);

        // different name -> returns false
        Person editedAlice = new PersonBuilderUtil(ALICE).withName(VALID_NAME_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different phone -> returns false
        editedAlice = new PersonBuilderUtil(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertNotEquals(ALICE, editedAlice);

        // different email -> returns true
        editedAlice = new PersonBuilderUtil(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertEquals(ALICE, editedAlice);

        // different address -> returns true
        editedAlice = new PersonBuilderUtil(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertEquals(ALICE, editedAlice);

        // different tags -> returns true
        editedAlice = new PersonBuilderUtil(ALICE).withTags(VALID_TAG_BUYER).build();
        assertEquals(ALICE, editedAlice);
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
