package comp3415.telehealth;

import comp3415.telehealth.db.LogInfo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML private ChoiceBox userTypePicker;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        userTypePicker.setTooltip(new Tooltip("Select the type of user you are."));
        userTypePicker.setItems(FXCollections.observableArrayList(
                "Patient",
                new Separator(),
                "Doctor")
        );
        userTypePicker.setValue("Patient");

    }

    public void redirectToLogin()
    {
        try {
            // Prepare the scene and stage:
            Parent loginViewParent = FXMLLoader.load(getClass().getResource("view/welcome.fxml"));
            Scene loginViewScene = new Scene(loginViewParent);
            // Gets the window
            Stage window = LogInfo.window;
            window.setScene(loginViewScene);
            window.show();
        } catch (IOException ioe) {
            // Error loading view
        }
    }
}
