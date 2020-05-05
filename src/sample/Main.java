package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // DEFINE GRID
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setAlignment(Pos.CENTER);

        // DEFINE SCENE
        Scene scene = new Scene(grid, 350, 350);
        scene.getStylesheets().add("sample/styles.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pemesanan Jaket");

        // SCENE TITLE
        Text sceneTitle = new Text("Isi Data Pemesanan Jaket");
        sceneTitle.getStyleClass().add("scene-title");
        grid.add(sceneTitle, 0, 0, 2, 1);

        // TEXTFIELD NIM
        Label labelNim = new Label("NIM:");
        grid.add(labelNim, 0, 1);

        TextField fieldNim = new TextField();
        // INPUT CAN ONLY NUMBER
        fieldNim.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fieldNim.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        grid.add(fieldNim, 1, 1);

        // TEXTFIELD NAME
        Label labelName = new Label("Nama:");
        grid.add(labelName, 0, 2);

        TextField fieldName = new TextField();
        grid.add(fieldName, 1, 2);

        // TEXTFIELD CLASS
        Label labelClass = new Label("Kelas:");
        grid.add(labelClass, 0, 3);

        TextField fieldClass = new TextField();
        grid.add(fieldClass, 1, 3);

        // RADIO BUTTON SIZE
        Label labelSize = new Label("Ukuran Jaket:");
        grid.add(labelSize, 0, 4);

        RadioButton small = new RadioButton("S");
        RadioButton medium = new RadioButton("M");
        RadioButton large = new RadioButton("L");
        RadioButton extraLage = new RadioButton("XL");

        ToggleGroup radioSize = new ToggleGroup();
        small.setToggleGroup(radioSize);
        medium.setToggleGroup(radioSize);
        large.setToggleGroup(radioSize);
        extraLage.setToggleGroup(radioSize);

        HBox boxRadio = new HBox(10, small, medium, large, extraLage);
        grid.add(boxRadio, 1, 4);

        // BUTTON ACTION
        Button btnNew = new Button("Tambah");
        Button btnEdit = new Button("Edit");
        Button btnDelete = new Button("Hapus");
        btnDelete.getStyleClass().add("button-danger");
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);

        HBox boxButton = new HBox(10, btnNew, btnEdit, btnDelete);
        grid.add(boxButton, 1, 5);

        // ACTION NEW DATA
        btnNew.setOnAction((actionEvent) -> {
            try {
                // VALIDATION
                if (!textIsEmpty(fieldNim) && !textIsEmpty(fieldName) && !textIsEmpty(fieldClass)) {
                    if (radioSize.getSelectedToggle() == null) {
                        Alert alertSize = new Alert(Alert.AlertType.WARNING, "Anda harus memilih ukuran jaket", ButtonType.OK);
                        alertSize.show();
                    } else {
                        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "Apakah data anda sudah benar?", ButtonType.YES, ButtonType.NO);
                        alertConfirm.showAndWait();

                        if (alertConfirm.getResult() == ButtonType.YES) {
                            fieldNim.setDisable(true);
                            fieldName.setDisable(true);
                            fieldClass.setDisable(true);
                            radioSize.getToggles().forEach(toggle -> {
                                Node node = (Node) toggle;
                                node.setDisable(true);
                            });

                            btnDelete.setDisable(false);
                            btnEdit.setDisable(false);
                            btnNew.setDisable(true);

                            Alert alertSuccess = new Alert(Alert.AlertType.NONE, "Data berhasil disimpan", ButtonType.OK);
                            alertSuccess.showAndWait();
                            if (alertSuccess.getResult() == ButtonType.OK) {
                                String nim = fieldNim.getText();
                                String name = fieldName.getText();
                                String kelas = fieldClass.getText();
                                RadioButton selectedSize = (RadioButton) radioSize.getSelectedToggle();
                                String size = selectedSize.getText();

                                // OPEN NEW STAGE (New Window)
                                ShowData newStage = new ShowData(nim, name, kelas, size);
                            }
                        }
                    }
                }
            } catch (NullPointerException error) {
                System.out.println(error);
            }
        });

        // ACTION DELETE DATA
        btnDelete.setOnAction((actionEvent) -> {
            try {
                Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Anda yakin ingin menghapus data?", ButtonType.YES, ButtonType.NO);
                alertDelete.showAndWait();
                if (alertDelete.getResult() == ButtonType.YES) {
                    Alert alertSuccessDelete = new Alert(Alert.AlertType.NONE, "Data berhasil dihapus", ButtonType.OK);
                    alertSuccessDelete.show();

                    fieldNim.setText("");
                    fieldName.setText("");
                    fieldClass.setText("");
                    fieldNim.setDisable(false);
                    fieldName.setDisable(false);
                    fieldClass.setDisable(false);
                    radioSize.getSelectedToggle().setSelected(false);
                    radioSize.getToggles().forEach(toggle -> {
                        Node node = (Node) toggle;
                        node.setDisable(false);
                    });

                    btnNew.setDisable(false);
                    btnDelete.setDisable(true);
                    btnEdit.setDisable(true);
                    btnNew.setText("Tambah");
                }
            } catch (NullPointerException e) {
                System.out.println(e);
            }
        });

        // ACTION EDIT DATA
        btnEdit.setOnAction((actionEvent) -> {
            fieldNim.setDisable(false);
            fieldName.setDisable(false);
            fieldClass.setDisable(false);

            // DISABLE ALL RadioButton
            radioSize.getToggles().forEach(toggle -> {
                Node node = (Node) toggle;
                node.setDisable(false);
            });

            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
            btnNew.setDisable(false);
            btnNew.setText("Simpan");
        });

        // SHOW STAGE
        primaryStage.show();
    }

    public boolean textIsEmpty(TextField field) {
        try {
            if (field.getText().trim().isEmpty()) {
                field.getStyleClass().add("text-input-error");
                return true;
            }
            field.getStyleClass().remove("text-input-error");
            return false;
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return false;
    }
}
