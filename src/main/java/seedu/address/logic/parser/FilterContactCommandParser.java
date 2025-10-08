package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.*;

import java.math.BigDecimal;
import java.util.Optional;

import seedu.address.logic.commands.FilterContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.PersonMatchesFilterPredicate;

/**
 * Parses input arguments and creates a new FilterContactCommand.
 */
public class FilterContactCommandParser implements Parser<FilterContactCommand> {

    @Override
    public FilterContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_STATUS, PREFIX_BUDGET_MIN, PREFIX_BUDGET_MAX, PREFIX_NOTES);

        PersonMatchesFilterPredicate.Builder builder = new PersonMatchesFilterPredicate.Builder();

        argMultimap.getValue(PREFIX_NAME).ifPresent(builder::withName);
        argMultimap.getValue(PREFIX_PHONE).ifPresent(builder::withPhone);
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(builder::withEmail);
        argMultimap.getValue(PREFIX_ADDRESS).ifPresent(builder::withAddress);
        argMultimap.getValue(PREFIX_TAG).ifPresent(builder::withTag);
        argMultimap.getValue(PREFIX_STATUS).ifPresent(builder::withStatus);
        argMultimap.getValue(PREFIX_NOTES).ifPresent(builder::withNotes);
        argMultimap.getValue(PREFIX_BUDGET_MIN).ifPresent(builder::withBudgetMin);
        argMultimap.getValue(PREFIX_BUDGET_MAX).ifPresent(builder::withBudgetMax);

        // Optional pagination
        int limit = 20;
        int offset = 0;
        try {
            if (args.contains("limit/")) {
                limit = Integer.parseInt(args.split("limit/")[1].split("\\s")[0]);
                if (limit < 1) throw new NumberFormatException();
            }
            if (args.contains("offset/")) {
                offset = Integer.parseInt(args.split("offset/")[1].split("\\s")[0]);
                if (offset < 0) throw new NumberFormatException();
            }
        } catch (Exception e) {
            throw new ParseException("Invalid limit/offset value");
        }

        return new FilterContactCommand(builder.build(), limit, offset);
    }
}

