package seedu.address.model.property.predicates;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link PropertyMatchesFilterPredicate}.
 */
public class PropertyMatchesFilterPredicateTest {

    private Property condoProperty = new Property(
            "Random id",
            new PropertyAddress("123 Orchard Road"),
            new Bathroom("2"),
            new Bedroom("3"),
            new FloorArea("120"),
            new Listing("sale"),
            new Postal("123456"),
            new Price("800000"),
            new Status("listed"),
            new Type("condo"),
            new Owner("JohnTan"),
            new HashSet<>(),
            new HashSet<>()
    );
    private Property hdbProperty = new Property(
            "randomId",
            new PropertyAddress("456 Bukit Timah"),
            new Bathroom("1"),
            new Bedroom("2"),
            new FloorArea("80"),
            new Listing("rent"),
            new Postal("654321"),
            new Price("400000"),
            new Status("sold"),
            new Type("hdb"),
            new Owner("MaryLim"),
            new HashSet<>(),
            new HashSet<>()
    );

    @Test
    public void testMatchesTypeSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().withType("condo").build();
        assertTrue(predicate.test(condoProperty));
        assertFalse(predicate.test(hdbProperty));
    }

    @Test
    public void testMatchesOwnerSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().withOwner("mary").build();
        assertTrue(predicate.test(hdbProperty));
        assertFalse(predicate.test(condoProperty));
    }

    @Test
    public void testMatchesStatusSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().withStatus("listed").build();
        assertTrue(predicate.test(condoProperty));
        assertFalse(predicate.test(hdbProperty));
    }

    @Test
    public void testMatchesBedroomSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().withBedroom("3").build();
        assertTrue(predicate.test(condoProperty));
        assertFalse(predicate.test(hdbProperty));
    }

    @Test
    public void testMatchesBathroomSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().withBathroom("1").build();
        assertTrue(predicate.test(hdbProperty));
        assertFalse(predicate.test(condoProperty));
    }

    @Test
    public void testMatchesPriceSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().withPrice("800000").build();
        assertTrue(predicate.test(condoProperty));
        assertFalse(predicate.test(hdbProperty));
    }

    @Test
    public void testMatchesAddressSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().withAddress("orchard").build();
        assertTrue(predicate.test(condoProperty));
        assertFalse(predicate.test(hdbProperty));
    }

    @Test
    public void testMatchesMultipleCriteriaSuccess() {
        var predicate = new PropertyMatchesFilterPredicate.Builder()
                .withType("condo")
                .withStatus("listed")
                .withOwner("john")
                .build();
        assertTrue(predicate.test(condoProperty));
        assertFalse(predicate.test(hdbProperty));
    }

    @Test
    public void testEmptyPredicateMatchesAll() {
        var predicate = new PropertyMatchesFilterPredicate.Builder().build();
        assertTrue(predicate.test(condoProperty));
        assertTrue(predicate.test(hdbProperty));
    }
}
