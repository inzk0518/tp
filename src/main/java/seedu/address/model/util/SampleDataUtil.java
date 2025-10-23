package seedu.address.model.util;

import java.awt.event.HierarchyBoundsListener;

import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;
import static seedu.address.model.uuid.Uuid.StoredItem.PROPERTY;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.contact.BudgetMax;
import seedu.address.model.contact.BudgetMin;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactAddress;
import seedu.address.model.contact.ContactStatus;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Notes;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Tag;
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
import seedu.address.model.uuid.Uuid.StoredItem;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static final Set<Tag> EMPTY_TAG_SET = Set.of();
    private static final Set<Uuid> EMPTY_BUYING_CONTACT_ID_SET = Set.of();
    private static final Set<Uuid> EMPTY_SELLING_CONTACT_ID_SET = Set.of();
    private static final Set<Uuid> EMPTY_BUYING_PROPERTY_ID_SET = Set.of();
    private static final Set<Uuid> EMPTY_SELLING_PROPERTY_ID_SET = Set.of();

    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Uuid(1, CONTACT), new Name("Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new ContactAddress("Blk 30 Geylang Street 29, #06-40"), getTagSet("landlord"),
                        new BudgetMin("500"), new BudgetMax("1000"), new Notes("Prefers email contact"),
                        new ContactStatus("Active"), EMPTY_BUYING_PROPERTY_ID_SET, Set.of(new Uuid(1, PROPERTY))),

            new Contact(new Uuid(2, CONTACT), new Name("Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new ContactAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), EMPTY_TAG_SET,
                        new BudgetMin("700"), new BudgetMax("1500"), new Notes("VIP client"),
                        new ContactStatus("Active"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(3, CONTACT), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new ContactAddress("Blk 11 Ang Mo Kio Street 74, #11-04"), EMPTY_TAG_SET,
                        new BudgetMin("400"), new BudgetMax("1200"), new Notes("Follow up in July"),
                        new ContactStatus("Inactive"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(4, CONTACT), new Name("David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new ContactAddress("Blk 436 Serangoon Gardens Street 26, #16-43"), EMPTY_TAG_SET,
                        new BudgetMin("800"), new BudgetMax("2000"), new Notes("Prefers phone calls"),
                        new ContactStatus("Active"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(5, CONTACT), new Name("Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new ContactAddress("Blk 47 Tampines Street 20, #17-35"), EMPTY_TAG_SET,
                        new BudgetMin("300"), new BudgetMax("900"), new Notes("Interested in workshops"),
                        new ContactStatus("Inactive"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET),

            new Contact(new Uuid(6, CONTACT), new Name("Roy Balakrishnan"), new Phone("92624417"),
                        new Email("royb@example.com"),
                        new ContactAddress("Blk 45 Aljunied Street 85, #11-31"), EMPTY_TAG_SET,
                        new BudgetMin("600"), new BudgetMax("1400"), new Notes("Important partner"),
                        new ContactStatus("Active"), EMPTY_BUYING_PROPERTY_ID_SET, EMPTY_SELLING_PROPERTY_ID_SET)
        };
    }

    public static Property[] getSampleProperties() {
        return new Property[] {
            new Property(new Uuid(1, PROPERTY), new PropertyAddress("123 Yishun Road"), new Bathroom("2"),
                    new Bedroom("3"), new FloorArea("100"), new Listing("sale"), new Postal("545603"),
                    new Price("310000"), new Status("sold"), new Type("HDB"),
                    new Owner("1"), EMPTY_BUYING_CONTACT_ID_SET, Set.of(new Uuid(1, CONTACT)))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        sampleAb.setNextUuid(getSampleContacts().length + 1);
        return sampleAb;
    }

    public static ReadOnlyPropertyBook getSamplePropertyBook() {
        PropertyBook samplePb = new PropertyBook();
        for (Property sampleProperty : getSampleProperties()) {
            samplePb.addProperty(sampleProperty);
        }
        samplePb.setNextUuid(getSampleProperties().length + 1);
        return samplePb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a Uuid set containing the list of integers representing indices given.
     */
    public static Set<Uuid> getUuidSet(StoredItem itemType, int ... ids) {
        return Arrays.stream(ids)
                .boxed()
                .map(id -> new Uuid(id, itemType))
                .collect(Collectors.toSet());
    }

}
