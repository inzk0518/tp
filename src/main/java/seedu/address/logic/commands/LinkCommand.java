package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_PROPERTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK_RELATIONSHIP;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;

public class LinkCommand extends Command {
    
    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a property to a person. "
            + "Parameters: "
            + "PERSON_INDEX "
            + PREFIX_LINK_PROPERTY + "PROPERTY_INDEX "
            + PREFIX_LINK_RELATIONSHIP + "RELATIONSHIP (must be either 'buyer' or 'seller')"
            + PREFIX_LINK_PROPERTY + "PROPERTY_INDEX"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK_PROPERTY + "2 "
            + PREFIX_LINK_RELATIONSHIP + "buyer";

    private final Person person;
    private final Property property;
    private final String relationship;

    public LinkCommand(Person person, Property property, String relationship) {
        this.person = person;
        this.property = property;
        this.relationship = relationship;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        throw new CommandException("LinkCommand is not yet implemented.");
    }
}
