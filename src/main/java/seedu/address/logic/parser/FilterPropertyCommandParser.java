package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BATHROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_BEDROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_FLOOR_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_LISTING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_TYPE;

import java.util.Optional;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;

/**
 * Parses input arguments and creates a new FilterPropertyCommand object.
 */
public class FilterPropertyCommandParser implements Parser<FilterPropertyCommand> {

    private static final Prefix PREFIX_LIMIT = new Prefix("limit/");
    private static final Prefix PREFIX_OFFSET = new Prefix("offset/");

    /**
     * Parses the given {@code String} of arguments in the context of the FilterPropertyCommand
     * and returns an FilterPropertyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_BEDROOM,
                PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA, PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_OWNER, PREFIX_PROPERTY_LISTING, PREFIX_LIMIT, PREFIX_OFFSET);

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PROPERTY_ADDRESS, PREFIX_PROPERTY_POSTAL, PREFIX_PROPERTY_TYPE, PREFIX_PROPERTY_BEDROOM,
                PREFIX_PROPERTY_BATHROOM, PREFIX_PROPERTY_FLOOR_AREA, PREFIX_PROPERTY_PRICE, PREFIX_PROPERTY_STATUS,
                PREFIX_PROPERTY_OWNER, PREFIX_PROPERTY_LISTING, PREFIX_LIMIT, PREFIX_OFFSET
        );

        PropertyMatchesFilterPredicate.Builder builder = new PropertyMatchesFilterPredicate.Builder();

        Optional<String> maybeAddress = argMultimap.getValue(PREFIX_PROPERTY_ADDRESS);
        if (maybeAddress.isPresent()) {
            String t = maybeAddress.get().trim();
            if (t.isEmpty() || t.length() > 50) {
                throw new ParseException("Error: address too long (max 50 chars)");
            }
            builder.withAddress(t);
        }

        Optional<String> maybePostal = argMultimap.getValue(PREFIX_PROPERTY_POSTAL);
        if (maybePostal.isPresent()) {
            String t = maybePostal.get().trim();
            if (t.length() != 6) {
                throw new ParseException("Error: Invalid postal code (Use a 6-digit postal code)");
            }
            builder.withPostal(t);
        }

        argMultimap.getValue(PREFIX_PROPERTY_TYPE).ifPresent(s -> builder.withType(s.trim()));

        Optional<String> maybeBedroom = argMultimap.getValue(PREFIX_PROPERTY_BEDROOM);
        if (maybeBedroom.isPresent()) {
            String t = maybeBedroom.get().trim();
            if (!t.matches("^(?:[0-9]|1[0-9]|20)$")) {
                throw new ParseException("Error: Invalid bedroom (0–20)");
            }
            builder.withBedroom(t);
        }

        Optional<String> maybeBathroom = argMultimap.getValue(PREFIX_PROPERTY_BATHROOM);
        if (maybeBathroom.isPresent()) {
            String t = maybeBathroom.get().trim();
            if (!t.matches("^(?:[0-9]|1[0-9]|20)$")) {
                throw new ParseException("Error: Invalid bathroom (0–20)");
            }
            builder.withBathroom(t);
        }

        Optional<String> maybeFloorArea = argMultimap.getValue(PREFIX_PROPERTY_FLOOR_AREA);
        if (maybeFloorArea.isPresent()) {
            String t = maybeFloorArea.get().replace(",", "").trim();
            if (!t.matches("^\\d+$")) {
                throw new ParseException("Error: Invalid floor area (digits only)");
            }
            builder.withFloorArea(t);
        }

        Optional<String> maybePrice = argMultimap.getValue(PREFIX_PROPERTY_PRICE);
        if (maybePrice.isPresent()) {
            String t = maybePrice.get().replace(",", "").trim();
            if (!t.matches("^\\d+$")) {
                throw new ParseException("Error: Invalid price (digits only)");
            }
            builder.withPrice(t);
        }

        Optional<String> maybeStatus = argMultimap.getValue(PREFIX_PROPERTY_STATUS);
        if (maybeStatus.isPresent()) {
            String t = maybeStatus.get().trim().toLowerCase();
            if (!(t.equals("unsold") || t.equals("sold"))) {
                throw new ParseException("Error: Invalid status");
            }
            builder.withStatus(t);
        }

        Optional<String> maybeOwner = argMultimap.getValue(PREFIX_PROPERTY_OWNER);
        if (maybeOwner.isPresent()) {
            String t = maybeOwner.get().trim();
            if (t.isEmpty() || t.length() > 50) {
                throw new ParseException("Error: owner value too long");
            }
            builder.withOwner(t);
        }

        Optional<String> maybeListing = argMultimap.getValue(PREFIX_PROPERTY_LISTING);
        if (maybeListing.isPresent()) {
            String t = maybeListing.get().trim().toLowerCase();
            if (!(t.equals("sale") || t.equals("rent"))) {
                throw new ParseException("Error: Invalid listing");
            }
            builder.withListing(t);
        }

        int limit = argMultimap.getValue(PREFIX_LIMIT)
                .map(String::trim)
                .map(Integer::parseInt)
                .orElse(20);
        int offset = argMultimap.getValue(PREFIX_OFFSET)
                .map(String::trim)
                .map(Integer::parseInt)
                .orElse(0);

        if (limit < 1) {
            throw new ParseException(FilterPropertyCommand.MESSAGE_INVALID_LIMIT);
        }
        if (offset < 0) {
            throw new ParseException(FilterPropertyCommand.MESSAGE_INVALID_OFFSET);
        }

        return new FilterPropertyCommand(builder.build(), limit, offset);
    }
}
