package comp3415.telehealth;

import comp3415.telehealth.db.Logout;
import comp3415.telehealth.db.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorDashboardController implements Initializable {


    @FXML
    private Label bottomLabel;

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

        // Logout button onAction set to logoutUser() in doctorDashboard.fxml

    }

    public void logoutUser(ActionEvent e){
        Parent loginViewParent;

        // Logout the user, then redirect to login screen
        User.logOut();

        try {
            // Prepare the scene and stage:
            loginViewParent = FXMLLoader.load(getClass().getResource("view/welcome.fxml"));
            Scene loginViewScene = new Scene(loginViewParent);
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            window.setScene(loginViewScene);
            window.show();
        } catch (IOException ioException) {
            // Error loading view
        }

    }

    // Class to manipulate the doctor dashboard view

}
