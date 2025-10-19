package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;

import java.util.Set;

import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.uuid.Uuid;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns a LinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LinkCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LINK_PROPERTY_ID, PREFIX_LINK_RELATIONSHIP,
                PREFIX_LINK_CLIENT_ID);
        if (!argMultimap.arePrefixesPresent(PREFIX_LINK_PROPERTY_ID, PREFIX_LINK_RELATIONSHIP, PREFIX_LINK_CLIENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LINK_RELATIONSHIP);

        String relationship = argMultimap.getValue(PREFIX_LINK_RELATIONSHIP).get();
        if (!relationship.equals("buyer") && !relationship.equals("seller")) {
            throw new ParseException(String.format(MESSAGE_INVALID_RELATIONSHIP, LinkCommand.MESSAGE_USAGE));
        }

        Set<Uuid> personIds = ParserUtil.parsePersonIds(argMultimap.getAllValues(PREFIX_LINK_CLIENT_ID));
        Set<Uuid> propertyIds = ParserUtil.parsePropertyIds(argMultimap.getAllValues(PREFIX_LINK_PROPERTY_ID));

        LinkDescriptor linkDescriptor = new LinkDescriptor();

        linkDescriptor.setRelationship(relationship);
        linkDescriptor.setPersonIds(personIds);
        linkDescriptor.setPropertyIds(propertyIds);

        return new LinkCommand(linkDescriptor);
    }
}
