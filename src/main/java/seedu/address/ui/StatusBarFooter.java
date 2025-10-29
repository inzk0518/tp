package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

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
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation) {
        super(FXML);
        filePath.setText(Paths.get(".").resolve(saveLocation).toString());
    }

    /**
     * Updates the file path text in the status bar.
     *
     * @param filePath the new file path to display.
     */
    public void setFilePath(Path filePath) {
        this.filePath.setText(Paths.get(".").resolve(filePath).toString());
    }
}
