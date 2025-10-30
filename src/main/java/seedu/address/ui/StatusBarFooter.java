package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label filePath;

    /**
     * Creates a {@code StatusBarFooter} with the given status message.
     *
     * @param statusMessage The status message to display at footer.
     */
    public StatusBarFooter(String statusMessage) {
        super(FXML);
        filePath.setText(statusMessage);
    }

    /**
     * Updates the status message text in the status bar.
     *
     * @param statusMessage the new status message to display.
     */
    public void setStatusMessage(String statusMessage) {
        this.filePath.setText(statusMessage);
    }
}
