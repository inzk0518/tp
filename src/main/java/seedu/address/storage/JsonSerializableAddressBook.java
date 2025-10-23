package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Contact;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_CONTACT = "Contacts list contains duplicate contact(s).";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    @JsonProperty("nextUuid")
    private final int nextUuid; // stored UUID counter

    /**
     * Constructs a {@code JsonSerializableAddressBook} with contacts and UUID.
     * @param contacts List of contacts adapted for JSON.
     * @param nextUuid Next UUID counter to store.
     */
    @JsonCreator
    public JsonSerializableAddressBook(
            @JsonProperty("contacts") List<JsonAdaptedContact> contacts,
            @JsonProperty("nextUuid") int nextUuid) {
        this.contacts.addAll(contacts);
        this.nextUuid = nextUuid;
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContact::new).collect(Collectors.toList()));
        nextUuid = source.getNextUuid();
    }

    /**
     * Returns the stored UUID counter.
     */
    public int getNextUuid() {
        return nextUuid;
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     * @return AddressBook model with contacts and nextUuid restored.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (addressBook.hasContact(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
            }
            addressBook.addContact(contact);
        }
        addressBook.setNextUuid(this.nextUuid);
        return addressBook;
    }

}
