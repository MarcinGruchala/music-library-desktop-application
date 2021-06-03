package main.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.DatabaseConnector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddSongStageController {
    static public Stage addSongStage = new Stage();
    public Button btnAdd;
    public TextField tfTitle;
    public TextField tfNumberInAlbum;
    public TextField tfGenre;
    public TextField tfSongDuration;
    public Button btnCancel;

    public void confirmAdd(ActionEvent actionEvent) {
        String title = tfTitle.getText();
        int numberInAlbum =  Integer.parseInt(tfNumberInAlbum.getText());
        String genre = tfGenre.getText();
        int songDuration =  Integer.parseInt(tfSongDuration.getText());
        String query = "INSERT INTO Songs VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps =  DatabaseConnector.getConnection().prepareStatement(query);
            ps.setInt(1,getNewId());
            ps.setString(2,title);
            ps.setInt(3,AlbumDetailsStageController.albumId);
            ps.setInt(4,numberInAlbum);
            ps.setString(5,genre);
            ps.setInt(6,songDuration);
            ps.execute();
            ps.close();
            addSongStage.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        addSongStage.close();
    }

    private Integer getNewId(){
        int newId;
        String query = "SELECT MAX(songid) FROM songs";
        Statement st;
        ResultSet rs;
        try {
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            rs.next();
            newId = rs.getInt(1);
            return newId+1;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }
}
