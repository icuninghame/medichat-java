package comp3415.telehealth;

import comp3415.telehealth.db.LogInfo;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPatientFileController implements Initializable {


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
