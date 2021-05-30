package main.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import main.model.Album;
import main.model.Song;

import java.net.URL;
import java.util.ResourceBundle;

public class AlbumDetailsStage implements Initializable {
    static public Stage stage = new Stage();
    static public Integer albumId = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Album id "+albumId);
    }

//    public  ObservableList<Song> getSongsList(){
//
//    }

    private void showSongs(){

    }
}
