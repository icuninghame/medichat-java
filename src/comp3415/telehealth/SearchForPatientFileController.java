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

public class SearchForPatientFileController extends Controller implements Initializable {

    @FXML TextField patientIDField;
    @FXML Button submitButton;
    @FXML private ListView listView;

    ObservableList outputContent = FXCollections.observableArrayList();

    @FXML
    void searchForFile(ActionEvent event) {
        searchForFile(Integer.parseInt(patientIDField.getText()));
    }

    public static int patientID = 0;
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
        // Initialize the search for patient file form view:

        // Bind the listView to whatever we put in the ObservableList outputContent:
        listView.setItems(outputContent);

    }

    public void searchForFile(int patientID) {
        SearchForPatientFileController.patientID = Integer.parseInt(patientIDField.getText());
        ArrayList allPatientFiles = PatientFile.getAllFiles();
        ArrayList patientFiles = new ArrayList<PatientFile>();



        for (int i=9; i < 11; i++) {
            System.out.println("Checking file " + i);
            if (PatientFile.getFile(i).getPatientID() == this.patientID) {
                        display("FileID: " + PatientFile.getFile(i).getID());
                        display("Doctor ID: " + PatientFile.getFile(i).getDoctorID());
                        display("FileID: " + PatientFile.getFile(i).getPatientID());
                        display("Medical Info: " + PatientFile.getFile(i).getMedicalInfo());
                        display("Medication: " + PatientFile.getFile(i).getMedication());
                        display("Verfied: " + PatientFile.getFile(i).getVerified());
                        display("File URL: " + PatientFile.getFile(i).getFileURL());
            }

        }

    }

    public void display(String file) {
        System.out.println(file);

        // "Lambda expression" to run in a new Thread to avoid JavaFX throwing an IllegalStateException
        Platform.runLater(
                () -> {
                    // Cast the incoming message to "Text"
                    Text msg = new Text(file);
                    // Sets the text wrapping to fit the window:
                    msg.setWrappingWidth(listView.getWidth() * 0.95);
                    // add the message to the output view:
                    outputContent.add(msg);
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
