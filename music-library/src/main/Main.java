package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Controller controller = new Controller();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("albums_scene.fxml"));
        primaryStage.setTitle("Music library");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        controller.getConnection();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
