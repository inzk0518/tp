package seedu.address.model.contact;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.uuid.Uuid;

/**
 * Maintains a registry of all {@link Contact} objects in the address book.
 * <p>
 * Each contact is stored in a {@link Map}, keyed by their unique {@link Uuid}.
 * This allows for efficient lookup, addition, and removal of contacts by ID.
 * </p>
 */
public class ContactRegistry {
    private final Map<Integer, Contact> contactMap = new HashMap<>();

    /**
     * Adds a contact to the registry.
     * If a contact with the same UUID already exists, it will be replaced.
     *
     * @param contact The contact to add. Must not be {@code null}.
     */
    public void addContact(Contact contact) {
        contactMap.put(contact.getUuid().getValue(), contact);
    }

    /**
     * Retrieves a contact from the registry by their unique ID.
     *
     * @param id The unique UUID of the contact.
     * @return The {@link Contact} associated with the given ID, or {@code null} if not found.
     */
    public Contact getContact(Integer id) {
        return contactMap.get(id);
    }

    /**
     * Checks whether a contact with the given ID exists in the registry.
     *
     * @param id The unique UUID of the contact.
     * @return {@code true} if the registry contains a contact with the given ID,
     *         {@code false} otherwise.
     */
    public boolean containsId(Integer id) {
        return contactMap.containsKey(id);
    }

    /**
     * Removes a contact from the registry by their ID.
     * If no contact with the given ID exists, no action is taken.
     *
     * @param id The unique UUID of the contact to remove.
     */
    public void removeContact(Integer id) {
        contactMap.remove(id);
    }
}
