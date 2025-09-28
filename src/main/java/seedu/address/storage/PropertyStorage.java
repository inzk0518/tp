package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.ReadOnlyProperty;

public class PropertyStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPropertyFilePath();

    /**
     * Returns Property data as a {@link ReadOnlyProperty}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyProperty> readProperty() throws DataLoadingException;

    /**
     * @see #getPropertyFilePath()
     */
    Optional<ReadOnlyProperty> readProperty(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyProperty} to the storage.
     * @param property cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProperty(ReadOnlyProperty property) throws IOException;

    /**
     * @see #saveProperty(ReadOnlyProperty)
     */
    void saveProperty(ReadOnlyProperty property, Path filePath) throws IOException;

}
