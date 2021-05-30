package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import main.model.DatabaseConnector;


import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {

    @FXML
    private ImageView registerImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File registerFile = new File("music-library/Images/register.png");
        Image registerImage = new Image(registerFile.toURI().toString());
        registerImageView.setImage(registerImage);
    }

    public void registerButtonOnAction(ActionEvent event){
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            confirmPasswordLabel.setText("");
        }else{
            confirmPasswordLabel.setText("Password does not match!");
        }
    }

    public void closeButtononAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void registerUser(){
        Connection connectDB = DatabaseConnector.getConnection();
        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();

        String insertFields = "INSERT INTO USER_ACCOUNTS(FirstName,LastName,Username,Password) VALUES ('";
        String insertValues = firstname + "','" + lastname+ "','" + username+ "','" + password+ "')" ;
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User has been registered successfully!");

        }catch (Exception ex){
            ex.printStackTrace();
            ex.getCause();
        }
    }

}
