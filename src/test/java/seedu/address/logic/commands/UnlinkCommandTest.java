package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_AMY_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.UNLINK_DESC_BOB_PROPERTY_BETA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnlinkCommand.UnlinkDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.property.Property;
import seedu.address.testutil.ContactBuilderUtil;
import seedu.address.testutil.PropertyBuilderUtil;
import seedu.address.testutil.UnlinkDescriptorBuilder;

public class UnlinkCommandTest {

    private Model model;

    @BeforeEach
    public void initialiseModel() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

        Property linkedPropertyAlpha = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withSellingContactIds(ALICE.getUuid().getValue())
                .withBuyingContactIds(BENSON.getUuid().getValue())
                .build();

        Property linkedPropertyBeta = new PropertyBuilderUtil(PROPERTY_BETA)
                .withSellingContactIds(BENSON.getUuid().getValue())
                .withBuyingContactIds(ALICE.getUuid().getValue())
                .build();

        Contact linkedAlice = new ContactBuilderUtil(ALICE)
                .withSellingPropertyIds(linkedPropertyAlpha.getUuid())
                .withBuyingPropertyIds(linkedPropertyBeta.getUuid())
                .build();

        Contact linkedBenson = new ContactBuilderUtil(BENSON)
                .withSellingPropertyIds(linkedPropertyBeta.getUuid())
                .withBuyingPropertyIds(linkedPropertyAlpha.getUuid())
                .build();

        model.setProperty(PROPERTY_ALPHA, linkedPropertyAlpha);
        model.setProperty(PROPERTY_BETA, linkedPropertyBeta);
        model.setContact(ALICE, linkedAlice);
        model.setContact(BENSON, linkedBenson);
    }

    @Test
    public void constructor_nullLinkDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkCommand(null));
    }

    @Test
    public void execute_validUnlinkDescriptor_success() {

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid()))
                .withContactIds(Set.of(ALICE.getUuid()))
                .build();

        UnlinkCommand unlinkCommand = new UnlinkCommand(unlinkDescriptor);

        String expectedMessage = String.format(UnlinkCommand.MESSAGE_UNLINK_SUCCESS,
                unlinkDescriptor.getPropertyIds(), unlinkDescriptor.getContactIds());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());

        Property expectedPropertyAlpha = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withBuyingContactIds(BENSON.getUuid().getValue())
                .build();

        Property expectedPropertyBeta = new PropertyBuilderUtil(PROPERTY_BETA)
                .withSellingContactIds(BENSON.getUuid().getValue())
                .withBuyingContactIds(ALICE.getUuid().getValue())
                .build();

        Contact expectedAlice = new ContactBuilderUtil(ALICE)
                .withBuyingPropertyIds(expectedPropertyBeta.getUuid())
                .build();

        Contact expectedBenson = new ContactBuilderUtil(BENSON)
                .withSellingPropertyIds(expectedPropertyBeta.getUuid())
                .withBuyingPropertyIds(expectedPropertyAlpha.getUuid())
                .build();

        expectedModel.setProperty(PROPERTY_ALPHA, expectedPropertyAlpha);
        expectedModel.setProperty(PROPERTY_BETA, expectedPropertyBeta);
        expectedModel.setContact(ALICE, expectedAlice);
        expectedModel.setContact(BENSON, expectedBenson);

        assertCommandSuccess(unlinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validUnlinkDescriptorWithMultipleContacts_success() {

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid()))
                .withContactIds(Set.of(ALICE.getUuid(), BENSON.getUuid()))
                .build();

        UnlinkCommand unlinkCommand = new UnlinkCommand(unlinkDescriptor);

        String expectedMessage = String.format(UnlinkCommand.MESSAGE_UNLINK_SUCCESS,
                unlinkDescriptor.getPropertyIds(), unlinkDescriptor.getContactIds());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());

        Property expectedPropertyBeta = new PropertyBuilderUtil(PROPERTY_BETA)
                .withSellingContactIds(BENSON.getUuid().getValue())
                .withBuyingContactIds(ALICE.getUuid().getValue())
                .build();

        Contact expectedAlice = new ContactBuilderUtil(ALICE)
                .withBuyingPropertyIds(expectedPropertyBeta.getUuid())
                .build();

        Contact expectedBenson = new ContactBuilderUtil(BENSON)
                .withSellingPropertyIds(expectedPropertyBeta.getUuid())
                .build();

        expectedModel.setProperty(PROPERTY_BETA, expectedPropertyBeta);
        expectedModel.setContact(ALICE, expectedAlice);
        expectedModel.setContact(BENSON, expectedBenson);

        assertCommandSuccess(unlinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validUnlinkDescriptorWithMultiplePropertiesAndContacts_success() {

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid(), PROPERTY_BETA.getUuid()))
                .withContactIds(Set.of(ALICE.getUuid(), BENSON.getUuid()))
                .build();

        UnlinkCommand unlinkCommand = new UnlinkCommand(unlinkDescriptor);

        String expectedMessage = String.format(UnlinkCommand.MESSAGE_UNLINK_SUCCESS,
                unlinkDescriptor.getPropertyIds(), unlinkDescriptor.getContactIds());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

        assertCommandSuccess(unlinkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptor(UNLINK_DESC_AMY_PROPERTY_ALPHA);

        // same value -> returns true
        UnlinkCommand unlinkCommand = new UnlinkCommand(unlinkDescriptor);
        UnlinkCommand identicalUnlinkCommand = new UnlinkCommand(unlinkDescriptor);
        assertEquals(unlinkCommand, identicalUnlinkCommand);

        // same object -> returns true
        assertEquals(unlinkCommand, unlinkCommand);

        // null -> returns false
        assertNotEquals(unlinkCommand, null);

        // different types -> returns false
        assertNotEquals(unlinkCommand, new ClearCommand());

        // different descriptor -> returns false
        UnlinkDescriptor alternateUnlinkDescriptor = new UnlinkDescriptor(UNLINK_DESC_BOB_PROPERTY_BETA);
        UnlinkCommand alternateUnlinkCommand = new UnlinkCommand(alternateUnlinkDescriptor);
        assertNotEquals(unlinkCommand, alternateUnlinkCommand);

    }

    @Test
    public void toStringMethod() {
        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid(), PROPERTY_BETA.getUuid()))
                .withContactIds(Set.of(ALICE.getUuid(), BENSON.getUuid()))
                .build();
        UnlinkCommand unlinkCommand = new UnlinkCommand(unlinkDescriptor);
        String expected = UnlinkCommand.class.getCanonicalName() + "{unlinkDescriptor=" + unlinkDescriptor + "}";
        assertEquals(unlinkCommand.toString(), expected);
    }
}
