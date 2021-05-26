package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("albums_scene.fxml")));
        primaryStage.setTitle("Music library");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

//        Stage addAlbumStage = new Stage();
//        Pane addAlbumPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add_album_stage.fxml")));
//        addAlbumStage.setScene(new Scene(addAlbumPane,300,275));
//        addAlbumStage.show();


//        MainController controller = new MainController();
//        controller.initialize();
//        controller.getConnection();
//        controller.showAlbums();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
