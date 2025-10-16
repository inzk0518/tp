package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.model.uuid.Uuid;
import seedu.address.testutil.UnlinkDescriptorBuilder;

public class UnlinkCommandParserTest {

    public static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE);

    private UnlinkCommandParser parser = new UnlinkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no property id
        String userInput = " " + PREFIX_LINK_CLIENT_ID + "1";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no client id
        userInput = " " + PREFIX_LINK_PROPERTY_ID + "1 ";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // all parts missing
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // valid input
        String userInput = " " + PREFIX_LINK_PROPERTY_ID + "1 " + PREFIX_LINK_CLIENT_ID + "5";
        assertParseSuccess(parser, userInput, new UnlinkCommand(
                new UnlinkDescriptorBuilder()
                        .withPropertyIds(Set.of(new Uuid(1, PROPERTY)))
                        .withPersonIds(Set.of(new Uuid(5, PERSON)))
                        .build()));

        // valid input with extra spaces
        userInput = " " + PREFIX_LINK_PROPERTY_ID + "2  " + PREFIX_LINK_CLIENT_ID + "3  ";
        assertParseSuccess(parser, userInput, new UnlinkCommand(
                new UnlinkDescriptorBuilder()
                        .withPropertyIds(Set.of(new Uuid(2, PROPERTY)))
                        .withPersonIds(Set.of(new Uuid(3, PERSON)))
                        .build()));

        // valid input with multiple property ids and client ids
        userInput = " " + PREFIX_LINK_PROPERTY_ID + "2 " + PREFIX_LINK_PROPERTY_ID + "3 "
                + PREFIX_LINK_CLIENT_ID + "3 " + PREFIX_LINK_CLIENT_ID + "4";
        assertParseSuccess(parser, userInput, new UnlinkCommand(
                new UnlinkDescriptorBuilder()
                        .withPropertyIds(Set.of(new Uuid(2, PROPERTY), new Uuid(3, PROPERTY)))
                        .withPersonIds(Set.of(new Uuid(3, PERSON), new Uuid(4, PERSON)))
                        .build()));
    }
}
