package comp3415.telehealth;

import comp3415.telehealth.db.GlobalUser;
import comp3415.telehealth.model.PatientFile;
import comp3415.telehealth.model.User;
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
    @FXML private Button editButton;

    public static int patientID;
    public static int doctorID;
    public static int fileID;

    @FXML void editFile(ActionEvent event) {
        try {
            patientID = Integer.parseInt(patientIDField.getText());
            redirectToViewPatientFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ObservableList outputContent = FXCollections.observableArrayList();

    @FXML
    void searchForFile(ActionEvent event) {
        patientID = Integer.parseInt(patientIDField.getText());
        searchForFile(patientID);
    }


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
        // Initialize the search for patient file form view

        // Set edit button visibility to false
        editButton.setVisible(false);

        // Bind the listView to whatever we put in the ObservableList outputContent:
        listView.setItems(outputContent);

    }

    public void searchForFile(int pID) {
        outputContent.clear();
        SearchForPatientFileController.patientID = pID;
        ArrayList allPatientFiles = PatientFile.getAllFiles();
        editButton.setVisible(false);

        // If empty, stop here.
        if (allPatientFiles.isEmpty()) {
            display("No files found.");
            return;
        }

        // Search for file with same patientID
        for (int i=0; i < allPatientFiles.size(); i++) {
            System.out.println("Checking file " + i);
            if (PatientFile.getFile(i).getPatientID() == pID) {
                        display("FileID: " + PatientFile.getFile(i).getID());
                        display("Doctor ID: " + PatientFile.getFile(i).getDoctorID());
                        display("Patient ID: " + PatientFile.getFile(i).getPatientID());
                        display("Medical Info: " + PatientFile.getFile(i).getMedicalInfo());
                        display("Medication: " + PatientFile.getFile(i).getMedication());
                        display("Verfied: " + PatientFile.getFile(i).getVerified());
                        display("File URL: " + PatientFile.getFile(i).getFileURL());
                        display("----"); // spacer for multiple files
                        editButton.setVisible(true);
                        SearchForPatientFileController.fileID = PatientFile.getFile(i).getID();
                        SearchForPatientFileController.doctorID = PatientFile.getFile(i).getDoctorID();
            }

        }

    }

    public void display(String output) {
        try {
            // "Lambda expression" to run in a new Thread to avoid JavaFX throwing an IllegalStateException
            Platform.runLater(
                    () -> {
                        // Cast the incoming message to "Text"
                        Text txt = new Text(output);
                        // Sets the text wrapping to fit the window:
                        txt.setWrappingWidth(listView.getWidth() * 0.8);
                        // add the message to the output view:
                        outputContent.add(txt);
                    }
            );
        }catch(Exception e) {

        }

    }

    public int getPatientID() { return patientID; }
    public int getDoctorID() { return doctorID; }
    public int getFileID() { return fileID; }


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
