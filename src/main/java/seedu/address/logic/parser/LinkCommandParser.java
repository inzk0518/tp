package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.commands.LinkCommand.LinkDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

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
        if (!arePrefixesPresent(argMultimap, PREFIX_LINK_PROPERTY_ID, PREFIX_LINK_RELATIONSHIP, PREFIX_LINK_CLIENT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LINK_PROPERTY_ID, PREFIX_LINK_RELATIONSHIP,
                PREFIX_LINK_CLIENT_ID);

        String relationship = argMultimap.getValue(PREFIX_LINK_RELATIONSHIP).get();
        if (!relationship.equals("buyer") && !relationship.equals("seller")) {
            throw new ParseException(String.format(MESSAGE_INVALID_RELATIONSHIP, LinkCommand.MESSAGE_USAGE));
        }

        Index personId = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LINK_CLIENT_ID).get());
        Index propertyId = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LINK_PROPERTY_ID).get());

        LinkDescriptor linkDescriptor = new LinkDescriptor();

        linkDescriptor.setRelationship(relationship);
        linkDescriptor.setPersonId(personId);
        linkDescriptor.setPropertyId(propertyId);

        return new LinkCommand(linkDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
