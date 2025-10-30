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
        String input = " type/condo bedroom/3 floorarea/100 status/available limit/5 offset/10";
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
        String input = " owner/alice";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withOwner("alice").build(),
                        Integer.MAX_VALUE, 0);
        assertEquals(expected, parser.parse(input));
    }
    @Test
    public void parseValidArgsSuccessThird() throws Exception {
        String input = " address/Geylang 18 postal/123000 bathroom/3 price/5000 listing/rent";
        FilterPropertyCommand expected =
                new FilterPropertyCommand(
                        new PropertyMatchesFilterPredicate.Builder()
                                .withAddress("Geylang 18").withPostal("123000").withBathroom("3")
                                .withPrice("5000").withListing("rent").build(),
                        Integer.MAX_VALUE, 0); // defaults
        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parseInvalidTagThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" owner/"));
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
        assertThrows(ParseException.class, () -> parser.parse(" bedroom/22"));
    }

    @Test
    public void parseInvalidBathroomThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" bathroom/23"));
    }

    @Test
    public void parseInvalidFloorAreaThrowsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" floorarea/23sqrft"));
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
        assertThrows(ParseException.class, () -> parser.parse(" listing/free"));
    }
}

