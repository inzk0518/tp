package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_AMY_BUYER_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_BOB_SELLER_PROPERTY_BETA;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.getTypicalProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.testutil.LinkDescriptorBuilder;

public class LinkDescriptorTest {

    @Test
    public void getPeopleInList_duplicatePersonIds_throwsCommandException() {

        List<Person> personList = new ArrayList<>(getTypicalPersons());
        personList.add(ALICE);
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPersonIds(Set.of(ALICE.getUuid())).build();

        assertThrows(CommandException.class, () -> linkDescriptor.getPeopleInList(personList));
    }

    @Test
    public void getPropertiesInList_duplicatePropertyIds_throwsCommandException() {

        List<Property> personList = new ArrayList<>(getTypicalProperties());
        personList.add(PROPERTY_ALPHA);
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getId())).build();

        assertThrows(CommandException.class, () -> linkDescriptor.getPropertiesInList(personList));
    }

    @Test
    public void getUpdatedPeople_unknownRelationship_throwsCommandException() {

        List<Person> personList = new ArrayList<>(getTypicalPersons());
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPersonIds(Set.of(ALICE.getUuid()))
                .withRelationship("owner")
                .build();

        assertThrows(CommandException.class, () -> linkDescriptor.getUpdatedPeople(personList));
    }

    @Test
    public void getUpdatedProperties_unknownRelationship_throwsCommandException() {

        List<Property> personList = new ArrayList<>(getTypicalProperties());
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getId()))
                .withRelationship("tenant")
                .build();

        assertThrows(CommandException.class, () -> linkDescriptor.getUpdatedProperties(personList));
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

        // different personId -> returns false
        editedDescriptor = new LinkDescriptorBuilder(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA)
                .withPersonIds(LINK_DESC_BOB_SELLER_PROPERTY_BETA.getPersonIds()).build();
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
                + "{personIds=" + descriptor.getPersonIds()
                + ", relationship=" + descriptor.getRelationship()
                + ", propertyIds=" + descriptor.getPropertyIds() + "}";
        assertTrue(descriptor.toString().equals(expectedString));
    }
}
