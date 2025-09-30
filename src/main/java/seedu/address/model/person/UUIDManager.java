package seedu.address.model.person;

public class UUIDManager {
    private static int nextUUID = 1; // start id from 1

    public static int generateUUID() {
        return nextUUID++;
    }

    public static void reset() {
        nextUUID = 1; // reset id, only will be done on "clear" command
    }
}