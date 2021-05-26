package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    //private Connection connection;
    Stage addAlbumStage = new Stage();

    @FXML private TableView<Album> tvAlbums;
    @FXML private TableColumn<Album,String> colAlbumTitle;
    @FXML private TableColumn<Album, Date> colPublicationDate;
    @FXML private TableColumn<Album, String> colPerformer;
    @FXML private TableColumn<Album, Integer> colReview;

    @Override
    public void initialize(URL url, ResourceBundle rb){
//        getConnection();
        showAlbums();
    }

//    public void getConnection(){
//        try {
//            connection = DriverManager.getConnection("jdbc:oracle:thin:@//192.168.0.180:1521/XEPDB1","hr","hr");
//            System.out.println("Connected to Oracle database server");
//        }catch (Exception ex){
//            System.out.println("Error: " + ex.getMessage());
//        }
//    }

    public ObservableList<Album> getAlbumsList(){
        ObservableList<Album> albumsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM albums";
        Statement st;
        ResultSet rs;
        try{
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            Album albums;
            while (rs.next()){
                albums = new Album(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getString(4),
                        rs.getInt(5)
                );
                albumsList.add(albums);
                System.out.println(albums.getTitle());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return albumsList;
    }

    public void showAlbums(){
        ObservableList<Album> list = getAlbumsList();
        colAlbumTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        colPerformer.setCellValueFactory(new PropertyValueFactory<>("performer"));
        colReview.setCellValueFactory(new PropertyValueFactory<>("review"));

        tvAlbums.setItems(list);
    }

    public void addAlbum() throws IOException {
        Pane addAlbumPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add_album_stage.fxml")));
        addAlbumStage.setScene(new Scene(addAlbumPane,600,400));
        addAlbumStage.show();
        AddAlbumStageController.addAlbumStage = addAlbumStage;
    }

    public void addReview(ActionEvent actionEvent) {
    }

    public void deleteAlbum(ActionEvent actionEvent) {
    }
}
