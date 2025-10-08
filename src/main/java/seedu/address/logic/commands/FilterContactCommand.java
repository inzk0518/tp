package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.PersonMatchesFilterPredicate;

/**
 * Filters contacts based on various optional fields.
 * Example:
 * filtercontact n/tan s/active min/800000 max/1200000 limit/10
 */
public class FilterContactCommand extends Command {

    public static final String COMMAND_WORD = "filtercontact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Filters contacts using optional fields.\n"
            + "Available tags:\n"
            + "n/NAME p/PHONE e/EMAIL a/ADDRESS t/TAG s/STATUS role/ROLE\n"
            + "min/AMOUNT max/AMOUNT notes/TEXT limit/N offset/N\n"
            + "Example: " + COMMAND_WORD + " t/buyer s/active min/800000 max/1200000";

    public static final String MESSAGE_INVALID_INPUT = "Error: Invalid filter value(s).";
    public static final String MESSAGE_SUCCESS = "%1$d contacts matched (showing %2$d–%3$d)";

    private final PersonMatchesFilterPredicate predicate;
    private final int limit;
    private final int offset;

    public FilterContactCommand(PersonMatchesFilterPredicate predicate, int limit, int offset) {
        this.predicate = predicate;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> allPersons = model.getFilteredPersonList()
                .stream()
                .filter(predicate)
                .toList();

        int total = allPersons.size();
        int start = Math.min(total, offset);
        int end = Math.min(total, offset + limit);
        List<Person> shown = allPersons.subList(start, end);

        // show only sublist in UI
        model.updateFilteredPersonList(shown::contains);

        return new CommandResult(String.format(MESSAGE_SUCCESS, total, start + 1, end));
    }
}