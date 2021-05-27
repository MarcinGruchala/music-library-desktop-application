package main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField enterPasswordField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("music-library/Images/gitara.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("music-library/Images/88-887400_cyber-security-security-lock-lock-icon-lock-image.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);

    }


    public void loginButtonOnAction(ActionEvent event){
        if (usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false)
        {
            validateLogin();
        }else{
            loginMessageLabel.setText("Please enter username and password.");
        }
    }
    public void cancelButtonOnAction(ActionEvent event){
        Stage  stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void validateLogin(){
        Connection connectDB = DatabaseConnector.getConnection();

        String verifyLogin = "SELECT count(1) FROM USER_ACCOUNTS WHERE Username = '" + usernameTextField.getText() + "' AND password = '" + enterPasswordField.getText() +"'";
        System.out.print(verifyLogin+"\n");
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                System.out.print(queryResult.getInt(1));
                if(queryResult.getInt(1) == 1){
                    loginMessageLabel.setText("LOGIN SUCCESFULL.");
                } else
                {
                    loginMessageLabel.setText("Invalid login. Please try again.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }
}

