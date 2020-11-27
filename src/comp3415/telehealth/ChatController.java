package comp3415.telehealth;

import comp3415.telehealth.chat.client.ChatClient;
import comp3415.telehealth.chat.common.ChatIF;
import comp3415.telehealth.chat.server.EchoServer;
import comp3415.telehealth.db.AppInfo;
import comp3415.telehealth.db.LogInfo;
import comp3415.telehealth.db.GlobalUser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable, ChatIF {

    @FXML private TextField textInput;
    @FXML private ListView<String> textOutput;
    @FXML private Button sendButton;
    protected ObservableList<String> outputContent;

    // Chat objects
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

        // Bind the textOutput to whatever we put in the ObservableList outputContent:
        textOutput = new ListView<String>(outputContent);

        // Logout button onAction set to logoutUser() in dashboard.fxml

        // Ensure user is logged in, if not, redirect them to loginUser view:
        if (!GlobalUser.isLoggedIn())
            redirectToLogin();

        // Customize dashboard view based on User Type:
        if (GlobalUser.isDoctor())
            initDoctorChatService();
        else
            initPatientChatService();

    }

    public void initDoctorChatService()
    {
        // Customize gui

        // Start the chat server
    }

    public void initPatientChatService()
    {
        // Customize gui

        // Listen for the chat server, setting "loginUser id" to the patient's username
        try {
            patientChatClient = new ChatClient(AppInfo.CHAT_HOST, AppInfo.CHAT_PORT, this);
        } catch (Exception ex) {
            outputContent.add(ex.toString());
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
        if (!GlobalUser.isDoctor())
            sendPatientMsg();
    }

    public void sendPatientMsg()
    {
        try{
            patientChatClient.sendToServer(textInput.getText());
        } catch (Exception ex) {
            outputContent.add(ex.toString());
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

    }
}
