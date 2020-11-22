package comp3415.telehealth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // The beginning "View" specified in FXML
        Parent root = FXMLLoader.load(getClass().getResource("view/welcome.fxml"));

        // The beginning scene containing the login view
        Scene loginScene = new Scene(root, 500, 400);

        // Set the stage (window) properties and show it:
        primaryStage.setTitle("COMP3415 Group Project");
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
