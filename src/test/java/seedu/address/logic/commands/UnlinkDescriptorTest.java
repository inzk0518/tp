package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_AMY_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_BOB_PROPERTY_BETA;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.getTypicalProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnlinkCommand.UnlinkDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.testutil.UnlinkDescriptorBuilder;

public class UnlinkDescriptorTest {

    @Test
    public void getPeopleInList_duplicatePersonIds_throwsCommandException() {

        List<Person> personList = new ArrayList<>(getTypicalPersons());
        personList.add(ALICE);
        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withPersonIds(Set.of(ALICE.getUuid())).build();

        assertThrows(CommandException.class, () -> unlinkDescriptor.getPeopleInList(personList));

    }

    @Test
    public void getPropertiesInList_duplicatePropertyIds_throwsCommandException() {

        List<Property> personList = new ArrayList<>(getTypicalProperties());
        personList.add(PROPERTY_ALPHA);
        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())).build();

        assertThrows(CommandException.class, () -> unlinkDescriptor.getPropertiesInList(personList));
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

        // different personId -> returns false
        UnlinkDescriptor editedDescriptor = new UnlinkDescriptorBuilder(UNLINK_DESC_AMY_PROPERTY_ALPHA)
                .withPersonIds(UNLINK_DESC_BOB_PROPERTY_BETA.getPersonIds()).build();
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
                + "{personIds=" + descriptor.getPersonIds()
                + ", propertyIds=" + descriptor.getPropertyIds() + "}";
        assertTrue(descriptor.toString().equals(expectedString));
    }
}
