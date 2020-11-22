package comp3415.telehealth;

import comp3415.telehealth.db.login;
import comp3415.telehealth.view.replace;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // Variable names used here *must match* the fx:id set in the FXML file for that component.
    @FXML private Label welcomeText;
    @FXML private Button loginBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox userTypePicker;

    /**
     * Initializes the controller class
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        welcomeText.setText("Welcome to LU Telehealth!");

        userTypePicker.setTooltip(new Tooltip("Select the type of user you are."));
        userTypePicker.setItems(FXCollections.observableArrayList(
                "Patient",
                new Separator(),
                "Doctor")
        );

        // (onAction for the login/register buttons are set in the FXML view as an attribute)

    }

    /**
     * This method is called when the login button is pushed.
     */
    public void loginButtonPushed(ActionEvent event) throws IOException
    {
        // Required variables
        login log = new login();
        String userType = "Patient";
        Parent dashViewParent;

        try{
            // storing login information from user input:
            String usertype;
            String username = usernameField.getText();
            String password = new String(passwordField.getText());

            // User type selected:
            userType = userTypePicker.getValue().toString();
            // Set the appropriate dashboard based on user selection:
            if (userType.equals("Doctor"))
                dashViewParent = FXMLLoader.load(getClass().getResource("view/doctorDashboard.fxml"));
            else
                dashViewParent = FXMLLoader.load(getClass().getResource("view/patientDashboard.fxml"));

            // Login user, then show the dashboard.
            if(login.isLogin(username, password, userType)){
                Scene dashViewScene = new Scene(dashViewParent);

                // This line gets the Stage (window) info from the button being clicked
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                // Set the window to the new scene:
                window.setScene(dashViewScene);
                window.show();
            }
            else{ // if username password incorrect
                welcomeText.setText("Invalid credentials. Please try again.");
            }
        }
        catch(Exception e){
            welcomeText.setText("Login Error. Please try again.");
        }

    }
}
