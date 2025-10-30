package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Filters properties using optional fields.\n"
            + "Available tags:\n"
            + "address/ postal/ type/ bedroom/ bathroom/ floorarea/ price/ status/ owner/ listing/ limit/ offset/\n"
            + "Example: " + COMMAND_WORD + " postal/123000 bedroom/2 bathroom/3 price/500000 listing/sale";

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

        List<Property> allMatches = model.getFilteredPropertyList().stream()
                .filter(predicate)
                .toList();

        int total = allMatches.size();
        int start = Math.min(offset, total);
        int endExclusive = Math.min(offset + limit, total);
        List<Property> page = allMatches.subList(start, endExclusive);

        model.updateFilteredPropertyList(page::contains);

        // Build “X properties matched”
        String msg = String.format("%d properties matched", Math.min(limit, total - offset));

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
