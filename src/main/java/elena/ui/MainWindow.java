package elena.ui;

import elena.core.Elena;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Elena elena;

    private final Image userImage =
            new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image elenaImage =
            new Image(this.getClass().getResourceAsStream("/images/DaElena.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Elena instance. */
    public void setElena(Elena e) {
        elena = e;
    }

    /**
     * Handles user input by creating dialog boxes for both user and Elena,
     * appending them to the container, and clearing the input field.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = elena.handleInput(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, elenaImage)
        );
        userInput.clear();
    }
}
