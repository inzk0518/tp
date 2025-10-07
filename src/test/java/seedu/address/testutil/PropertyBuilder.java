package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.property.Bathroom;
import seedu.address.model.property.Bedroom;
import seedu.address.model.property.FloorArea;
import seedu.address.model.property.Listing;
import seedu.address.model.property.Owner;
import seedu.address.model.property.Postal;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyAddress;
import seedu.address.model.property.Status;
import seedu.address.model.property.Type;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Property objects.
 */
public class PropertyBuilder {

    public static final String DEFAULT_PROPERTY_ADDRESS = "123 Kent Ridge Road";
    public static final String DEFAULT_BATHROOM = "1";
    public static final String DEFAULT_BEDROOM = "2";
    public static final String DEFAULT_FLOORAREA = "2000";
    public static final String DEFAULT_LISTING = "rent";
    public static final String DEFAULT_POSTAL = "886231";
    public static final String DEFAULT_PRICE = "210000";
    public static final String DEFAULT_STATUS = "listed";
    public static final String DEFAULT_TYPE = "landed";
    public static final String DEFAULT_OWNER = "owner123";
    public static final Set<Index> DEFAULT_LINKED_PERSON_IDS = SampleDataUtil.getIndexSet();

    private PropertyAddress propertyAddress;
    private Bathroom bathroom;
    private Bedroom bedroom;
    private FloorArea floorArea;
    private Listing listing;
    private Postal postal;
    private Price price;
    private Status status;
    private Type type;
    private Owner owner;
    private Set<Index> linkedPersonIds;

    /**
     * Creates a {@code PropertyBuilder} with the default details.
     */
    public PropertyBuilder() {
        propertyAddress = new PropertyAddress(DEFAULT_PROPERTY_ADDRESS);
        bathroom = new Bathroom(DEFAULT_BATHROOM);
        bedroom = new Bedroom(DEFAULT_BEDROOM);
        floorArea = new FloorArea(DEFAULT_FLOORAREA);
        listing = new Listing(DEFAULT_LISTING);
        postal = new Postal(DEFAULT_POSTAL);
        price = new Price(DEFAULT_PRICE);
        status = new Status(DEFAULT_STATUS);
        type = new Type(DEFAULT_TYPE);
        owner = new Owner(DEFAULT_OWNER);
        linkedPersonIds = DEFAULT_LINKED_PERSON_IDS;
    }

    /**
     * Initializes the PropertyBuilder with the data of {@code propertyToCopy}.
     */
    public PropertyBuilder(Property propertyToCopy) {
        propertyAddress = propertyToCopy.getPropertyAddress();
        bathroom = propertyToCopy.getBathroom();
        bedroom = propertyToCopy.getBedroom();
        floorArea = propertyToCopy.getFloorArea();
        listing = propertyToCopy.getListing();
        postal = propertyToCopy.getPostal();
        price = propertyToCopy.getPrice();
        status = propertyToCopy.getStatus();
        type = propertyToCopy.getType();
        owner = propertyToCopy.getOwner();
        linkedPersonIds = new HashSet<>(propertyToCopy.getLinkedPersonIds());
    }

    /**
     * Sets the {@code PropertyAddress} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPropertyAddress(String address) {
        this.propertyAddress = new PropertyAddress(address);
        return this;
    }

    /**
     * Sets the {@code Bathroom} of the {@code Property} that we are building.
     */
    public PropertyBuilder withBathroom(String bathroom) {
        this.bathroom = new Bathroom(bathroom);
        return this;
    }

    /**
     * Sets the {@code Bedroom} of the {@code Property} that we are building.
     */
    public PropertyBuilder withBedroom(String bedroom) {
        this.bedroom = new Bedroom(bedroom);
        return this;
    }

    /**
     * Sets the {@code FloorArea} of the {@code Property} that we are building.
     */
    public PropertyBuilder withFloorArea(String floorArea) {
        this.floorArea = new FloorArea(floorArea);
        return this;
    }

    /**
     * Sets the {@code Listing} of the {@code Property} that we are building.
     */
    public PropertyBuilder withListing(String listing) {
        this.listing = new Listing(listing);
        return this;
    }

    /**
     * Sets the {@code Postal} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPostal(String postal) {
        this.postal = new Postal(postal);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Property} that we are building.
     */
    public PropertyBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Property} that we are building.
     */
    public PropertyBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Property} that we are building.
     */
    public PropertyBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Owner} of the {@code Property} that we are building.
     */
    public PropertyBuilder withOwner(String owner) {
        this.owner = new Owner(owner);
        return this;
    }

    /**
     * Parses the {@code ids} into a {@code Set<Index>} and set it to the {@code Person} that we are building.
     */
    public PropertyBuilder withLinkedPersonIds(int ... ids) {
        this.linkedPersonIds = SampleDataUtil.getIndexSet(ids);
        return this;
    }

    /**
     * Builds the {@code Property} that we are building.
     */
    public Property build() {
        return new Property(propertyAddress, bathroom, bedroom, floorArea, listing, postal, price, status,
                type, owner, linkedPersonIds);
    }

}
