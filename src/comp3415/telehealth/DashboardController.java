package comp3415.telehealth;

import comp3415.telehealth.db.LogInfo;
import comp3415.telehealth.db.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label bottomLabel;
    @FXML private Button startMeetingButton;
    @FXML private Button viewPatientButton;
    @FXML private Button newPatientButton;
    @FXML private Button emergencyButton;

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

        // Logout button onAction set to logoutUser() in dashboard.fxml

        // Ensure user is logged in, if not, redirect them to login view:
        if (!LogInfo.isLoggedIn)
            redirectToLogin();

        // Customize dashboard view based on User Type:
        if (User.isDoctor())
            initDoctorDashboard();
        else
            initPatientDashboard();

    }

    public void initDoctorDashboard()
    {
        welcomeLabel.setText("Welcome, Doctor. Choose from one of the options below.");
        bottomLabel.setText("Welcome, Doctor. Signed in as " + LogInfo.UName);
        startMeetingButton.setText("Start a Meeting");
        viewPatientButton.setText("View a Patient's File");
        newPatientButton.setText("Create a New Patient's File");
        emergencyButton.setVisible(false);
    }

    public void initPatientDashboard()
    {
        welcomeLabel.setText("Welcome to LU TeleHealth. Choose from one of the options below.");
        bottomLabel.setText("Welcome, " + LogInfo.UName);
        startMeetingButton.setText("Join a Meeting");
        viewPatientButton.setText("View your File");
        newPatientButton.setText("Register new File");
        emergencyButton.setVisible(true);
    }

    public void redirectToLogin()
    {
        try {
            // Prepare the scene and stage:
            Parent loginViewParent = FXMLLoader.load(getClass().getResource("view/welcome.fxml"));
            Scene loginViewScene = new Scene(loginViewParent);
            // Gets the window from the event that called this method
            Stage window = LogInfo.window;
            window.setScene(loginViewScene);
            window.show();
        } catch (IOException ioe) {
            // Error loading view
        }
    }

    public void logoutUser(ActionEvent e){
        // Logout the user, then redirect to login screen
        User.logOut();
        redirectToLogin();
    }

    // Class to manipulate the doctor dashboard view

}
