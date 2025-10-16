package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ShowPropertiesCommand;
import seedu.address.model.person.Uuid;

/**
 * Contains unit tests for {@code ShowPropertiesCommandParser}.
 */
public class ShowPropertiesCommandParserTest {

    private final ShowPropertiesCommandParser parser = new ShowPropertiesCommandParser();

    @Test
    public void parse_validSingleDigitUuid_success() {
        String userInput = " c/1";
        ShowPropertiesCommand expectedCommand = new ShowPropertiesCommand(new Uuid(1));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleDigitUuid_success() {
        String userInput = " c/123";
        ShowPropertiesCommand expectedCommand = new ShowPropertiesCommand(new Uuid(123));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validLargeUuid_success() {
        String userInput = " c/999999";
        ShowPropertiesCommand expectedCommand = new ShowPropertiesCommand(new Uuid(999999));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validWithExtraSpaces_success() {
        String userInput = "   c/123   ";
        ShowPropertiesCommand expectedCommand = new ShowPropertiesCommand(new Uuid(123));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validWithLeadingZeros_success() {
        String userInput = " c/00123";
        ShowPropertiesCommand expectedCommand = new ShowPropertiesCommand(new Uuid(123));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String userInput = " 123";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_failure() {
        String userInput = "";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyWhitespace_failure() {
        String userInput = "   ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyPrefixNoValue_failure() {
        String userInput = " c/";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_onlyPrefixWithSpaces_failure() {
        String userInput = " c/   ";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonNumericUuid_failure() {
        String userInput = " c/abc";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_negativeUuid_failure() {
        String userInput = " c/-123";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_uuidWithSpecialCharacters_failure() {
        String userInput = " c/12@34";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_uuidWithSpaces_failure() {
        String userInput = " c/12 34";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_decimalUuid_failure() {
        String userInput = " c/12.34";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_uuidWithLetters_failure() {
        String userInput = " c/12a34";
        assertParseFailure(parser, userInput, Uuid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefix_failure() {
        String userInput = " c/123 c/456";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLIENT));
    }

    @Test
    public void parse_multipleDuplicatePrefix_failure() {
        String userInput = " c/1 c/2 c/3";
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLIENT));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String userInput = "extra c/123";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_numberInPreamble_failure() {
        String userInput = "999 c/123";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongPrefix_failure() {
        String userInput = " p/123";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
    }
}
