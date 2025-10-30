package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ShowContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Parses input arguments and creates a new ShowContactsCommand object.
 */
public class ShowContactsCommandParser implements Parser<ShowContactsCommand> {

    @Override
    public ShowContactsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowContactsCommand.MESSAGE_USAGE));
        }

        Uuid propertyUuid = ParserUtil.parsePropertyId(trimmedArgs);
        return new ShowContactsCommand(propertyUuid);
    }
}
