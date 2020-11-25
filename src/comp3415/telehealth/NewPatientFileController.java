package comp3415.telehealth;

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

        // Initialize the new patient file form view

        // Set the bottom status text to invisible until we need it
        statusText.setVisible(false);

    }

    /**
     * Called when the submit button is pressed.
     * @param e the action event that led to this method call
     */
    public void submitForm(ActionEvent e)
    {
        statusText.setText("Uploading your file...");
        statusText.setVisible(true);

        // get the form entries:
        int patientID = Integer.parseInt(patientIDField.getText());
        int doctorID = Integer.parseInt(doctorIDField.getText());
        String medicalInfo = medicalInfoField.getText();
        String medications = medicationField.getText();

        // some crappy validation before inserting the file:
        if (patientID != 0 && doctorID != 0 && medicalInfo != null && medications != null)
            if (PatientFile.insertFile(patientID, doctorID, medicalInfo, medications))
                statusText.setText("Uploaded successfully!");
            else
                statusText.setText("Problem uploading your information. Please try again.");
        else
            statusText.setText("Can't submit. Please enter valid information.");
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
            // Error loading view
        }
    }

}
