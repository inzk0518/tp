package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_ALICE_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_AMY_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_BENSON_PROPERTY_BETA;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_BOB_PROPERTY_BETA;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.getTypicalContacts;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;
import static seedu.address.testutil.TypicalProperties.getTypicalProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnlinkCommand.UnlinkDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.testutil.UnlinkDescriptorBuilder;

public class UnlinkDescriptorTest {

    @Test
    public void getContactsInList_duplicateContactIds_throwsCommandException() {

        List<Contact> contactList = new ArrayList<>(getTypicalContacts());
        contactList.add(ALICE);
        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withContactIds(Set.of(ALICE.getUuid())).build();

        assertThrows(CommandException.class, () -> unlinkDescriptor.getContactsInList(contactList));

    }

    @Test
    public void getPropertiesInList_duplicatePropertyIds_throwsCommandException() {

        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        propertyList.add(PROPERTY_ALPHA);
        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())).build();

        assertThrows(CommandException.class, () -> unlinkDescriptor.getPropertiesInList(propertyList));
    }

    @Test
    public void throwExceptionIfUnlinked_linkedAsBuyer_noException() {
        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        List<Contact> contactList = new ArrayList<>(getTypicalContacts());

        propertyList.set(propertyList.indexOf(PROPERTY_ALPHA),
                PROPERTY_ALPHA.duplicateWithNewBuyingContactIds(Set.of(ALICE.getUuid())));
        contactList.set(contactList.indexOf(ALICE),
                ALICE.duplicateWithNewBuyingPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())));

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptor(UNLINK_DESC_ALICE_PROPERTY_ALPHA);

        assertDoesNotThrow(() -> unlinkDescriptor.throwExceptionIfUnlinked(contactList, propertyList));
    }

    @Test
    public void throwExceptionIfUnlinked_linkedAsSeller_noException() {
        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        List<Contact> contactList = new ArrayList<>(getTypicalContacts());

        propertyList.set(propertyList.indexOf(PROPERTY_BETA),
                PROPERTY_BETA.duplicateWithNewSellingContactIds(Set.of(BENSON.getUuid())));
        contactList.set(contactList.indexOf(BENSON),
                BENSON.duplicateWithNewSellingPropertyIds(Set.of(PROPERTY_BETA.getUuid())));

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptor(UNLINK_DESC_BENSON_PROPERTY_BETA);

        assertDoesNotThrow(() -> unlinkDescriptor.throwExceptionIfUnlinked(contactList, propertyList));
    }

    @Test
    public void throwExceptionIfUnlinked_unlinked_throwsCommandException() {
        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        List<Contact> contactList = new ArrayList<>(getTypicalContacts());

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptor(UNLINK_DESC_ALICE_PROPERTY_ALPHA);

        assertThrows(CommandException.class, () -> unlinkDescriptor
                .throwExceptionIfUnlinked(contactList, propertyList));
    }

    @Test
    public void equals() {
        // same values -> returns true
        UnlinkDescriptor descriptorWithSameValues = new UnlinkDescriptor(UNLINK_DESC_AMY_PROPERTY_ALPHA);
        assertTrue(UNLINK_DESC_AMY_PROPERTY_ALPHA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(UNLINK_DESC_AMY_PROPERTY_ALPHA.equals(UNLINK_DESC_AMY_PROPERTY_ALPHA));

        // null -> returns false
        assertFalse(UNLINK_DESC_AMY_PROPERTY_ALPHA.equals(null));

        // different types -> returns false
        assertFalse(UNLINK_DESC_AMY_PROPERTY_ALPHA.equals(5));

        // different values -> returns false
        assertFalse(UNLINK_DESC_AMY_PROPERTY_ALPHA.equals(UNLINK_DESC_BOB_PROPERTY_BETA));

        // different contactId -> returns false
        UnlinkDescriptor editedDescriptor = new UnlinkDescriptorBuilder(UNLINK_DESC_AMY_PROPERTY_ALPHA)
                .withContactIds(UNLINK_DESC_BOB_PROPERTY_BETA.getContactIds()).build();
        assertFalse(UNLINK_DESC_AMY_PROPERTY_ALPHA.equals(editedDescriptor));

        // different propertyId -> returns false
        editedDescriptor = new UnlinkDescriptorBuilder(UNLINK_DESC_AMY_PROPERTY_ALPHA)
                .withPropertyIds(UNLINK_DESC_BOB_PROPERTY_BETA.getPropertyIds()).build();
        assertFalse(UNLINK_DESC_AMY_PROPERTY_ALPHA.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        UnlinkDescriptor descriptor = UNLINK_DESC_AMY_PROPERTY_ALPHA;
        String expectedString = UnlinkDescriptor.class.getCanonicalName()
                + "{contactIds=" + descriptor.getContactIds()
                + ", propertyIds=" + descriptor.getPropertyIds() + "}";
        assertTrue(descriptor.toString().equals(expectedString));
    }
}
