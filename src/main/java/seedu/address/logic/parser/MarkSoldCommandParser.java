package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.MarkSoldCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Parses input arguments and creates a new {@code MarkSoldCommand}.
 */
public class MarkSoldCommandParser implements Parser<MarkSoldCommand> {

    @Override
    public MarkSoldCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_ID);
        List<String> idList = argMultimap.getAllValues(PREFIX_PROPERTY_ID);

        if (idList.isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
        }

        // Check for empty UUID value e.g. command: sold p/
        for (String id : idList) {
            if (id.trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSoldCommand.MESSAGE_USAGE));
            }
        }

        // Check for duplicates explicitly and throw custom error on the first found
        Set<String> seen = new HashSet<>();
        for (String id : idList) {
            if (!seen.add(id)) {
                throw new ParseException("Duplicate property ID detected: " + id);
            }
        }

        Set<Uuid> uuids = Set.copyOf((ParserUtil.parsePropertyIds(idList)));

        return new MarkSoldCommand(uuids);
    }
}
