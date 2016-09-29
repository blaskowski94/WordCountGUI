package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Controller class that uses FileProcessing and GUI elements to update display and process files per user input. This is
 * the "Controller" from the "Model-View-Controller" design pattern. This class defines the event handlers for the
 * objects in the GUI.
 *
 * @author Bob Laskowski
 */
public class Controller {

    /* ======================================== INSTANCE VARIABLES ======================================== */

    private Stage stage; // primary stage passed from Main
    @FXML
    private TextArea fileDisplay; // GUI component where text file information will be displayed
    private String fileName; // The filepath of the text file, used for saving
    private boolean fileChosen = false; // True when file has been validated, used to determine if there is a file to writeToFile

    /* ======================================== PUBLIC METHODS ======================================== */

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
     * Defines the action taken by the "Choose File" button. It creates a File Chooser to allow the user
     * to select a text file. Once the file is selected, it is passed to the displayFile method.
     */
    public void chooseFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose a text file");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fc.showOpenDialog(stage);
        displayFile(file);
    }

    /**
     * Handles the event of a mouse drag over. It allows the program to accept dragged files and copies them
     * to the drag board of the event so that it can be handled later.
     *
     * @param d An event resulting from a drag and drop gesture
     */
    public void dragOver(DragEvent d) {
        Dragboard db = d.getDragboard();
        if (db.hasFiles()) {
            d.acceptTransferModes(TransferMode.COPY);
        } else
            d.consume();
    }

    /**
     * Handles the event of a drag and drop completing. We check to see if a File has been added to the drag board
     * and then we make sure only one file has been added. If more than one file has been added we do not proceed and
     * display an error message in the text area. If only one file has been added, we get the file from the List and
     * pass it to the displayFile method.
     *
     * @param d An event resulting from a drag and drop gesture
     */
    public void dragDropped(DragEvent d) {
        fileDisplay.setStyle("-fx-border-color: lightgray;"); // reset border
        Dragboard db = d.getDragboard();
        boolean success = false;
        List<File> file = db.getFiles();
        if (file.size() == 1) { // Check to see if more than one file being dragged in
            success = true;
            displayFile(file.get(0));
        } else {
            fileDisplay.setText("You may only drag and drop one file at a time.");
        }
        if (!success) // Disallow file saving if problem with file
            fileChosen = false;
        d.setDropCompleted(success);
        d.consume();
    }

    /**
     * Changes CSS styling of TextArea when a file is dragged over that GUI element. If one text file is dragged,
     * border turns green. Otherwise, border turns red.
     *
     * @param d An event resulting from a drag and drop gesture
     */
    public void dragEntered(DragEvent d) {
        Dragboard db = d.getDragboard();
        boolean accepted = false;
        List<File> file = db.getFiles();
        if (file.size() == 1 && file.get(0).getName().toLowerCase().endsWith(".txt"))
            accepted = true;
        if (accepted) {
            fileDisplay.setStyle("-fx-border-color: green;"
                    + "-fx-border-width: 5;"
                    + "-fx-background-color: lightgray;"
                    + "-fx-border-style: solid;");
        } else {
            fileDisplay.setStyle("-fx-border-color: red;"
                    + "-fx-border-width: 5;"
                    + "-fx-background-color: lightgray;"
                    + "-fx-border-style: solid;");
        }
        d.consume();
    }

    /**
     * Resets the border to light gray if file is not dropped and drag exits the program.
     *
     * @param d An event resulting from a drag and drop gesture
     */
    public void dragExited(DragEvent d) {
        fileDisplay.setStyle("-fx-border-color: lightgray;");
        d.consume();
    }

    /**
     * Resets the program to its start state with default prompt. This is the event handler for the Reset menu button.
     */
    public void reset() {
        fileDisplay.setText("Choose a text file to display information about the file. Use the button below or drag and drop the file here.");
        fileChosen = false;
    }

    /**
     * Saves info generated about a file to a txt.info file.
     * <p>
     * Defines the action for the save file menu button. It first checks to see if a file has been selected and if not
     * displays an error message. If a file has been selected, a file chooser opens in the directory where the file
     * was selected from and with the default option to save the file as "*filename*.txt.info" where *filename* is the
     * name of the file. Once the save directory and file name have been chosen in the file chooser, the writeToFile
     * method is called.
     */
    public void saveFile() {
        if (fileChosen) {
            String fileNameInfo = fileName + ".info";
            FileChooser fc = new FileChooser();
            fc.setTitle("Save info file");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Info Text Files", "*.info"));
            fc.setInitialFileName(fileNameInfo);
            fc.setInitialDirectory(new File(fileName).getParentFile());
            File file = fc.showSaveDialog(stage);
            if (file != null) {
                writeToFile(fileDisplay.getText(), file);
            }
        } else {
            fileDisplay.setText("You must choose a file before saving it.");
        }
    }

    /**
     * Default close action, closes the program. Called from the File - Close menu button.
     */
    public void closeProgram() {
        System.exit(0);
    }

    /* ======================================== PRIVATE METHODS ======================================== */


    /**
     * Checks to see if file is valid.
     * <p>
     * Checks that the file selected exists, we have permission to read the file, that it is a file
     * (and not a directory), and that the extension is ".txt". Future developments may allow other file extensions
     * that contain text and verify by the encoding of the file instead of the extension. If these conditions are met,
     * fileChosen is set to true and fileName is set to the absolute path of the file. Both of these instance variables
     * are used for saving purposes so as not to allow saving before a valid file has been selected. If conditions are
     * not met, fileChosen is set to false, fileName is set to an empty string and an error message is displayed.
     *
     * @param file The File object to be validated
     * @return True if conditions are met and we are able to process the file, false otherwise
     */
    private boolean validateFile(File file) {
        if (file != null && file.canRead() && file.exists() && file.isFile() && file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()).equals(".txt")) {
            fileChosen = true;
            fileName = file.getAbsolutePath();
            return true;
        }
        fileChosen = false;
        fileName = "";
        fileDisplay.setText("There was a problem with the file you tried to select.");
        return false;
    }

    /**
     * Calls the validateFile method and if the file is verified, a new FileProcessing object is created and a String is
     * built to contain the text that will be displayed in the text area FileDisplay by calling FileProcessing methods.
     * If the file is not verified, no action is taken.
     *
     * @param file The file to be processed and displayed
     */
    private void displayFile(File file) {
        if (validateFile(file)) {
            FileProcessing fp = new FileProcessing(file);
            String s = "";
            s += file.getAbsolutePath() + "\n";
            s += "Total number of words in file: " + fp.countWords() + "\n";
            s += "Total number of different words: " + fp.countDiffWords() + "\n";
            s += "Count by word: \n" + fp.countByWord();
            fileDisplay.setText(s);
        }
    }

    /**
     * Writes a string to a file using a FileWriter.
     *
     * @param content The String to be written to the file
     * @param file    The File where the content will be stored
     */
    private void writeToFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
