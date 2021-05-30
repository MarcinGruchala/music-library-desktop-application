package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.model.DatabaseConnector;
import main.model.Song;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class AlbumDetailsStageController implements Initializable {
    static public Stage stage = new Stage();
    Stage addSongStage = new Stage();

    static public Integer albumId = 0;
    public TableColumn<Song, String> colGenre;
    public TableColumn<Song, String> colSongTitle;
    public TableView<Song> tvSongs;
    public TableColumn<Song, String> colAlbumTitle;
    public TableColumn<Song, Integer> colNumberInAlbum;
    public TableColumn<Song, Integer> colSongDuration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Album id "+albumId);
        showSongs();
    }

    public  ObservableList<Song> getSongsList(){
        ObservableList<Song> songsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM songs WHERE albumId =  " + albumId  + " " ;
        Statement st;
        ResultSet rs;
        try {
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            Song song;
            System.out.println(getAlbumTitle(albumId));
            while (rs.next()){
                song = new Song(
                        rs.getInt(1),
                        rs.getString(2),
                        getAlbumTitle(albumId),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getInt(6)
                );
                songsList.add(song);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return songsList;

    }

    private String getAlbumTitle(Integer albumId){
        String albumTitle = "";
        String query = "SELECT albumtitle FROM albums WHERE albumId =  " + albumId  + " " ;
        Statement st;
        ResultSet rs;
        try {
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            rs.next();
            albumTitle = rs.getString(1);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return albumTitle;
    }

    private void showSongs(){
        ObservableList<Song> songList = getSongsList();
        colSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAlbumTitle.setCellValueFactory(new PropertyValueFactory<>("albumTitle"));
        colNumberInAlbum.setCellValueFactory(new PropertyValueFactory<>("numberInAlbum"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colSongDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tvSongs.setItems(songList);
    }

    public void addSong() throws IOException {
        Pane addAlbumPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../scenes/add_song_stage.fxml")));
        addSongStage.setScene(new Scene(addAlbumPane,600,400));
        addSongStage.show();
        AddSongStageController.addSongStage = addSongStage;
    }

    public void refreshTable() {
        showSongs();
    }
}
