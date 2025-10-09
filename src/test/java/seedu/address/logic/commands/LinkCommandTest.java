package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RELATIONSHIP;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_AMY_BUYER_PROPERTY_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.LINK_DESC_BOB_SELLER_PROPERTY_BETA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.PROPERTY_BETA;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;
import seedu.address.testutil.LinkDescriptorBuilder;
import seedu.address.testutil.PersonBuilderUtil;
import seedu.address.testutil.PropertyBuilderUtil;
public class LinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

    @Test
    public void constructor_nullLinkDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkCommand(null));
    }

    @Test
    public void execute_validLinkDescriptor_success() {

        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getId()))
                .withPersonIds(Set.of(ALICE.getUuid()))
                .withRelationship("seller")
                .build();

        LinkCommand linkCommand = new LinkCommand(linkDescriptor);

        String expectedMessage = String.format(LinkCommand.MESSAGE_LINK_SELLER_SUCCESS,
                linkDescriptor.getPropertyIds(), linkDescriptor.getPersonIds());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());

        Property updatedPropertyAlpha = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withSellingPersonIds(ALICE.getUuid().value).build();
        expectedModel.setProperty(PROPERTY_ALPHA, updatedPropertyAlpha);

        Person updatedAlice = new PersonBuilderUtil(ALICE)
                .withSellingPropertyIds(updatedPropertyAlpha.getId()).build();
        expectedModel.setPerson(ALICE, updatedAlice);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validLinkDescriptorWithMultiplePropertiesAndPersons_success() {

        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getId(), PROPERTY_BETA.getId()))
                .withPersonIds(Set.of(ALICE.getUuid(), BENSON.getUuid()))
                .withRelationship("buyer")
                .build();

        LinkCommand linkCommand = new LinkCommand(linkDescriptor);

        String expectedMessage = String.format(LinkCommand.MESSAGE_LINK_BUYER_SUCCESS,
                linkDescriptor.getPropertyIds(), linkDescriptor.getPersonIds());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new PropertyBook(model.getPropertyBook()), new UserPrefs());

        Property updatedPropertyAlpha = new PropertyBuilderUtil(PROPERTY_ALPHA)
                .withBuyingPersonIds(ALICE.getUuid().value, BENSON.getUuid().value).build();
        expectedModel.setProperty(PROPERTY_ALPHA, updatedPropertyAlpha);

        Property updatedPropertyBeta = new PropertyBuilderUtil(PROPERTY_BETA)
                .withBuyingPersonIds(ALICE.getUuid().value, BENSON.getUuid().value).build();
        expectedModel.setProperty(PROPERTY_BETA, updatedPropertyBeta);

        Person updatedAlice = new PersonBuilderUtil(ALICE)
                .withBuyingPropertyIds(updatedPropertyAlpha.getId(), updatedPropertyBeta.getId()).build();
        expectedModel.setPerson(ALICE, updatedAlice);

        Person updatedBenson = new PersonBuilderUtil(BENSON)
                .withBuyingPropertyIds(updatedPropertyAlpha.getId(), updatedPropertyBeta.getId()).build();
        expectedModel.setPerson(BENSON, updatedBenson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidRelationshipInLinkDescriptor_success() {

        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getId()))
                .withPersonIds(Set.of(ALICE.getUuid()))
                .withRelationship("owner")
                .build();

        LinkCommand linkCommand = new LinkCommand(linkDescriptor);

        String expectedMessage = MESSAGE_INVALID_RELATIONSHIP;

        assertCommandFailure(linkCommand, model, expectedMessage);
    }

    @Test
    public void equals() {

        LinkDescriptor linkDescriptor = new LinkDescriptor(LINK_DESC_AMY_BUYER_PROPERTY_ALPHA);

        // same value -> returns true
        LinkCommand linkCommand = new LinkCommand(linkDescriptor);
        LinkCommand identicalLinkCommand = new LinkCommand(linkDescriptor);
        assertEquals(linkCommand, identicalLinkCommand);

        // same object -> returns true
        assertEquals(linkCommand, linkCommand);

        // null -> returns false
        assertNotEquals(linkCommand, null);

        // different types -> returns false
        assertNotEquals(linkCommand, new ClearCommand());

        // different descriptor -> returns false
        LinkDescriptor alternateLinkDescriptor = new LinkDescriptor(LINK_DESC_BOB_SELLER_PROPERTY_BETA);
        LinkCommand alternateLinkCommand = new LinkCommand(alternateLinkDescriptor);
        assertNotEquals(linkCommand, alternateLinkCommand);

    }

    @Test
    public void toStringMethod() {
        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder()
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getId(), PROPERTY_BETA.getId()))
                .withPersonIds(Set.of(ALICE.getUuid(), BENSON.getUuid()))
                .withRelationship("seller")
                .build();
        LinkCommand linkCommand = new LinkCommand(linkDescriptor);
        String expected = LinkCommand.class.getCanonicalName() + "{linkDescriptor=" + linkDescriptor + "}";
        assertEquals(linkCommand.toString(), expected);
    }
}
