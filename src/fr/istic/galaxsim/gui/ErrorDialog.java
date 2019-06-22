package fr.istic.galaxsim.gui;

import javafx.scene.control.Alert;

/**
 * Classe permettant d'ouvrir une fenetre d'erreur.
 */
public class ErrorDialog {

    /**
     * Ouvre une fenetre d'erreur contenant le message passe en parametre.
     * Elle possede le titre par defaut "Erreur".
     *
     * @param content message d'erreur
     */
    public static void show(String content) {
        show("Erreur", content);
    }

    /**
     * Ouvre une fenetre d'erreur contenant le message passe en parametre.
     *
     * @param title titre de la fenetre
     * @param content message d'erreur
     */
    public static void show(String title, String content) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle(title);
        errorDialog.setHeaderText(null);
        errorDialog.setContentText(content);

        errorDialog.showAndWait();
    }

}
