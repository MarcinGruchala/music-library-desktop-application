package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DatabaseConnector.setConnection();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("albums_scene.fxml")));
        primaryStage.setTitle("Music library");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
