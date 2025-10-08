package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkCommand;
import seedu.address.model.person.Uuid;
import seedu.address.testutil.LinkDescriptorBuilder;

public class LinkCommandParserTest {

    public static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE);

    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no property id
        String userInput = PREFIX_LINK_RELATIONSHIP + "buyer " + PREFIX_LINK_CLIENT_ID + "1";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no relationship
        userInput = PREFIX_LINK_PROPERTY_ID + "1 " + PREFIX_LINK_CLIENT_ID + "1";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no client id
        userInput = PREFIX_LINK_PROPERTY_ID + "1 " + PREFIX_LINK_RELATIONSHIP + "buyer";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // all parts missing
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_multipleRelationships_failure() {
        String userInput = PREFIX_LINK_PROPERTY_ID + "1 " + PREFIX_LINK_RELATIONSHIP + "buyer "
                + PREFIX_LINK_RELATIONSHIP + "seller " + PREFIX_LINK_CLIENT_ID + "1";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // valid input
        String userInput = " " + PREFIX_LINK_PROPERTY_ID + "1 " + PREFIX_LINK_RELATIONSHIP + "buyer "
                + PREFIX_LINK_CLIENT_ID + "5";
        assertParseSuccess(parser, userInput, new LinkCommand(
                new LinkDescriptorBuilder()
                        .withPropertyIds(Set.of("1"))
                        .withRelationship("buyer")
                        .withPersonIds(Set.of(new Uuid(5)))
                        .build()));

        // valid input with extra spaces
        userInput = " " + PREFIX_LINK_PROPERTY_ID + "2  " + PREFIX_LINK_RELATIONSHIP + "seller  "
                + PREFIX_LINK_CLIENT_ID + "3  ";
        assertParseSuccess(parser, userInput, new LinkCommand(
                new LinkDescriptorBuilder()
                        .withPropertyIds(Set.of("2"))
                        .withRelationship("seller")
                        .withPersonIds(Set.of(new Uuid(3)))
                        .build()));

        // valid input with multiple property ids and client ids
        userInput = " " + PREFIX_LINK_PROPERTY_ID + "2 " + PREFIX_LINK_PROPERTY_ID + "3 "
                + PREFIX_LINK_RELATIONSHIP + "seller  "
                + PREFIX_LINK_CLIENT_ID + "3 " + PREFIX_LINK_CLIENT_ID + "4";
        assertParseSuccess(parser, userInput, new LinkCommand(
                new LinkDescriptorBuilder()
                        .withPropertyIds(Set.of("2", "3"))
                        .withRelationship("seller")
                        .withPersonIds(Set.of(new Uuid(3), new Uuid(4)))
                        .build()));
    }

}
