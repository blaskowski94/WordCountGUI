package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class drives the Text File Processing program. It loads the FXML file, which was created with SceneBuilder 2.0,
 * creates the primary stage and shows the stage on the screen.
 */
public class Main extends Application {

    /**
     * Main method that launches the program.
     *
     * @param args A String array of the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX method that creates the primary stage and loads the other components from the FXML file.
     *
     * @param primaryStage The primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
        try {
            Parent root = loader.load();
            primaryStage.setTitle("Text File Processing");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            Controller controller = loader.getController();
            controller.init(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
