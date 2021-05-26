package main;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AddAlbumStageController {

    static Stage addAlbumStage = new Stage();


    public void confirmAdd(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
        addAlbumStage.close();
    }
}
