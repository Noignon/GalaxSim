package fr.istic.galaxsim.gui.form;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

/**
 * Composant permettre de selectionner un fichier a l'aide d'une fenetre
 * ou un champ de texte.
 */
public class BrowseField extends HBox {

    @FXML
    private TextField pathField;

    /**
     * Creer une nouvelle instance de BrowseField.
     *
     * Le fichier BrowseField.fxml est charge dans le constructeur.
     */
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

    /**
     * Retourne le fichier selectionne par l'utilisateur ou null.
     */
    public File getFile() {
        return pathField.getText().isEmpty() ? null : new File(pathField.getText());
    }

    /**
     * Retourne le chemin du fichier selectionne par l'utilisateur.
     */
    public String getPath() {
        return pathField.getText();
    }

    /**
     * Masque l'affichage de l'erreur en retirant la classe css "field-error"
     */
    public void hideError() {
        pathField.getStyleClass().remove("field-error");
    }

    /**
     * Ouvre une fenetre de selection de fichier.
     *
     * @param event
     */
    @FXML
    private void openFileBrowser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélection d'un jeu de données");
        File file = fileChooser.showOpenDialog(null);

        if(file != null) {
            pathField.setText(file.getAbsolutePath());
        }
    }

    /**
     * Affiche une erreur en ajoutant la classe css "field-error".
     */
    public void showError() {
        pathField.getStyleClass().add("field-error");
    }

}
