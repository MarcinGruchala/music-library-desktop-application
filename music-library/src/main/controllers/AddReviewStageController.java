package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.model.DatabaseConnector;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class AddReviewStageController implements Initializable {
    static public Stage addReviewStage = new Stage();
    static public Integer albumID = null;
    static public Integer userID = null;


    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<Integer> ratingChoiceBox;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label infoLabel;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle event){
        loadDataToChoiceBox();
    }

    public void addButtonOnAction(ActionEvent actionEvent) {
        if(descriptionTextArea.getText().toString() == "")
        {
            errorLabel.setText("You must fill the Description box!");
        }else
        {
            if(ratingChoiceBox.getValue() == null)
            {
                errorLabel.setText("You must fill the Rating!");
            }else
            {
                String query = "INSERT INTO reviews(reviewcontent,userid,rating,albumid) VALUES('";
                String query2 = descriptionTextArea.getText() + "'," + userID +"," +  ratingChoiceBox.getValue() + "," +albumID +")";
                String finalQuery = query+query2;

                Connection connectDB = DatabaseConnector.getConnection();
                try {
                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(finalQuery);
                    errorLabel.setText("");
                    infoLabel.setText("Review has been updated successfully!");
                    Stage stage = (Stage) cancelButton.getScene().getWindow();
                    stage.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                    ex.getCause();
                }
            }
        }
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void loadDataToChoiceBox(){
        list.removeAll(list);
        list.addAll(1,2,3,4,5,6,7,8,9,10);
        ratingChoiceBox.getItems().addAll(list);
    }
}
