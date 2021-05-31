package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import main.model.Review;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    Stage addAlbumStage = new Stage();
    Stage albumDetailsStage =  new Stage();
    @FXML private TextField searchTextField;
    @FXML private Label userName;
    @FXML private TableView<Album> tvAlbums;
    @FXML private TableColumn<Album,String> colAlbumTitle;
    @FXML private TableColumn<Album, Date> colPublicationDate;
    @FXML private TableColumn<Album, String> colPerformer;
    @FXML private TableColumn<Album, Integer> colReview;

    static public String userNickName = "";

    private ObservableList<Album> albumsList = FXCollections.observableArrayList();
//    private ObservableList<Album> list;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        calculateReviews();
        showAlbums();
        setUserName();
        searchListener();

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
        albumsList = getAlbumsList();
        colAlbumTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPublicationDate.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        colPerformer.setCellValueFactory(new PropertyValueFactory<>("performer"));
        colReview.setCellValueFactory(new PropertyValueFactory<>("review"));
        tvAlbums.setItems(albumsList);
        searchListener();
    }

    public void addAlbum() throws IOException {
        Pane addAlbumPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../scenes/add_album_stage.fxml")));
        addAlbumStage.setScene(new Scene(addAlbumPane,560,350));
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

    public void showReviews(ActionEvent actionEvent) throws IOException {
        ReviewController.albumId = tvAlbums.getSelectionModel().getSelectedItem().getId();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../scenes/reviews_scene.fxml")));
        Stage reviewStage = new Stage();
        reviewStage.setTitle("Music library");
        reviewStage.setScene(new Scene(root, 800, 480));
        reviewStage.show();
    }

    public void deleteAlbum() {
        Integer deletedAlbumId = tvAlbums.getSelectionModel().getSelectedItem().getId();

        deleteSongs(deletedAlbumId);

        String query = "DELETE FROM albums WHERE albumid = " + deletedAlbumId ;
        Statement st;
        try{
            st = DatabaseConnector.getConnection().createStatement();
            st.executeQuery(query);
            showAlbums();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void deleteSongs(Integer albumId){
        String query = "DELETE FROM songs WHERE albumid = " + albumId ;
        Statement st;
        try{
            st = DatabaseConnector.getConnection().createStatement();
            st.executeQuery(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void calculateReviews(){
        albumsList = getAlbumsList();
        albumsList.forEach( (album) ->{
            String query = "SELECT rating FROM reviews WHERE albumid = " + album.getId();
            Statement st;
            ResultSet rs;
            try{
                st = DatabaseConnector.getConnection().createStatement();
                rs = st.executeQuery(query);
                ArrayList<Integer> reviewScores = new ArrayList<>();
                while (rs.next()){
                    System.out.println(rs.getInt(1));
                    reviewScores.add(rs.getInt(1));
                }
                System.out.println(reviewScores);
                if (reviewScores.size() != 0){
                    Integer finalReviewScore = 0;
                    for (Integer reviewScore: reviewScores) {
                        finalReviewScore+=reviewScore;
                    }
                    finalReviewScore =  finalReviewScore / reviewScores.size();
                    System.out.println(album.getTitle() + "Final review" + finalReviewScore);
                    updateAlbumReview(album.getId(),finalReviewScore);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }

    public void refreshTable() {
        calculateReviews();
        showAlbums();
    }

    private void updateAlbumReview(Integer albumId ,Integer newScore){
        String query = "UPDATE albums SET review =" + newScore + " WHERE albumid = " + albumId;
        Statement st;
        try{
            st = DatabaseConnector.getConnection().createStatement();
            st.executeQuery(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void openAlbumDetailsStage(Integer albumId) throws IOException {
        AlbumDetailsStageController.albumId = albumId;
        Pane addAlbumPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../scenes/album_details_stage.fxml")));
        albumDetailsStage.setScene(new Scene(addAlbumPane,800,480));
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

    public void searchListener(){
        FilteredList<Album> filteredData = new FilteredList<>(albumsList, b-> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(album -> {
                // if filter is empty, display all albums
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase(Locale.ROOT);
                if(album.getTitle().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1)
                {
                    return true;
                }else if (album.getPerformer().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(album.getPublicationDate().toString().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(album.getReview().toString().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else{
                    return false;
                }
            });
        });
        //Wrap the filtered list in a sortedlist
        SortedList<Album> sortedAlbums = new SortedList<>(filteredData);
        //Bind the sorted list comparator to the TableView comparator.
        sortedAlbums.comparatorProperty().bind(tvAlbums.comparatorProperty());
        // Add sorted and filtered data to the table
        tvAlbums.setItems(sortedAlbums);
    }

}
