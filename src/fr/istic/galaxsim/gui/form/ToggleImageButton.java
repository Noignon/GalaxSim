package fr.istic.galaxsim.gui.form;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ToggleImageButton extends StackPane {

    /**
     * Bouton on/off represente par deux images
     */

    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;

    private final StringProperty url1 = new SimpleStringProperty();
    private final StringProperty url2 = new SimpleStringProperty();

    private final DoubleProperty fitWidth = new SimpleDoubleProperty();
    private final DoubleProperty fitHeight = new SimpleDoubleProperty();

    public ToggleImageButton() {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToggleImageButton.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch(IOException e) {
            System.err.println("Impossible de trouver le fichier ToggleImageButton.fxml");
            throw new RuntimeException(e);
        }

        url1.addListener((obs) -> {
            img1.setImage(new Image(url1.get()));
        });

        url2.addListener((obs) -> {
            img2.setImage(new Image(url2.get()));
        });

        // Les deux images sont reliees aux proprietes fitWidth et fitHeight
        img2.fitWidthProperty().bind(img1.fitWidthProperty());
        img1.fitWidthProperty().bind(fitWidth);

        img2.fitHeightProperty().bind(img1.fitHeightProperty());
        img1.fitHeightProperty().bind(fitHeight);

        // Une seule image est affichee
        img2.visibleProperty().bind(img1.visibleProperty().not());
    }

    /**
     * @return propriete sur la longueur des images
     */
    public DoubleProperty fitWidthProperty() {
        return fitWidth;
    }

    /**
     * @return propriete sur la hauteur des images
     */
    public DoubleProperty fitHeightProperty() {
        return fitHeight;
    }

    /**
     * @return longueur des images
     */
    public double getFitWidth() {
        return fitWidth.get();
    }

    /**
     * @return hauteur des images
     */
    public double getFitHeight() {
        return fitHeight.get();
    }

    /**
     * @return chemin de la premiere image
     */
    public String getUrl1() {
        return url1.get();
    }

    /**
     * @return chemin vers la deuxieme image
     */
    public String getUrl2() {
        return url2.get();
    }

    /**
     * @return propriete sur le chemin de la premiere image
     */
    public String url1Property() {
        return url1.get();
    }

    /**
     * @return propriete sur le chemin de la deuxieme image
     */
    public String url2Property() {
        return url2.get();
    }

    /**
     * Change l'image qui doit etre affichee a l'ecran
     */
    public void toggle() {
        img1.setVisible(!img1.isVisible());
    }

    /**
     * Definit la visibilite de la premiere image
     *
     * @param v parametre de visibilite
     */
    public void setFirstButtonVisibility(boolean v) {
        img1.setVisible(v);
    }

    /**
     * Definit la longueur des images
     *
     * @param fitWidth longueur en pixel
     */
    public void setFitWidth(double fitWidth) {
        this.fitWidth.set(fitWidth);
    }

    /**
     * Definit la hauteur des images
     *
     * @param fitHeight hauteur en pixel
     */
    public void setFitHeight(double fitHeight) {
        this.fitHeight.set(fitHeight);
    }

    /**
     * Definit le chemin vers la premiere image
     *
     * @param url chemin vers une image
     */
    public void setUrl1(String url) {
        url1.set(url);
    }

    /**
     * Definit le chemin vers la deuxieme image
     *
     * @param url chemin vers une image
     */
    public void setUrl2(String url) {
        url2.set(url);
    }

}
