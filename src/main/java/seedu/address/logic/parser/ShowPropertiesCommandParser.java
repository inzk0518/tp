package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;

import seedu.address.logic.commands.ShowPropertiesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Parses input arguments and creates a new ShowPropertiesCommand object
 */
public class ShowPropertiesCommandParser implements Parser<ShowPropertiesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowPropertiesCommand
     * and returns a ShowPropertiesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowPropertiesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_ID);

        if (!argMultimap.arePrefixesPresent(PREFIX_CLIENT_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowPropertiesCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_ID);

        Uuid clientUuid = ParserUtil.parsePersonId(argMultimap.getValue(PREFIX_CLIENT_ID).get());

        return new ShowPropertiesCommand(clientUuid);
    }
}
