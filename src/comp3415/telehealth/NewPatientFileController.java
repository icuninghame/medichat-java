package comp3415.telehealth;

import comp3415.telehealth.db.GlobalUser;
import comp3415.telehealth.db.LogInfo;
import comp3415.telehealth.db.MySQLConnections;
import comp3415.telehealth.model.PatientFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class NewPatientFileController implements Initializable {

    // Components from newpatientfile.fxml:
    @FXML TextField patientIDField;
    @FXML TextField doctorIDField;
    @FXML TextArea medicalInfoField;
    @FXML TextArea medicationField;
    @FXML Label statusText;

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

        // Redirect the user if they are not authorized:
        if (!GlobalUser.isDoctor())
            redirectToDashboard();

        // Set the bottom status text to invisible until we need it
        statusText.setVisible(false);

    }

    /**
     * Click listener for back button press
     * @param e the action event that led to this method call
     */
    public void backButtonPressed(ActionEvent e){
        redirectToDashboard();
    }

    /**
     * Called when the submit button is pressed.
     * @param e the action event that led to this method call
     */
    public void submitForm(ActionEvent e)
    {
        statusText.setText("Uploading your file...");
        statusText.setVisible(true);

        // Get form entries:
        String patientID_s = patientIDField.getText();
        String doctorID_s = doctorIDField.getText();
        String medicalInfo_s = medicalInfoField.getText();
        String medications_s = medicationField.getText();

        // Abort if the form input is invalid:
        if (!validateFormInput(patientID_s, doctorID_s, medicalInfo_s, medications_s))
            return;

        // Insert the file into the database:
        if (PatientFile.insertFile(Integer.parseInt(patientID_s), Integer.parseInt(doctorID_s), null, medicalInfo_s, medications_s, false))
            statusText.setText("Uploaded successfully!");
        else
            statusText.setText("Problem uploading your information. Please try again.");

    }

    /**
     * Returns true if the form input is valid, false otherwise.
     * @param pid_s the raw string of the patient id field
     * @param did_s the raw string of the doctor id field
     * @param medInfo_s the raw string of the medical info field
     * @param meds_s the raw string of the medication field
     * @return
     */
    public boolean validateFormInput(String pid_s, String did_s, String medInfo_s, String meds_s)
    {
        // If input passes all the following tests, the function will return true:
        try{
            int i = Integer.parseInt(pid_s);
            int j = Integer.parseInt(did_s);
        }catch (Exception e){
            // Error parsing the integers
            return false;
        }
        return true;
    }

    public void redirectToDashboard()
    {
        try {
            // Prepare the scene and stage:
            Parent dashViewParent = FXMLLoader.load(getClass().getResource("view/dashboard.fxml"));
            Scene dashViewScene = new Scene(dashViewParent);
            // Gets the window
            Stage window = LogInfo.window;
            window.setScene(dashViewScene);
            window.show();
        } catch (IOException ioe) {
            // Error loading the dashboard
        }
    }

}
