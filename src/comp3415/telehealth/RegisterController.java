package comp3415.telehealth;

import comp3415.telehealth.db.LogInfo;
import comp3415.telehealth.db.MySQLConnections;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import java.util.Random; // Random Number Generation

public class RegisterController implements Initializable {

    @FXML private ChoiceBox userTypePicker;

    @FXML private TextField nameField;

    @FXML private TextField usernameField;

    @FXML private PasswordField passwordField;

    @FXML private Label outputText;

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

    public void register()
    {
        try{
            Random rand = new Random(); //instance of random class
            int userID = rand.nextInt(100); // generate a random number from 0-10
            Connection sqlConnection = MySQLConnections.getConnection();                                    // connecting to database
            print("connected");


            String query = "INSERT INTO login(UserID,UName,UType,username,password) VALUES("
                    + userID + ",\""+ nameField.toString() + "\",\"" + userTypePicker.getTypeSelector() + "\",\"" + usernameField.toString()
                    + "\",\"" + passwordField.toString() +"\")";
            print("query made");


            Statement stmt = sqlConnection.prepareStatement("INSERT INTO login(UserID,UName,UType,username,password)" + " VALUES("
                    + userID + ",\""+ nameField.toString() + "\",\"" + userTypePicker.getTypeSelector() + "\",\"" + usernameField.toString()
                    + "\",\"" + passwordField.toString() +"\")");
            print("query prep done");


            stmt.execute(query); // fails here
            print("query executed");

            print(query);

            //outputText.setText("Register Success");

        }
        catch(Exception e){ // error while connecting to database
            //outputText.setText("Register Failed");
            print("failed");
        }

    }

    public static void print(String s) {
        System.out.println(s);
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
