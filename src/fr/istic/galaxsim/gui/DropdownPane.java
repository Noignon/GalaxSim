package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.gui.form.ToggleImageButton;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public class DropdownPane extends VBox {

    @FXML
    private Label headerLabel;
    @FXML
    private ToggleImageButton dropButton;

    // Indique si le composant affiche ou non le contenu
    private BooleanProperty contentHidden;

    public DropdownPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DropdownPane.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch(IOException e) {
            System.err.println("Impossible de trouver le fichier Dropdownpane.fxml");
            throw new RuntimeException(e);
        }

        contentHidden = new SimpleBooleanProperty();
    }

    public BooleanProperty contentHiddenProperty() {
        return contentHidden;
    }

    public boolean getContentHidden() {
        return contentHidden.get();
    }

    /**
     * Recupere la chaine de caracteres de l'en-tete
     *
     * Cette fonction est utilisee par javafx pour permettre l'assignation
     * de l'attribut text (en-tete)
     *
     * @return valeur de l'en-tete
     */
    public String getText() {
        return headerLabel.getText();
    }

    /**
     * Cache le contenu du composants
     *
     * Chaque enfant est associe a une animation (FadeTransition) pour
     * disparaitre progressivement. A la fin de l'animation, l'enfant
     * n'est plus affiche pour que le parent soit redimensionne
     */
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

    public void setContentHidden(boolean hidden) {
        contentHidden.set(hidden);
        if(hidden) {
            hideContent();
        }
        else {
            showContent();
        }

        dropButton.toggle();
    }

    /**
     * Definit le texte de l'en-tete
     *
     * Cette fonction est utilisee par javafx pour permettre l'assignation
     * de l'attribut text (en-tete)
     * @param text nouvel en-tete
     */
    public void setText(String text) {
        headerLabel.setText(text);
    }

    /**
     * Affiche le contenu du composant
     *
     * Chaque enfant est associe a une animation (FadeTransition) pour
     * apparaitre progressivement
     */
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

    /**
     * Cette fonction est utilisee par javafx pour permettre l'assignation
     * de l'attribut text (en-tete)
     *
     * @return propriete representant la valeur de l'en-tete
     */
    public StringProperty textProperty() {
        return headerLabel.textProperty();
    }

    /**
     * Affiche/masque le contenu du composant selon la valeur de l'attribut
     * contentHidden. Celle-ci est changee par sa negation a la fin de la fonction
     *
     * @param event evennement associe au clique de la souris
     */
    @FXML
    private void toggleContent(MouseEvent event) {
        setContentHidden(!contentHidden.get());
    }

}
