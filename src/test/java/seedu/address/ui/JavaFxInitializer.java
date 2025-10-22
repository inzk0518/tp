package seedu.address.ui;

import javafx.application.Platform;

/**
 * Helper class to initialize JavaFX toolkit for testing.
 */
public class JavaFxInitializer {
    private static boolean isInitialized = false;

    /**
     * Initializes JavaFX toolkit if not already initialized.
     */
    public static synchronized void initialize() {
        if (isInitialized) {
            return;
        }

        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Already initialized, ignore
        }
        isInitialized = true;
    }
}
