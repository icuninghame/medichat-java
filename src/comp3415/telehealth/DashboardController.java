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
        if (!User.isLoggedIn())
            redirectToLogin();

        // Customize dashboard view based on User Type:
        if (User.isDoctor())
            initDoctorDashboard();
        else
            initPatientDashboard();

        welcomeLabel.setText("Welcome to LU TeleHealth. Choose from one of the options below.");
        bottomLabel.setText("Signed in as " + LogInfo.UName);

    }

    public void initDoctorDashboard()
    {
        startMeetingButton.setText("Start a Meeting");
        viewPatientButton.setText("View a Patient's File");
        newPatientButton.setText("Create a Patient's File");
        emergencyButton.setVisible(false);
    }

    public void initPatientDashboard()
    {
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
            // Gets the window
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

    public void redirectToChat(ActionEvent e){
        try {
            // Prepare the scene and stage:
            Parent chatViewParent = FXMLLoader.load(getClass().getResource("view/chat.fxml"));
            Scene chatViewScene = new Scene(chatViewParent);
            // Gets the window
            Stage window = LogInfo.window;
            window.setScene(chatViewScene);
            window.show();
        } catch (IOException ioe) {
            // Error loading view
        }
    }

    // Class to manipulate the dashboard

}
