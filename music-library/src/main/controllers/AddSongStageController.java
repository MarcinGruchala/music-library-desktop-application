package main.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddSongStageController {
    static public Stage addSongStage = new Stage();
    public Button btnAdd;
    public TextField tfTitle;
    public TextField tfNumberInAlbum;
    public TextField tfGenre;
    public TextField tfSongDuration;
    public Button btnCancel;

    public void confirmAdd(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
        addSongStage.close();
    }
}
