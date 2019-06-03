package fr.istic.galaxsim.gui;

import javafx.scene.control.Alert;

public class ErrorDialog {

    public static void show(String content) {
        show("Erreur", content);
    }

    public static void show(String title, String content) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle(title);
        errorDialog.setHeaderText(null);
        errorDialog.setContentText(content);

        errorDialog.showAndWait();
    }

}
