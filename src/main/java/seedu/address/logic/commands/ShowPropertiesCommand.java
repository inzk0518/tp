package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Uuid;
import seedu.address.model.property.Property;

/**
 * Finds and lists all properties associated with a specified client.
 * Shows properties where the client is the owner, buyer, or seller.
 */
public class ShowPropertiesCommand extends Command {

    public static final String COMMAND_WORD = "showproperties";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all properties associated with the specified client.\n"
            + "Parameters: c/CLIENT_UUID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " c/123";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with UUID %s not found.";
    public static final String MESSAGE_NO_PROPERTIES = "Client UUID %s has no associated properties.";

    private final Uuid clientUuid;

    /**
     * Creates a ShowPropertiesCommand to find all properties associated with the specified client.
     *
     * @param clientUuid The UUID of the client to search for.
     */
    public ShowPropertiesCommand(Uuid clientUuid) {
        requireNonNull(clientUuid);
        this.clientUuid = clientUuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the target person from the person list
        String clientUuidString = clientUuid.toString();
        Person targetPerson = null;

        for (Person person : model.getFilteredPersonList()) {
            if (person.getUuid().toString().equals(clientUuidString)) {
                targetPerson = person;
                break;
            }
        }

        if (targetPerson == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, clientUuid));
        }

        // Get all properties
        List<Property> allProperties = model.getFilteredPropertyList();

        // Find properties where client is OWNER
        List<Property> ownerProperties = new ArrayList<>();
        for (Property property : allProperties) {
            if (property.getOwner().value.equals(clientUuidString)) {
                ownerProperties.add(property);
            }
        }

        // Find properties where client is BUYER
        Set<String> buyingPropertyIds = targetPerson.getBuyingPropertyIds();
        List<Property> buyerProperties = new ArrayList<>();
        for (Property property : allProperties) {
            if (buyingPropertyIds.contains(property.getId())) {
                buyerProperties.add(property);
            }
        }

        // Find properties where client is SELLER
        Set<String> sellingPropertyIds = targetPerson.getSellingPropertyIds();
        List<Property> sellerProperties = new ArrayList<>();
        for (Property property : allProperties) {
            if (sellingPropertyIds.contains(property.getId())) {
                sellerProperties.add(property);
            }
        }

        // Check if client has any properties
        int totalProperties = ownerProperties.size() + buyerProperties.size() + sellerProperties.size();
        if (totalProperties == 0) {
            throw new CommandException(String.format(MESSAGE_NO_PROPERTIES, clientUuidString));
        }

        // Build formatted output
        String formattedOutput = buildFormattedOutput(
                targetPerson, clientUuidString, ownerProperties, buyerProperties, sellerProperties, totalProperties);

        return new CommandResult(formattedOutput);
    }

    /**
     * Builds the formatted output string showing all properties grouped by relationship type.
     */
    private String buildFormattedOutput(Person targetPerson, String clientUuidString,
                                        List<Property> ownerProperties,
                                        List<Property> buyerProperties,
                                        List<Property> sellerProperties,
                                        int totalProperties) {
        StringBuilder result = new StringBuilder();

        // Header
        result.append(String.format("Showing properties for Client UUID %s (%s):\n\n",
                clientUuidString, targetPerson.getName()));

        // Owner section
        if (!ownerProperties.isEmpty()) {
            result.append("As Owner:\n");
            for (Property property : ownerProperties) {
                result.append(formatPropertyLine(property));
            }
            result.append("\n");
        }

        // Buyer section
        if (!buyerProperties.isEmpty()) {
            result.append("As Buyer:\n");
            for (Property property : buyerProperties) {
                result.append(formatPropertyLine(property));
            }
            result.append("\n");
        }

        // Seller section
        if (!sellerProperties.isEmpty()) {
            result.append("As Seller:\n");
            for (Property property : sellerProperties) {
                result.append(formatPropertyLine(property));
            }
            result.append("\n");
        }

        // Footer with total count
        String propertyWord = totalProperties == 1 ? "property" : "properties";
        result.append(String.format("Total: %d %s", totalProperties, propertyWord));

        return result.toString();
    }

    /**
     * Formats a single property line for display.
     */
    private String formatPropertyLine(Property property) {
        return String.format("  - Property %s: %s (%s, $%s, %s)\n",
                property.getId(),
                property.getPropertyAddress().value,
                property.getType().value,
                formatPrice(property.getPrice().value),
                property.getStatus().value);
    }

    /**
     * Formats the price with comma separators for readability.
     */
    private String formatPrice(String price) {
        try {
            long priceValue = Long.parseLong(price);
            return String.format("%,d", priceValue);
        } catch (NumberFormatException e) {
            return price;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ShowPropertiesCommand)) {
            return false;
        }

        ShowPropertiesCommand otherCommand = (ShowPropertiesCommand) other;
        return clientUuid.equals(otherCommand.clientUuid);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientUuid", clientUuid)
                .toString();
    }
}
