package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;


public class FilterPropertyCommandParserTest {
    private final FilterPropertyCommandParser parser = new FilterPropertyCommandParser();

    @Test
    public void parseValidArgsSuccessFirst() throws Exception {
        String input = " type/condo bed/3 f/100 status/available limit/5 offset/10";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withType("condo").withBedroom("3")
                                .withFloorArea("100").withStatus("available").build(),
                        5, 10);
        assertEquals(expected, parser.parse(input));

    }

    @Test
    public void parseValidArgsSuccessSecond() throws Exception {
        String input = " o/alice";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withOwner("alice").build(),
                        20, 0);
        assertEquals(expected, parser.parse(input));
    }
    @Test
    public void parseValidArgsSuccessThird() throws Exception {
        String input = " a/Geylang 18 postal/123000 bath/3 price/5000 l/rent";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withAddress("Geylang 18").withPostal("123000").withBathroom("3")
                                .withPrice("5000").withListing("rent").build(),
                        20, 0); // defaults
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parseInvalidTagThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" o/"));
    }

    @Test
    public void parseInvalidPostalThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" postal/1234567"));
    }
    @Test
    public void parseDuplicateTagThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" type/condo type/hdb"));
    }

    @Test
    public void parseInvalidBedroomThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" bed/22"));
    }

    @Test
    public void parseInvalidBathroomThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" bath/23"));
    }

    @Test
    public void parseInvalidFloorAreaThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" f/23sqrft"));
    }

    @Test
    public void parseInvalidPriceThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" price/5000dollar"));
    }

    @Test
    public void parseInvalidStatusThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" status/listed"));
    }

    @Test
    public void parseInvalidListingThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" l/free"));
    }
}

