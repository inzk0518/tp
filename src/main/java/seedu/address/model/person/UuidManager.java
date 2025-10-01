package seedu.address.model.person;

/**
 * Utility class for managing unique identifiers (UUIDs) for {@link Person} objects.
 * <p>
 * UUIDs are represented as sequential positive integers (1, 2, 3, ...).
 * This ensures uniqueness across the lifetime of the application unless reset.
 * </p>
 */
public class UuidManager {
    private static int nextUuid = 1; // Start IDs from 1

    /**
     * Generates and returns the next unique UUID.
     *
     * @return The next available UUID as an integer.
     */
    public static int generateUuid() {
        return nextUuid++;
    }

    /**
     * Resets the UUID counter back to 1.
     * <p>
     * This should typically only be used in special scenarios,
     * such as when clearing the entire address book.
     * </p>
     */
    public static void reset() {
        nextUuid = 1;
    }
}
