package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for ResultDisplay.
 */
public class ResultDisplayTest {

    @BeforeAll
    public static void initJavaFx() {
        JavaFxInitializer.initialize();
    }

    @Test
    public void constructor_success() {
        // Test that constructor executes without throwing exceptions
        assertDoesNotThrow(() -> new ResultDisplay());
    }

    @Test
    public void constructor_initializesRoot() {
        ResultDisplay resultDisplay = new ResultDisplay();
        assertNotNull(resultDisplay.getRoot());
    }

    @Test
    public void setFeedbackToUser_validString_noException() {
        ResultDisplay resultDisplay = new ResultDisplay();
        String testFeedback = "Test feedback message";

        // Verify method executes without throwing exception
        assertDoesNotThrow(() -> resultDisplay.setFeedbackToUser(testFeedback));
    }
}
