package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.property.Property;

/**
 * A UI component that displays information of a {@code Property}.
 */
public class PropertyCard extends UiPart<Region> {

    private static final String FXML = "PropertyListCard.fxml";

    public final Property property;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label propertyId;
    @FXML
    private Label address;
    @FXML
    private Label details;
    @FXML
    private Label price;
    @FXML
    private Label owner;

    /**
     * Creates a {@code PropertyCard} with the given {@code Property} and index to display.
     */
    public PropertyCard(Property property, int displayedIndex) {
        super(FXML);
        this.property = property;
        id.setText(displayedIndex + ". ");
        propertyId.setText("Property " + property.getId());
        address.setText(property.getPropertyAddress().value);
        details.setText(String.format("%s • %s beds • %s baths • %s sqft",
                property.getType().value,
                property.getBedroom().value,
                property.getBathroom().value,
                property.getFloorArea().value));
        price.setText(String.format("$%s • %s • %s",
                formatPrice(property.getPrice().value),
                property.getStatus().value,
                property.getListing().value));
        owner.setText("Owner: " + property.getOwner().value);
    }

    /**
     * Format the price with comma separators to increase readability.
     */
    private String formatPrice(String price) {
        try {
            long priceValue = Long.parseLong(price);
            return String.format("%,d", priceValue);
        } catch (NumberFormatException e) {
            return price;
        }
    }
}
