package elena.apps;

import java.io.IOException;

import elena.ui.MainWindow;
import elena.core.Elena;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Elena using FXML.
 */
public class Main extends Application {
    private final Elena elena = new Elena("./data/elena.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setElena(elena);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
