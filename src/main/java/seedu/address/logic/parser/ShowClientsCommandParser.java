package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import seedu.address.logic.commands.ShowClientsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Parses input arguments and creates a new ShowClientsCommand object.
 */
public class ShowClientsCommandParser implements Parser<ShowClientsCommand> {

    @Override
    public ShowClientsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_ID);

        if (!argMultimap.arePrefixesPresent(PREFIX_PROPERTY_ID) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowClientsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_ID);

        Uuid propertyUuid = ParserUtil.parsePropertyId(argMultimap.getValue(PREFIX_PROPERTY_ID).get());
        return new ShowClientsCommand(propertyUuid);
    }
}
