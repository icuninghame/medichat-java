package comp3415.telehealth;

import comp3415.telehealth.model.PatientFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchForPatientFileController extends Controller implements Initializable {

    @FXML TextField patientIDField;
    @FXML Button submitButton;

    @FXML
    void searchForFile(ActionEvent event) {
        try {
            searchForFile(Integer.parseInt(patientIDField.getText()));
            redirectToSearchResults();
        } catch (IOException ex) {
            System.exit(0);
        }
    }

    public int patientID = 0;
    public int fileID;
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



    }

    public void searchForFile(int patientID) {
        this.patientID = Integer.parseInt(patientIDField.getText());
        ArrayList allPatientFiles = PatientFile.getAllFiles();
        ArrayList patientFiles = new ArrayList<PatientFile>();


        for (int i=0; i < allPatientFiles.size(); i++) {
            if (PatientFile.getFile(i).getPatientID() == this.patientID) {
                patientFiles.add(new PatientFile(
                        PatientFile.getFile(i).getID(),
                        PatientFile.getFile(i).getDoctorID(),
                        PatientFile.getFile(i).getMedicalInfo(),
                        PatientFile.getFile(i).getMedication(),
                        PatientFile.getFile(i).getVerified(),
                        PatientFile.getFile(i).getFileURL()));
                fileID = PatientFile.getFile(i).getID();
            }
        }

        try {
            redirectToSearchResults();
        }catch (IOException ignored){
        }

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
