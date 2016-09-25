package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    private Stage stage;
    @FXML
    private MenuItem close;
    @FXML
    private Button fileButton;
    @FXML
    private ScrollPane fileDisplay;

    public void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public void chooseFile(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a text file");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fc.showOpenDialog(stage);
        if (file != null && file.canRead() && file.exists() && file.isFile() && file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()).equals(".txt")) {

        }
    }

    public void closeProgram(ActionEvent e) {
        System.exit(0);
    }


}
