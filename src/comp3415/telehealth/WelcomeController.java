package comp3415.telehealth;

import comp3415.telehealth.db.LogInfo;
import comp3415.telehealth.db.Login;
import comp3415.telehealth.db.User;
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

public class WelcomeController implements Initializable {

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

        // (Initial attributes set in the welcome.fxml file)

        welcomeText.setText("Welcome to LU Telehealth! \n" + "Please log in or register below.");
        userTypePicker.setTooltip(new Tooltip("Select the type of user you are."));
        userTypePicker.setItems(FXCollections.observableArrayList(
                "Patient",
                new Separator(),
                "Doctor")
        );
        userTypePicker.setValue("Patient");

    }


    /**
     * This method is called when the login button is pushed.
     * Handles the user's log in request.
     * @param event the (Mouse)Event associated with this method call
     */
    public void loginUser(ActionEvent event) {
        // Required variables
        Login log = new Login();
        String userType = "Patient";
        Parent dashViewParent;

        try{
            // login information from user input:
            String username = usernameField.getText();
            String password = passwordField.getText();
            userType = userTypePicker.getValue().toString();

            // Login user, then show the dashboard.
            if(User.isLogin(username, password, userType)){
                // Change the user's login status:
                User.logIn();

                // Prepare the scene and stage:
                dashViewParent = FXMLLoader.load(getClass().getResource("view/dashboard.fxml"));
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
            welcomeText.setText(e.toString());
        }

    }


    /**
     * This method is called when the register button is pushed.
     * Handles the user's registration request.
     * @param event the (Mouse)Event associated with this method call
     */
    public void registerUser(ActionEvent event) throws IOException
    {

    }

}
