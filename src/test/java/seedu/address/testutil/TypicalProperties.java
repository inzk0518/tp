package seedu.address.testutil;

import seedu.address.model.property.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperties {

    public static final Property PROPERTY_ALPHA = new PropertyBuilder().withPropertyAddress("123 Main St 5")
            .withBathroom("2").withBedroom("3").withFloorArea("120").withListing("sale")
            .withPostal("123456").withPrice("500000").withStatus("listed")
            .withType("HDB").withOwner("owner123").withBuyingPersonIds().withSellingPersonIds().build();

    public static final Property PROPERTY_ALPHA_VARIANT = new PropertyBuilder().withPropertyAddress("123 Main St 5")
            .withBathroom("2").withBedroom("3").withFloorArea("120").withListing("rent")
            .withPostal("123456").withPrice("510000").withStatus("listed")
            .withType("HDB").withOwner("owner789").withBuyingPersonIds().withSellingPersonIds().build();

    public static final Property PROPERTY_BETA = new PropertyBuilder().withPropertyAddress("456 Market Ave 9")
            .withBathroom("1").withBedroom("2").withFloorArea("80").withListing("rent")
            .withPostal("654321").withPrice("3500").withStatus("listed")
            .withType("apartment").withOwner("owner456").withBuyingPersonIds().withSellingPersonIds().build();

    public static final Property PROPERTY_GAMMA = new PropertyBuilder().withPropertyAddress("321 Market St 9")
            .withBathroom("3").withBedroom("4").withFloorArea("150").withListing("rent")
            .withPostal("654321").withPrice("750000").withStatus("sold")
            .withType("Condo").withOwner("owner321").withBuyingPersonIds().withSellingPersonIds().build();

}
