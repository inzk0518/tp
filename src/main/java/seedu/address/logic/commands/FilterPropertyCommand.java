package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFSET;
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

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.property.predicates.PropertyMatchesFilterPredicate;

/**
 * Filters properties using various optional attributes with pagination.
 */
public class FilterPropertyCommand extends Command {

    public static final String COMMAND_WORD = "filterproperty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all properties in the property book "
            + "based on the given fields. All prefixes are optional.\n"
            + "Parameters: "
            + "[" + PREFIX_PROPERTY_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PROPERTY_POSTAL + "POSTAL] "
            + "[" + PREFIX_PROPERTY_TYPE + "TYPE] "
            + "[" + PREFIX_PROPERTY_BEDROOM + "BEDROOM] "
            + "[" + PREFIX_PROPERTY_BATHROOM + "BATHROOM] "
            + "[" + PREFIX_PROPERTY_FLOOR_AREA + "FLOORAREA] "
            + "[" + PREFIX_PROPERTY_STATUS + "STATUS] "
            + "[" + PREFIX_PROPERTY_PRICE + "PRICE] "
            + "[" + PREFIX_PROPERTY_LISTING + "LISTING] "
            + "[" + PREFIX_PROPERTY_OWNER + "CONTACT_ID] "
            + "[" + PREFIX_LIMIT + "LIMIT] "
            + "[" + PREFIX_OFFSET + "OFFSET]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_POSTAL + "123000 "
            + PREFIX_PROPERTY_BEDROOM + "2 "
            + PREFIX_PROPERTY_BATHROOM + "3 "
            + PREFIX_PROPERTY_PRICE + "500000 "
            + PREFIX_PROPERTY_LISTING + "sale";

    public static final String MESSAGE_INVALID_LIMIT = "Error: Invalid limit";
    public static final String MESSAGE_INVALID_OFFSET = "Error: Invalid offset";

    private final PropertyMatchesFilterPredicate predicate;
    private final int limit;
    private final int offset;

    /**
     * Creates an FilterPropertyCommand to filter {@code Property} with given predicate.
     */
    public FilterPropertyCommand(PropertyMatchesFilterPredicate predicate, int limit, int offset) {
        this.predicate = predicate;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (limit < 1) {
            throw new CommandException(MESSAGE_INVALID_LIMIT);
        }
        if (offset < 0) {
            throw new CommandException(MESSAGE_INVALID_OFFSET);
        }

        List<Property> allMatches = model.getFilteredPropertyList().stream()
                .filter(predicate)
                .toList();

        int total = allMatches.size();
        int start = Math.min(offset, total);
        int endExclusive = Math.min(offset + limit, total);
        List<Property> page = allMatches.subList(start, endExclusive);

        model.updateFilteredPropertyList(page::contains);

        // Build “X properties matched (showing i–j)”
        int from = total == 0 ? 0 : start + 1;
        int to = total == 0 ? 0 : endExclusive;
        String msg = String.format("%d properties matched (showing %d–%d)", total, from, to);

        showPropertiesView();

        return new CommandResult(msg);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FilterPropertyCommand
                && predicate.equals(((FilterPropertyCommand) other).predicate)
                && limit == ((FilterPropertyCommand) other).limit
                && offset == ((FilterPropertyCommand) other).offset);
    }
}
