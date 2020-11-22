package comp3415.telehealth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Label welcomeText;
    @FXML private Button loginBtn;

    /**
     * Initializes the controller class
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        welcomeText.setText("Welcome to LU Telehealth!");

        // (onAction for the login button is set in the FXML view)
        //      loginBtn.setOnAction();

    }

    /**
     * When this method is called by a button press, it will change to the Home Scene
     */
    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent homeViewParent = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene homeViewScene = new Scene(homeViewParent);

        //This gets the information of the Stage the button was clicked on
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(homeViewScene);
        window.show();

    }
}
