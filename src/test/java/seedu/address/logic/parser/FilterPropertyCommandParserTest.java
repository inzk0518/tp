package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;

import static org.junit.jupiter.api.Assertions.*;

public class FilterPropertyCommandParserTest {
    private final FilterPropertyCommandParser parser = new FilterPropertyCommandParser();

    @Test
    public void parse_validArgs_success_first() throws Exception {
        String input = " type/condo bedroom/3 status/listed limit/5 offset/10";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withType("condo").withBedroom("3").withStatus("listed").build(),
                        5, 10);
        assertEquals(expected, parser.parse(input));

    }

    @Test
    public void parse_validArgs_success_second() throws Exception {
        String input = " owner/alice";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withOwner("alice").build(),
                        20, 0); // defaults
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parse_invalidTag_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" owner/"));
    }

    @Test
    public void parse_duplicateTag_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" type/condo type/hdb"));
    }
}

