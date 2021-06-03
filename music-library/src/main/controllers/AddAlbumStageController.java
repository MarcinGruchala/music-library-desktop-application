package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.Album;
import main.model.DatabaseConnector;

import javax.swing.text.StyledEditorKit;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AddAlbumStageController {

    static public Stage addAlbumStage = new Stage();
    public TextField tfPublicationDate;
    public TextField tfPerformer;
    public TextField tfReview;
    public Button btnCancel;
    public TextField tfTitle;
    public Button btnAdd;


    public void confirmAdd()  {
        String title = tfTitle.getText();
        String date = tfPublicationDate.getText();
        String performer = tfPerformer.getText();
        String query = "INSERT INTO Albums VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps =  DatabaseConnector.getConnection().prepareStatement(query);
            ps.setInt(1,getNewId());
            ps.setString(2,title);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4,performer);
            ps.setInt(5,0);
            ps.execute();
            ps.close();
            System.out.println(isPerformer());
            addAlbumStage.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cancel() {
        addAlbumStage.close();
    }

    private Integer getNewId(){
        int newId;
        String query = "SELECT MAX(albumid) FROM albums";
        Statement st;
        ResultSet rs;
        try {
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            System.out.println(rs);
            rs.next();
            newId = rs.getInt(1);
            System.out.println(newId+1);
            return newId+1;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    private Boolean isPerformer(){
        String query = "SELECT bandId FROM bands WHERE bandname = '" + tfPerformer.getText() + "'";
        Statement st;
        ResultSet rs;
        ArrayList<Integer> bandsId= new ArrayList<>();
        try{
            st = DatabaseConnector.getConnection().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                bandsId.add(rs.getInt(1));
            }
            System.out.println("Bands: " + bandsId.size());
            if (bandsId.size()!=0){
                return true;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  false;
    }
}
