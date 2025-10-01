package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class PropertyValueObjectsTest {

    @Test
    void address_validation() {
        assertThrows(NullPointerException.class, () -> new Address(null));
        assertThrows(IllegalArgumentException.class, () -> new Address("12345"));
        assertThrows(IllegalArgumentException.class, () -> new Address("Main Street"));
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));
        assertTrue(Address.isValidAddress("123 Main St 5"));
        assertEquals("123 Main St 5", new Address(" 123 Main St 5 ").toString());
    }

    @Test
    void bathroom_validation() {
        assertThrows(NullPointerException.class, () -> new Bathroom(null));
        assertThrows(IllegalArgumentException.class, () -> new Bathroom("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Bathroom("21"));
        assertThrows(NullPointerException.class, () -> Bathroom.isValidBathroom(null));
        assertTrue(Bathroom.isValidBathroom("0"));
        assertTrue(Bathroom.isValidBathroom("20"));
    }

    @Test
    void bedroom_validation() {
        assertThrows(NullPointerException.class, () -> new Bedroom(null));
        assertThrows(IllegalArgumentException.class, () -> new Bedroom("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Bedroom("21"));
        assertThrows(NullPointerException.class, () -> Bedroom.isValidBedroom(null));
        assertTrue(Bedroom.isValidBedroom("0"));
        assertTrue(Bedroom.isValidBedroom("20"));
    }

    @Test
    void floorArea_validation() {
        assertThrows(NullPointerException.class, () -> new FloorArea(null));
        assertThrows(IllegalArgumentException.class, () -> new FloorArea("49"));
        assertThrows(IllegalArgumentException.class, () -> new FloorArea("100001"));
        assertThrows(NullPointerException.class, () -> FloorArea.isValidFloorArea(null));
        assertTrue(FloorArea.isValidFloorArea("50"));
        assertTrue(FloorArea.isValidFloorArea("100000"));
    }

    @Test
    void listing_validation() {
        assertThrows(NullPointerException.class, () -> new Listing(null));
        assertThrows(IllegalArgumentException.class, () -> new Listing("lease"));
        assertThrows(NullPointerException.class, () -> Listing.isValidListing(null));
        assertTrue(Listing.isValidListing("sale"));
        assertTrue(Listing.isValidListing("rent"));
        assertEquals("sale", new Listing("SALE").toString());
    }

    @Test
    void postal_validation() {
        assertThrows(NullPointerException.class, () -> new Postal(null));
        assertThrows(IllegalArgumentException.class, () -> new Postal("12345"));
        assertThrows(IllegalArgumentException.class, () -> new Postal("1234567"));
        assertThrows(IllegalArgumentException.class, () -> new Postal("ABC123"));
        assertThrows(NullPointerException.class, () -> Postal.isValidPostal(null));
        assertTrue(Postal.isValidPostal("123456"));
    }

    @Test
    void price_validation() {
        assertThrows(NullPointerException.class, () -> new Price(null));
        assertThrows(IllegalArgumentException.class, () -> new Price("0"));
        assertThrows(IllegalArgumentException.class, () -> new Price("-1"));
        assertThrows(IllegalArgumentException.class, () -> new Price("1000000000001"));
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));
        assertTrue(Price.isValidPrice("1"));
        assertTrue(Price.isValidPrice("1000000000000"));
    }

    @Test
    void status_validation() {
        assertThrows(NullPointerException.class, () -> new Status(null));
        assertThrows(IllegalArgumentException.class, () -> new Status("pending"));
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));
        assertTrue(Status.isValidStatus("listed"));
        assertTrue(Status.isValidStatus("sold"));
        assertEquals("rented", new Status("RENTED").toString());
    }

    @Test
    void type_validation() {
        assertThrows(NullPointerException.class, () -> new Type(null));
        assertThrows(IllegalArgumentException.class, () -> new Type("villa"));
        assertThrows(NullPointerException.class, () -> Type.isValidType(null));
        assertTrue(Type.isValidType("hdb"));
        assertTrue(Type.isValidType("Condo"));
        assertEquals("apartment", new Type(" Apartment ").toString());
    }

    @Test
    void owner_validation() {
        assertThrows(NullPointerException.class, () -> new Owner(null));
        assertThrows(IllegalArgumentException.class, () -> new Owner(""));
        assertThrows(IllegalArgumentException.class, () -> new Owner("owner 1"));
        assertThrows(NullPointerException.class, () -> Owner.isValidOwner(null));
        assertTrue(Owner.isValidOwner("owner_123"));
        assertEquals("owner123", new Owner(" owner123 ").toString());
    }
}
