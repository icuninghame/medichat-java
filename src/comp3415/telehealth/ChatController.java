package comp3415.telehealth;

import comp3415.telehealth.chat.client.ChatClient;
import comp3415.telehealth.chat.common.ChatIF;
import comp3415.telehealth.chat.server.EchoServer;
import comp3415.telehealth.db.AppInfo;
import comp3415.telehealth.db.LogInfo;
import comp3415.telehealth.db.GlobalUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatController implements Initializable, ChatIF {

    @FXML private TextField textInput;
    @FXML private ListView<Text> textOutput;
    @FXML private Button sendButton;
    @FXML private Label bottomLabel;

    // Chat objects
    String chatName;
    ObservableList<Text> outputContent = FXCollections.observableArrayList();
    ChatClient patientChatClient;
    EchoServer doctorChatServer;

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
        // if the user is not logged in, set their name to "Guest", otherwise set it to their display name:
        if (!LogInfo.isLoggedIn) chatName = "GUEST";
        else chatName = LogInfo.uname;

        // Bind the textOutput to whatever we put in the ObservableList outputContent:
        textOutput.setItems(outputContent);

        //clear the bottom label
        bottomLabel.setText("");

        // Customize dashboard view based on User Type:
        if (GlobalUser.isDoctor())
            initDoctorChatService();
        else
            initPatientChatService();

        // (Logout button onAction set to logoutUser() in dashboard.fxml)

    }

    public void initDoctorChatService()
    {
        // Customize gui

        // Start the chat server
        try {
            doctorChatServer = new EchoServer(AppInfo.CHAT_PORT, this, LogInfo.uID);
            doctorChatServer.listen(); //Start listening for connections
        }catch (Exception ex){
            bottomLabel.setText(ex.toString());
        }


    }

    public void initPatientChatService()
    {
        // Give welcome message:
        outputContent.add(new Text("Welcome to MediChat!"));
        outputContent.add(new Text("Start messaging by typing below. Press ENTER to send."));

        // Listen for the chat server, setting "loginUser id" to the patient's username
        try {
            patientChatClient = new ChatClient(AppInfo.CHAT_HOST, AppInfo.CHAT_PORT, this, LogInfo.uID);
            patientChatClient.sendToServer("#login " + chatName);
        } catch (Exception ex) {
            bottomLabel.setText(ex.toString());
        }
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

    // Chat functionality:

    public void send(ActionEvent e){
        sendPatientMsg(textInput.getText());
    }

    public void sendPatientMsg(String message)
    {
        try{
            patientChatClient.handleMessageFromClientUI(message);
        } catch (Exception ex) {
            bottomLabel.setText(ex.toString());
        }
    }

    /**
     * Method that when overriden is used to display objects onto
     * a UI.
     *
     * @param message
     */
    @Override
    public void display(String message) {

        // "Lambda expression" to run in a new Thread to avoid JavaFX throwing an IllegalStateException
        Platform.runLater(
                () -> {
                    // Cast the incoming message to "Text"
                    Text msg = new Text (message);
                    // Sets the text wrapping to fit the window:
                    msg.setWrappingWidth(textOutput.getWidth());
                    // add the message to the output view:
                    outputContent.add(msg);
                }
        );

    }
}
