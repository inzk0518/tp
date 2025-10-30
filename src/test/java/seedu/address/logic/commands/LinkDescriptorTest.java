package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_ALICE_BUYER_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_AMY_BUYER_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_BENSON_SELLER_PROPERTY_BETA;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_BOB_SELLER_PROPERTY_BETA;
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

import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.testutil.LinkDescriptorBuilder;

public class LinkDescriptorTest {

    @Test
    public void getContactsInList_duplicateContactIds_throwsCommandException() {

        List<Contact> contactList = new ArrayList<>(getTypicalContacts());
        contactList.add(ALICE);
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withContactIds(Set.of(ALICE.getUuid())).build();

        assertThrows(CommandException.class, () -> linkDescriptor.getContactsInList(contactList));
    }

    @Test
    public void getPropertiesInList_duplicatePropertyIds_throwsCommandException() {

        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        propertyList.add(PROPERTY_ALPHA);
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())).build();

        assertThrows(CommandException.class, () -> linkDescriptor.getPropertiesInList(propertyList));
    }

    @Test
    public void getUpdatedContacts_unknownRelationship_throwsCommandException() {

        List<Contact> contactList = new ArrayList<>(getTypicalContacts());
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withContactIds(Set.of(ALICE.getUuid()))
                .withRelationship("owner")
                .build();

        assertThrows(CommandException.class, () -> linkDescriptor.getUpdatedContacts(contactList));
    }

    @Test
    public void getUpdatedProperties_unknownRelationship_throwsCommandException() {

        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid()))
                .withRelationship("tenant")
                .build();

        assertThrows(CommandException.class, () -> linkDescriptor.getUpdatedProperties(propertyList));
    }

    @Test
    public void throwExceptionIfLinked_linkedAsBuyer_throwsCommandException() {
        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        List<Contact> contactList = new ArrayList<>(getTypicalContacts());

        propertyList.set(propertyList.indexOf(PROPERTY_ALPHA),
                PROPERTY_ALPHA.duplicateWithNewBuyingContactIds(Set.of(ALICE.getUuid())));
        contactList.set(contactList.indexOf(ALICE),
                ALICE.duplicateWithNewBuyingPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())));

        LinkDescriptor linkDescriptor = new LinkDescriptor(LINK_DESC_ALICE_BUYER_PROPERTY_ALPHA);

        assertThrows(CommandException.class, () -> linkDescriptor.throwExceptionIfLinked(contactList, propertyList));
    }

    @Test
    public void throwExceptionIfLinked_linkedAsSeller_throwsCommandException() {
        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        List<Contact> contactList = new ArrayList<>(getTypicalContacts());

        propertyList.set(propertyList.indexOf(PROPERTY_BETA),
                PROPERTY_BETA.duplicateWithNewSellingContactIds(Set.of(BENSON.getUuid())));
        contactList.set(contactList.indexOf(BENSON),
                BENSON.duplicateWithNewSellingPropertyIds(Set.of(PROPERTY_BETA.getUuid())));

        LinkDescriptor linkDescriptor = new LinkDescriptor(LINK_DESC_BENSON_SELLER_PROPERTY_BETA);

        assertThrows(CommandException.class, () -> linkDescriptor.throwExceptionIfLinked(contactList, propertyList));
    }

    @Test
    public void throwExceptionIfLinked_unlinked_noException() {
        List<Property> propertyList = new ArrayList<>(getTypicalProperties());
        List<Contact> contactList = new ArrayList<>(getTypicalContacts());

        LinkDescriptor linkDescriptor = new LinkDescriptor(LINK_DESC_ALICE_BUYER_PROPERTY_ALPHA);

        assertDoesNotThrow(() -> linkDescriptor.throwExceptionIfLinked(contactList, propertyList));
    }

    @Test
    public void equals() {
        // same values -> returns true
        LinkDescriptor descriptorWithSameValues = new LinkDescriptor(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA);
        assertTrue(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA));

        // null -> returns false
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(null));

        // different types -> returns false
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(5));

        // different values -> returns false
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(LINK_DESC_BOB_SELLER_PROPERTY_BETA));

        // different relationship -> returns false
        LinkDescriptor editedDescriptor = new LinkDescriptorBuilder(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA)
                .withRelationship(LINK_DESC_BOB_SELLER_PROPERTY_BETA.getRelationship()).build();
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(editedDescriptor));

        // different contactId -> returns false
        editedDescriptor = new LinkDescriptorBuilder(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA)
                .withContactIds(LINK_DESC_BOB_SELLER_PROPERTY_BETA.getContactIds()).build();
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(editedDescriptor));

        // different propertyId -> returns false
        editedDescriptor = new LinkDescriptorBuilder(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA)
                .withPropertyIds(LINK_DESC_BOB_SELLER_PROPERTY_BETA.getPropertyIds()).build();
        assertFalse(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        LinkDescriptor descriptor = LINK_DESC_AMY_BUYER_PROPERTY_ALPHA;
        String expectedString = LinkDescriptor.class.getCanonicalName()
                + "{contactIds=" + descriptor.getContactIds()
                + ", relationship=" + descriptor.getRelationship()
                + ", propertyIds=" + descriptor.getPropertyIds() + "}";
        assertTrue(descriptor.toString().equals(expectedString));
    }
}
