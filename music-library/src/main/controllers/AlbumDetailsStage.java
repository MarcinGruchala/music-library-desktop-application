package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.model.DatabaseConnector;
import main.model.Song;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AlbumDetailsStage implements Initializable {
    static public Stage stage = new Stage();
    static public String albumName = "";
    public TableColumn<Song, String> colGenre;
    public TableColumn<Song, String> colSongTitle;
    public TableView<Song> tvSongs;
    public TableColumn<Song, String> colAlbumTitle;
    public TableColumn<Song, Integer> colNumberInAlbum;
    public TableColumn<Song, Integer> colSongDuration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Album id "+albumName);
        showSongs();
    }

    public  ObservableList<Song> getSongsList(){
        ObservableList<Song> songsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM songs WHERE albumtitle =  '" + albumName  + "' " ;
        Statement st;
        ResultSet rs;
        try {
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            Song song;
            while (rs.next()){
                song = new Song(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
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

    private void showSongs(){
        ObservableList<Song> songList = getSongsList();
        colSongTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAlbumTitle.setCellValueFactory(new PropertyValueFactory<>("albumTitle"));
        colNumberInAlbum.setCellValueFactory(new PropertyValueFactory<>("numberInAlbum"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colSongDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tvSongs.setItems(songList);
    }
}
