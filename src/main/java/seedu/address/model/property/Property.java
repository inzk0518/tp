package seedu.address.model.property;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.commons.util.ToStringBuilder;

public class Property {

    // Identity fields
    private final String id;

    // Data fields
    private final Address address;
    private final Bathroom bathroom;
    private final Bedroom bedroom;
    private final FloorArea floorArea;
    private final Listing listing;
    private final Postal postal;
    private final Price price;
    private final Status status;
    private final Type type;
    private final Owner owner;

    public Property(Address address, Bathroom bathroom, Bedroom bedroom, FloorArea floorArea, Listing listing,
                    Postal postal, Price price, Status status, Type type, Owner owner) {
        // Listing can be null
        requireAllNonNull(address, bathroom, bedroom, floorArea, postal, price, status, type, owner);
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
        this.id = java.util.UUID.randomUUID().toString();
    }

    // Getter methods
    public Address getAddress() {
        return address;
    }

    public Bathroom getBathroom() {
        return bathroom;
    }

    public Bedroom getBedroom() {
        return bedroom;
    }

    public FloorArea getFloorArea() {
        return floorArea;
    }

    public Listing getListing() {
        return listing;
    }

    public Postal getPostal() {
        return postal;
    }

    public Price getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }

    public String getId() {
        return id;
    }
    public Owner getOwner() {
        return owner;
    }

    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && otherProperty.getId().equals(getId());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return java.util.Objects.hash(address, bathroom, bedroom, floorArea, listing, postal, price, status, type, owner);
    }

    /*
     * Returns true if both properties have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Property)) {
            return false;
        }

        Property otherProperty = (Property) other;
        return address.equals(otherProperty.address)
                && bathroom.equals(otherProperty.bathroom)
                && bedroom.equals(otherProperty.bedroom)
                && floorArea.equals(otherProperty.floorArea)
                && ((listing == null && otherProperty.listing == null)
                    || (listing != null && listing.equals(otherProperty.listing)))
                && postal.equals(otherProperty.postal)
                && price.equals(otherProperty.price)
                && status.equals(otherProperty.status)
                && type.equals(otherProperty.type)
                && owner.equals(otherProperty.owner);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Id", id)
                .add("Address", address)
                .add("Bathroom", bathroom)
                .add("Bedroom", bedroom)
                .add("Floor Area", floorArea)
                .add("Listing", listing)
                .add("Postal", postal)
                .add("Price", price)
                .add("Status", status)
                .add("Type", type)
                .add("Owner", owner)
                .toString();
    }
}
