package fr.istic.galaxsim.gui;

import javafx.animation.FadeTransition;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class DropdownPane extends VBox {

    @FXML
    private Label headerLabel;
    @FXML
    private ImageView hideButton;
    @FXML
    private ImageView showButton;

    private boolean contentHidden;

    public DropdownPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dropdownpane.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch(IOException e) {
            System.err.println("Impossible de trouver le fichier Dropdownpane.fxml");
            throw new RuntimeException(e);
        }

        contentHidden = false;
    }

    public void addNode(Node node) {
        getChildren().add(node);
    }

    public String getText() {
        return headerLabel.getText();
    }

    private void hideContent() {
        for(int i = 1;i < getChildren().size();i++) {
            Node child = getChildren().get(i);
            FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(500), child);
            fadeOutTransition.setFromValue(1.0);
            fadeOutTransition.setToValue(0.0);
            fadeOutTransition.play();

            fadeOutTransition.setOnFinished((handler) -> {
                child.setManaged(false);
            });
        }
    }

    @FXML
    public void initialize() {

    }

    public void setText(String text) {
        headerLabel.setText(text);
    }

    private void showContent() {
        for(int i = 1;i < getChildren().size();i++) {
            Node child = getChildren().get(i);
            child.setManaged(true);

            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(600), child);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
        }
    }

    public StringProperty textProperty() {
        return headerLabel.textProperty();
    }

    @FXML
    private void toggleContent(MouseEvent event) {
        if(contentHidden) {
            showContent();
        }
        else {
            hideContent();
        }

        contentHidden = !contentHidden;

        hideButton.setVisible(!hideButton.isVisible());
        showButton.setVisible(!showButton.isVisible());
    }

}
