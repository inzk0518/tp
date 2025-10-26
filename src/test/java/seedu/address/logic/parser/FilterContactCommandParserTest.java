package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.FilterContactPredicate;

public class FilterContactCommandParserTest {

    private final FilterContactCommandParser parser = new FilterContactCommandParser();

    @Test
    public void parse_validArgs_returnsFilterContactCommand() {
        FilterContactCommand expectedCommand =
                new FilterContactCommand(new FilterContactPredicate(
                        Optional.of(Arrays.asList("Alice", "Bob")),
                        Optional.of(Arrays.asList("12345678")),
                        Optional.of(Arrays.asList("alice@example.com")),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(), Optional.empty()
                ));

        assertParseSuccess(parser, " n/Alice Bob p/12345678 e/alice@example.com", expectedCommand);

        // Test with multiple prefixes and whitespace
        assertParseSuccess(parser, " \n n/Alice Bob \n p/12345678 \t e/alice@example.com \t", expectedCommand);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " abc/foo",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "n/Bob abc/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraPreamble_throwsParseException() {
        assertParseFailure(parser, "extra n/Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "randomtext",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parsePositiveInteger_validValues_success() throws Exception {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        Optional<Integer> result = parser.parsePositiveInteger(Optional.of("5"));
        assertTrue(result.isPresent());
        assertEquals(5, result.get());
    }

    @Test
    public void parsePositiveInteger_invalidValues_throwsParseException() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        assertThrows(ParseException.class, () -> parser.parsePositiveInteger(Optional.of("0")));
        assertThrows(ParseException.class, () -> parser.parsePositiveInteger(Optional.of("-2")));
        assertThrows(ParseException.class, () -> parser.parsePositiveInteger(Optional.of("abc")));
    }

    @Test
    public void parseNonNegativeInteger_validValues_success() throws Exception {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        Optional<Integer> zero = parser.parseNonNegativeInteger(Optional.of("0"));
        Optional<Integer> positive = parser.parseNonNegativeInteger(Optional.of("10"));

        assertTrue(zero.isPresent());
        assertEquals(0, zero.get());
        assertTrue(positive.isPresent());
        assertEquals(10, positive.get());
    }

    @Test
    public void parseNonNegativeInteger_invalidValues_throwsParseException() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        assertThrows(ParseException.class, () -> parser.parseNonNegativeInteger(Optional.of("-1")));
        assertThrows(ParseException.class, () -> parser.parseNonNegativeInteger(Optional.of("abc")));
    }

    @Test
    public void parse_withValidLimitAndOffset_success() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Alice")),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.of(5), Optional.of(10)
        );

        FilterContactCommand expectedCommand = new FilterContactCommand(predicate);
        assertParseSuccess(parser, " n/Alice limit/5 offset/10", expectedCommand);
    }

    @Test
    public void parse_withInvalidLimitOrOffset_throwsParseException() {
        FilterContactCommandParser parser = new FilterContactCommandParser();

        // invalid limit
        assertParseFailure(parser, " limit/0", "Limit must be greater than 0.");
        assertParseFailure(parser, " limit/-1", "Limit must be greater than 0.");
        assertParseFailure(parser, " limit/abc", "Invalid number for limit: Optional[abc]");

        // invalid offset
        assertParseFailure(parser, " offset/-5", "Offset cannot be negative.");
        assertParseFailure(parser, " offset/xyz", "Invalid number for offset: Optional[xyz]");
    }

    @Test
    public void parse_valuesContainingPrefixLikeStrings_failure() {
        String prefixLikeValue = "abc/like";

        // Phone containing prefix-like value
        assertParseFailure(parser,
                " n/Alice p/" + prefixLikeValue,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        // Email containing prefix-like value
        assertParseFailure(parser,
                " n/Alice p/12345678 e/" + prefixLikeValue,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        // Address containing prefix-like value
        assertParseFailure(parser,
                " n/Alice a/" + prefixLikeValue,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));

        // Tag containing prefix-like value
        assertParseFailure(parser,
                " n/Alice t/" + prefixLikeValue,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_notesContainingPrefixLikeStrings_success() throws Exception {
        String input = " n/Alice p/12345678 " + PREFIX_NOTES + " some/note/with/slashes";

        FilterContactPredicate predicate = new FilterContactPredicate(
                Optional.of(Arrays.asList("Alice")),
                Optional.of(Arrays.asList("12345678")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(Arrays.asList("some/note/with/slashes")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        FilterContactCommand expectedCommand = new FilterContactCommand(predicate);

        assertParseSuccess(parser, input, expectedCommand);
    }

}
