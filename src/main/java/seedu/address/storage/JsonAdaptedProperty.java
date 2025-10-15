package seedu.address.storage;

import static seedu.address.model.uuid.Uuid.StoredItem.PERSON;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
import seedu.address.model.uuid.Uuid;

/**
 * Jackson-friendly version of {@link Property}.
 */
class JsonAdaptedProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final Integer uuid;
    private final String address;
    private final String bathroom;
    private final String bedroom;
    private final String floorArea;
    private final String listing;
    private final String postal;
    private final String price;
    private final String status;
    private final String type;
    private final String owner;
    private final List<Integer> buyingPersonIds = new ArrayList<>();
    private final List<Integer> sellingPersonIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given property details.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("uuid") Integer uuid,
                               @JsonProperty("address") String address,
                               @JsonProperty("bathroom") String bathroom,
                               @JsonProperty("bedroom") String bedroom,
                               @JsonProperty("floorArea") String floorArea,
                               @JsonProperty("listing") String listing,
                               @JsonProperty("postal") String postal,
                               @JsonProperty("price") String price,
                               @JsonProperty("status") String status,
                               @JsonProperty("type") String type,
                               @JsonProperty("owner") String owner,
                               @JsonProperty("buyingPersonIds") List<Integer> buyingPersonIds,
                               @JsonProperty("sellingPersonIds") List<Integer> sellingPersonIds) {
        this.uuid = uuid;
        this.address = address;
        this.bathroom = bathroom;
        this.bedroom = bedroom;
        this.floorArea = floorArea;
        this.listing = listing;
        this.postal = postal;
        this.price = price;
        this.status = status;
        this.type = type;
        this.owner = owner;
        if (buyingPersonIds != null) {
            this.buyingPersonIds.addAll(buyingPersonIds);
        }
        if (sellingPersonIds != null) {
            this.sellingPersonIds.addAll(sellingPersonIds);
        }
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        uuid = source.getUuid().getValue();
        address = source.getPropertyAddress().value;
        bathroom = source.getBathroom().value;
        bedroom = source.getBedroom().value;
        floorArea = source.getFloorArea().value;
        listing = source.getListing().value;
        postal = source.getPostal().value;
        price = source.getPrice().value;
        status = source.getStatus().value;
        type = source.getType().value;
        owner = source.getOwner().value;
        buyingPersonIds.addAll(source
                .getBuyingPersonIds()
                .stream()
                .map(id -> id.getValue())
                .toList());
        sellingPersonIds.addAll(source
                .getSellingPersonIds()
                .stream()
                .map(id -> id.getValue())
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's
     * {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        if (uuid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Uuid"));
        }
        final Uuid modelUuid = new Uuid(uuid, PROPERTY);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PropertyAddress.class.getSimpleName()));
        }
        if (!PropertyAddress.isValidPropertyAddress(address)) {
            throw new IllegalValueException(PropertyAddress.MESSAGE_CONSTRAINTS);
        }
        final PropertyAddress modelAddress = new PropertyAddress(address);

        if (bathroom == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Bathroom.class.getSimpleName()));
        }
        if (!Bathroom.isValidBathroom(bathroom)) {
            throw new IllegalValueException(Bathroom.MESSAGE_CONSTRAINTS);
        }
        final Bathroom modelBathroom = new Bathroom(bathroom);

        if (bedroom == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Bedroom.class.getSimpleName()));
        }
        if (!Bedroom.isValidBedroom(bedroom)) {
            throw new IllegalValueException(Bedroom.MESSAGE_CONSTRAINTS);
        }
        final Bedroom modelBedroom = new Bedroom(bedroom);

        if (floorArea == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FloorArea.class.getSimpleName()));
        }
        if (!FloorArea.isValidFloorArea(floorArea)) {
            throw new IllegalValueException(FloorArea.MESSAGE_CONSTRAINTS);
        }
        final FloorArea modelFloorArea = new FloorArea(floorArea);
        if (listing == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Listing.class.getSimpleName()));
        }
        String trimmedListing = listing.trim();
        if (!Listing.isValidListing(trimmedListing)) {
            throw new IllegalValueException(Listing.MESSAGE_CONSTRAINTS);
        }
        final Listing modelListing = new Listing(trimmedListing);
        if (postal == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Postal.class.getSimpleName()));
        }
        if (!Postal.isValidPostal(postal)) {
            throw new IllegalValueException(Postal.MESSAGE_CONSTRAINTS);
        }
        final Postal modelPostal = new Postal(postal);
        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);
        if (owner == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Owner.class.getSimpleName()));
        }
        if (!Owner.isValidOwner(owner)) {
            throw new IllegalValueException(Owner.MESSAGE_CONSTRAINTS);
        }
        final Owner modelOwner = new Owner(owner);

        final List<Uuid> tempBuyingPersonIds = new ArrayList<>();
        for (Integer id : this.buyingPersonIds) {
            tempBuyingPersonIds.add(new Uuid(id, PERSON));
        }
        final Set<Uuid> modelBuyingPersonIds = new HashSet<>(tempBuyingPersonIds);

        final List<Uuid> tempSellingPersonIds = new ArrayList<>();
        for (Integer id : this.sellingPersonIds) {
            tempSellingPersonIds.add(new Uuid(id, PERSON));
        }
        final Set<Uuid> modelSellingPersonIds = new HashSet<>(tempSellingPersonIds);

        return new Property(modelUuid, modelAddress, modelBathroom, modelBedroom, modelFloorArea, modelListing,
                modelPostal, modelPrice, modelStatus, modelType, modelOwner,
                modelBuyingPersonIds, modelSellingPersonIds);
    }

}
