package seedu.address.logic.parser;

import seedu.address.logic.commands.ShowContactsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse the input for ShowContactsCommand
 */
public class ShowContactsCommandParser implements Parser<ShowContactsCommand> {

    @Override
    public ShowContactsCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException("Property ID cannot be empty");
        }

        // Check if input starts with "p/"
        if (!trimmedArgs.startsWith("p/")) {
            throw new ParseException("Invalid format. Use the following: showcontacts p/PROPERTY_ID");
        }

        // Extract everything after "p/"
        String propertyId = trimmedArgs.substring(2).trim();

        if (propertyId.isEmpty()) {
            throw new ParseException("Property ID cannot be empty");
        }

        return new ShowContactsCommand(propertyId);
    }
}
