package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import main.model.Album;
import main.model.Review;
import main.model.DatabaseConnector;
import main.model.Song;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReviewController  implements Initializable {

    static public Integer albumId = 0;
    static public Integer userID = 0;

    public TableColumn<Review, String> colReview;
    public TableColumn<Review, String> colAlbumTitle;
    public TableView<Review> tvReviews;
    public TableColumn<Review, String> colRating;
    public TableColumn<Review, Integer> colUserName;
    public TextField searchTextField;

    private ObservableList<Review> reviewsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showReviews();

        FilteredList<Review> filteredData = new FilteredList<>(reviewsList, b-> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(review -> {
                // if filter is empty, display all albums
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase(Locale.ROOT);
                if(review.getReview().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1)
                {
                    return true;
                }else if (review.getUserID().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(review.getRating().toString().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(review.getAlbumID().toLowerCase(Locale.ROOT).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else{
                    return false;
                }
            });
        });
        //Wrap the filtered list in a sortedlist
        SortedList<Review> sortedReviews = new SortedList<>(filteredData);
        //Bind the sorted list comparator to the TableView comparator.
        sortedReviews.comparatorProperty().bind(tvReviews.comparatorProperty());
        // Add sorted and filtered data to the table
        tvReviews.setItems(sortedReviews);

    }

    public  ObservableList<Review> getReviewsList(){
        ObservableList<Review> reviewList = FXCollections.observableArrayList();
        String query = "SELECT * FROM reviews WHERE albumId =  " + albumId  + " ";
        System.out.println(query);
        Statement st;
        ResultSet rs;
        try {
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            Review review;
            while (rs.next()){
                review = new Review(
                        rs.getInt(1),
                        rs.getString(2),
                        getUserName(rs.getInt(3)),
                        rs.getInt(4),
                        getAlbumTitle(albumId)
                );
                reviewList.add(review);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return reviewList;

    }

    private void showReviews(){
        reviewsList = getReviewsList();
        colReview.setCellValueFactory(new PropertyValueFactory<>("review"));
        colAlbumTitle.setCellValueFactory(new PropertyValueFactory<>("albumID"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userID"));
        tvReviews.setItems(reviewsList);
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

    private String getUserName(Integer userID){
        String userName = "";
        String query = "SELECT username FROM user_accounts WHERE accountid =  " + userID  + " " ;
        Statement st;
        ResultSet rs;
        try {
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            rs.next();
            userName = rs.getString(1);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return userName;
    }

}
