package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Controller class that uses FileProcessing and GUI elements to update display and process files per user input. This is
 * the "Controller" from the "Model-View-Controller" design pattern. This class defines the actions taken when "Choose
 * File" button is pressed and for "Close" menu item.
 *
 * @author Bob Laskowski
 */
public class Controller {

    private Stage stage; // primary stage passed from Main
    @FXML
    private TextArea fileDisplay; // GUI component where text file information will be displayed

    /**
     * Initialization method called in main class for the purpose of passing the primary stage to the Controller class
     * so that it can be used by the FileChooser. This method initializes the private stage instance variable with the
     * stage created in Main.
     *
     * @param primaryStage The primary stage of the JavaFX application
     */
    public void init(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * This method defines the action taken by the "Choose File" button. It creates a File Chooser to allow the user
     * to select a text file, ensures the file selected exists, we have permission to read the file, that it is a file
     * (and not a directory), and that the extension is ".txt". Future developments may allow other file extensions
     * that contain text and verify by the encoding of the file instead of the extension. If the file is verified, a
     * new FileProcessing object is created and a String is built to contain the text that will be displayed in the text
     * area FileDisplay by calling FileProcessing methods.
     */
    public void chooseFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a text file");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fc.showOpenDialog(stage);
        if (file != null && file.canRead() && file.exists() && file.isFile() && file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()).equals(".txt")) {
            FileProcessing fp = new FileProcessing(file);
            String s = "";
            s += file.toString() + "\n";
            s += "Total number of words in file: " + fp.countWords() + "\n";
            s += "Total number of different words: " + fp.countDiffWords() + "\n";
            s += "Count by word: \n" + fp.countByWord();
            fileDisplay.setText(s);
        }
    }

    /**
     * Default close action, closes the program. Called from the File > Close menu button.
     */
    public void closeProgram() {
        System.exit(0);
    }


}
