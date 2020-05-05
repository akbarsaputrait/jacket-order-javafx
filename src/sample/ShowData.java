package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;;

public class ShowData {
    public ShowData(String nim, String name, String kelas, String size) {
        GridPane newGrid = new GridPane();
        newGrid.setVgap(10);
        newGrid.setHgap(10);
        newGrid.setPadding(new Insets(10, 10, 10, 10));
        newGrid.setAlignment(Pos.CENTER);
        newGrid.setMinHeight(300);

        Scene newScene = new Scene(newGrid,300,300);
        newScene.getStylesheets().add("sample/styles.css");

        Stage newStage = new Stage();
        newStage.setTitle("Data Diri");
        newStage.setScene(newScene);

        Text newSceneTitle = new Text("Data Diri Pemesan");
        newSceneTitle.getStyleClass().add("scene-title");
        newGrid.add(newSceneTitle, 0, 0, 2, 1);

        Text textNim = new Text("NIM: " + nim);
        newGrid.add(textNim, 0,1);

        Text textName = new Text("Nama: " + name);
        newGrid.add(textName, 0,2);

        Text textKelas = new Text("Kelas: " + kelas);
        newGrid.add(textKelas, 0,3);

        Text textSize = new Text("Ukuran Jaket: " + size);
        newGrid.add(textSize, 0,4);

        Button buttonBack = new Button("Kembali");
        buttonBack.setOnAction((event) -> {
            newStage.close();
        });
        newGrid.add(buttonBack,0,5);

        newStage.show();
    }
}
