package seedu.address.model.util;

import static seedu.address.model.uuid.Uuid.StoredItem.CONTACT;

import java.util.Arrays;
import java.util.HashSet;
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
import seedu.address.model.property.Property;
import seedu.address.model.tag.Tag;
import seedu.address.model.uuid.Uuid;
import seedu.address.model.uuid.Uuid.StoredItem;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Uuid(1, CONTACT), new Name("Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new ContactAddress("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends"),
                        new BudgetMin("500"), new BudgetMax("1000"), new Notes("Prefers email contact"),
                        new ContactStatus("Active"), new HashSet<>(), new HashSet<>()),

            new Contact(new Uuid(2, CONTACT), new Name("Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new ContactAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                                            getTagSet("colleagues", "friends"),
                        new BudgetMin("700"), new BudgetMax("1500"), new Notes("VIP client"),
                        new ContactStatus("Active"), new HashSet<>(), new HashSet<>()),

            new Contact(new Uuid(3, CONTACT), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new ContactAddress("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours"),
                        new BudgetMin("400"), new BudgetMax("1200"), new Notes("Follow up in July"),
                        new ContactStatus("Inactive"), new HashSet<>(), new HashSet<>()),

            new Contact(new Uuid(4, CONTACT), new Name("David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new ContactAddress("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family"),
                        new BudgetMin("800"), new BudgetMax("2000"), new Notes("Prefers phone calls"),
                        new ContactStatus("Active"), new HashSet<>(), new HashSet<>()),

            new Contact(new Uuid(5, CONTACT), new Name("Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new ContactAddress("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"),
                        new BudgetMin("300"), new BudgetMax("900"), new Notes("Interested in workshops"),
                        new ContactStatus("Inactive"), new HashSet<>(), new HashSet<>()),

            new Contact(new Uuid(6, CONTACT), new Name("Roy Balakrishnan"), new Phone("92624417"),
                        new Email("royb@example.com"),
                        new ContactAddress("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"),
                        new BudgetMin("600"), new BudgetMax("1400"), new Notes("Important partner"),
                        new ContactStatus("Active"), new HashSet<>(), new HashSet<>())
        };
    }

    public static Property[] getSampleProperties() {
        return new Property[] {
            // Sample properties can be added here in the future
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        return sampleAb;
    }

    public static ReadOnlyPropertyBook getSamplePropertyBook() {
        PropertyBook samplePb = new PropertyBook();
        for (Property sampleProperty : getSampleProperties()) {
            samplePb.addProperty(sampleProperty);
        }
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
