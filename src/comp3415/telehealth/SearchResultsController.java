package comp3415.telehealth;

import comp3415.telehealth.model.PatientFile;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchResultsController extends Controller implements Initializable {

    @FXML TextField patientIDField;
    @FXML Button submitButton;
    @FXML private ListView<?> listView;

    ObservableList<Text> outputContent = FXCollections.observableArrayList();


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SearchForPatientFileController search = new SearchForPatientFileController();
        int fileID = search.fileID;
        PatientFile.getFile(fileID).getPatientID();
        display((ArrayList) outputContent);
    }



    public void display(ArrayList file) {



        // "Lambda expression" to run in a new Thread to avoid JavaFX throwing an IllegalStateException
        Platform.runLater(
                () -> {
                    // Cast the incoming message to "Text"
                    Text msg = new Text (file.toString());
                    // Sets the text wrapping to fit the window:
                    msg.setWrappingWidth(listView.getWidth() * 0.95);
                    // add the message to the output view:
                    outputContent.add(msg);
                    //listView.scrollTo(msg);
                }
        );

    }

    /**
     * Click listener for back button press
     * @param e the action event that led to this method call
     */
    public void back(ActionEvent e){
        try {
            redirectToDashboard();
        }catch (IOException ignored){

        }
    }

}
