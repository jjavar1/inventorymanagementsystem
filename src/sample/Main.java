package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Julian Javaruski
 */

/**
 * The way this project could be improved is through the use of back end data management such as SQL.
 * When the project is closed all saved data from the lists are deleted and reset upon re-opening.
 * Another improvement could be the use of a more in-depth css stylesheet and UX improvements in
 * general.
 */

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    //start method
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/sample.fxml"));
        primaryStage.setTitle("Main");
        primaryStage.setScene(new Scene(root, 970, 430));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
