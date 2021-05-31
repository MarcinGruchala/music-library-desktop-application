package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.model.Album;
import main.model.DatabaseConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    Stage addAlbumStage = new Stage();
    Stage albumDetailsStage =  new Stage();

    @FXML private Label userName;
    @FXML private TableView<Album> tvAlbums;
    @FXML private TableColumn<Album,String> colAlbumTitle;
    @FXML private TableColumn<Album, Date> colPublicationDate;
    @FXML private TableColumn<Album, String> colPerformer;
    @FXML private TableColumn<Album, Integer> colReview;

    static public String userNickName = "";

    @Override
    public void initialize(URL url, ResourceBundle rb){
        showAlbums();
        setUserName();
        tvAlbums.setRowFactory( tv -> {
            TableRow<Album> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Album rowData = row.getItem();
                    System.out.println(rowData);
                    try {
                        openAlbumDetailsStage(rowData.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

    }



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
        Pane addAlbumPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../scenes/add_album_stage.fxml")));
        addAlbumStage.setScene(new Scene(addAlbumPane,600,400));
        addAlbumStage.show();
        AddAlbumStageController.addAlbumStage = addAlbumStage;
    }

    public void addReview() throws IOException {
        try{
            AddReviewStageController.albumID = tvAlbums.getSelectionModel().getSelectedItem().getId();
            AddReviewStageController.userID = 1;

            String query = "SELECT ACCOUNTID FROM USER_ACCOUNTS WHERE USERNAME='" + userNickName + "'";
            Connection connectDB = DatabaseConnector.getConnection();
            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(query);
                while (queryResult.next()) {
                    Integer accountID = queryResult.getInt(1);
                    AddReviewStageController.userID = accountID;
                }
            }catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }


            //System.out.println("albumID =" + AddReviewStageController.albumID + "  userID=" + AddReviewStageController.userID);

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../scenes/add_review_stage.fxml")));
            Stage reviewStage = new Stage();
            reviewStage.setTitle("Music library");
            reviewStage.setScene(new Scene(root, 560, 350));
            reviewStage.show();
        }catch (Exception ex){
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void showReviews(ActionEvent actionEvent) {
    }

    public void deleteAlbum() {
    }

    public void refreshTable() {
        showAlbums();
    }

    private void openAlbumDetailsStage(Integer albumId) throws IOException {
        AlbumDetailsStageController.albumId = albumId;
        Pane addAlbumPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../scenes/album_details_stage.fxml")));
        albumDetailsStage.setScene(new Scene(addAlbumPane,600,400));
        albumDetailsStage.show();
        AlbumDetailsStageController.stage = albumDetailsStage;
    }
    public void setUserName(){
        String query = "SELECT FIRSTNAME, LASTNAME FROM USER_ACCOUNTS WHERE USERNAME='" + userNickName + "'";
        Connection connectDB = DatabaseConnector.getConnection();
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);
            while (queryResult.next()) {
                String firstName = queryResult.getString(1);
                String lastName = queryResult.getString(2);
                String fullName = firstName + " "+ lastName;
                userName.setText(fullName.toUpperCase(Locale.ROOT));
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }


    }


}
