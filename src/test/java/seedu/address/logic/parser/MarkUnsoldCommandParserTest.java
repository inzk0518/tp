package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MarkUnsoldCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Contains unit tests for {@code MarkUnsoldCommandParser}.
 */
public class MarkUnsoldCommandParserTest {

    private final MarkUnsoldCommandParser parser = new MarkUnsoldCommandParser();

    @Test
    public void parse_validSingleId_success() {
        String userInput = " " + PREFIX_PROPERTY_ID + "1";
        Set<Uuid> expectedIds = Set.of(new Uuid(1, PROPERTY));
        MarkUnsoldCommand expectedCommand = new MarkUnsoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleIds_success() {
        String userInput = " " + PREFIX_PROPERTY_ID + "100 " + PREFIX_PROPERTY_ID + "200";
        Set<Uuid> expectedIds = Set.of(new Uuid(100, PROPERTY), new Uuid(200, PROPERTY));
        MarkUnsoldCommand expectedCommand = new MarkUnsoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String userInput = " 100 200";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnsoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnsoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyPrefixNoValue_failure() {
        String userInput = " " + PREFIX_PROPERTY_ID + " ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnsoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withExtraSpaces_success() {
        String userInput = "   " + PREFIX_PROPERTY_ID + "55   " + PREFIX_PROPERTY_ID + "77   ";
        Set<Uuid> expectedIds = Set.of(new Uuid(55, PROPERTY), new Uuid(77, PROPERTY));
        MarkUnsoldCommand expectedCommand = new MarkUnsoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateIds_failure() {
        String input = " "
                + PREFIX_PROPERTY_ID + "aaa "
                + PREFIX_PROPERTY_ID + "bbb "
                + PREFIX_PROPERTY_ID + "aaa"; // duplicate aaa
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals("Duplicate property ID detected: aaa", exception.getMessage());
    }
}
