package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperties.PROPERTY_ALPHA;
import static seedu.address.testutil.TypicalProperties.getTypicalPropertyBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.logic.commands.UnlinkCommand.UnlinkDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.LinkDescriptorBuilder;
import seedu.address.testutil.UnlinkDescriptorBuilder;

public class LinkUnlinkIntegrationTest {

    @Test
    public void linkAndUnlink_returnTypicalModel_success() throws CommandException {

        Model model = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

        LinkDescriptor linkDescriptor = new LinkDescriptorBuilder().withContactIds(Set.of(ALICE.getUuid()))
                .withRelationship("buyer").withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())).build();
        LinkCommand linkCommand = new LinkCommand(linkDescriptor);

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptorBuilder().withContactIds(Set.of(ALICE.getUuid()))
                .withPropertyIds(Set.of(PROPERTY_ALPHA.getUuid())).build();
        UnlinkCommand unlinkCommand = new UnlinkCommand(unlinkDescriptor);
        LinkUnlinkCommandUtil linkUnlink = new LinkUnlinkCommandUtil(linkCommand, unlinkCommand);

        String expectedMessage =
                String.format(LinkCommand.MESSAGE_LINK_BUYER_SUCCESS + "\n" + UnlinkCommand.MESSAGE_UNLINK_SUCCESS,
                Uuid.getGuiSetDisplayAsString(linkDescriptor.getPropertyIds()),
                Uuid.getGuiSetDisplayAsString(linkDescriptor.getContactIds()),
                Uuid.getGuiSetDisplayAsString(unlinkDescriptor.getPropertyIds()),
                Uuid.getGuiSetDisplayAsString(unlinkDescriptor.getContactIds()));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalPropertyBook(), new UserPrefs());

        assertCommandSuccess(linkUnlink, model, expectedMessage, expectedModel);
    }
}
