package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.util.Set;

import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.logic.commands.UnlinkCommand.UnlinkDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Parsers input arguments and creates a new UnlinkCommand object
 */
public class UnlinkCommandParser implements Parser<UnlinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnlinkCommand
     * and returns a UnlinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnlinkCommand parse(String args) throws ParseException {

        var argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_ID, PREFIX_CLIENT_ID);
        if (!argMultimap.arePrefixesPresent(PREFIX_PROPERTY_ID, PREFIX_CLIENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE));
        }

        Set<Uuid> personIds = ParserUtil.parsePersonIds(argMultimap.getAllValues(PREFIX_CLIENT_ID));
        Set<Uuid> propertyIds = ParserUtil.parsePropertyIds(argMultimap.getAllValues(PREFIX_PROPERTY_ID));

        UnlinkDescriptor unlinkDescriptor = new UnlinkDescriptor();

        unlinkDescriptor.setPersonIds(personIds);
        unlinkDescriptor.setPropertyIds(propertyIds);

        return new UnlinkCommand(unlinkDescriptor);
    }
}
