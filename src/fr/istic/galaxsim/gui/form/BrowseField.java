package fr.istic.galaxsim.gui.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class BrowseField extends HBox {

    @FXML
    private TextField pathField;

    // @OTODO Ajouter une restriction d'extensions
    public BrowseField() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BrowseField.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch(IOException e) {
            System.err.println("Impossible de trouver le fichier BrowseField.fxml");
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {

    }

    public File getFile() {
        return pathField.getText().isEmpty() ? null : new File(pathField.getText());
    }

    public String getPath() {
        return pathField.getText();
    }

    public void hideError() {
        pathField.getStyleClass().remove("field-error");
    }

    @FXML
    private void openFileBrowser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélection d'un jeu de données");
        File file = fileChooser.showOpenDialog(null);

        if(file != null) {
            pathField.setText(file.getAbsolutePath());
        }
    }

    public void showError() {
        pathField.getStyleClass().add("field-error");
    }

}
