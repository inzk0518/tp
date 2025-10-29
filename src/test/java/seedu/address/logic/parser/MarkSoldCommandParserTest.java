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

import seedu.address.logic.commands.MarkSoldCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Contains unit tests for {@code MarkSoldCommandParser}.
 */
public class MarkSoldCommandParserTest {

    private final MarkSoldCommandParser parser = new MarkSoldCommandParser();

    @Test
    public void parse_validSingleId_success() {
        String userInput = " " + PREFIX_PROPERTY_ID + "123";
        Set<Uuid> expectedIds = Set.of(new Uuid(123, PROPERTY));
        MarkSoldCommand expectedCommand = new MarkSoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleIds_success() {
        String userInput = " " + PREFIX_PROPERTY_ID + "10 " + PREFIX_PROPERTY_ID + "20 " + PREFIX_PROPERTY_ID + "30";
        Set<Uuid> expectedIds = Set.of(new Uuid(10, PROPERTY), new Uuid(20, PROPERTY), new Uuid(30, PROPERTY));
        MarkSoldCommand expectedCommand = new MarkSoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String userInput = " 10 20";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyPrefixNoValue_failure() {
        String userInput = " " + PREFIX_PROPERTY_ID + " ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_withExtraSpaces_success() {
        String userInput = "   " + PREFIX_PROPERTY_ID + "5    " + PREFIX_PROPERTY_ID + "42   ";
        Set<Uuid> expectedIds = Set.of(new Uuid(5, PROPERTY), new Uuid(42, PROPERTY));
        MarkSoldCommand expectedCommand = new MarkSoldCommand(expectedIds);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateIds_failure() {
        String input = " "
                + PREFIX_PROPERTY_ID + "111 "
                + PREFIX_PROPERTY_ID + "222 "
                + PREFIX_PROPERTY_ID + "111"; // duplicate 111
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals("Duplicate property ID detected: 111", exception.getMessage());
    }
}
