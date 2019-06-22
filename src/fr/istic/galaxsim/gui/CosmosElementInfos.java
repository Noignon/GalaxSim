package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.data.Amas;
import fr.istic.galaxsim.data.CosmosElement;
import fr.istic.galaxsim.data.Galaxy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Fenetre affichant des informations sur l'amas ou la galaxie selectionne(e).
 */
public class CosmosElementInfos extends VBox {

    @FXML
    private GridPane pane;
    @FXML
    private HBox controlBar;

    @FXML
    private Label typeLabel;
    @FXML
    private Label identLabel;
    @FXML
    private Label velocityLabel;
    @FXML
    private Label massLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label sglongLabel;
    @FXML
    private Label sglatLabel;
    @FXML
    private Label galaxiesCountLabel;

    /**
     * Creer une nouvelle instance de CosmosElementInfos.
     * Le fichier CosmosElementInfos.fxml sera charge dans le constructeur.
     */
    public CosmosElementInfos() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CosmosElementInfos.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch(IOException e) {
            System.err.println("Impossible de trouver le fichier CosmosElementInfos.fxml");
            throw new RuntimeException(e);
        }
    }

    /**
     * Affiche les information de l'amas passe en parametre.
     * Bascule le mode d'affichage pour un amas.
     *
     * @param a amas a afficher
     */
    public void setAmas(Amas a) {
        typeLabel.setText("amas");
        massLabel.setText(String.valueOf(a.getMass()));
        galaxiesCountLabel.setText(String.valueOf(a.getNbGalaxies()));

        setGlobalInfos(a);
    }

    /**
     * Affiche les informations de la galaxie passee en parametre.
     * Bascule le mode d'affichage pour une galaxie.
     *
     * @param g galaxie a afficher
     */
    public void setGalaxy(Galaxy g) {
        typeLabel.setText("galaxie");
        massLabel.setText("non definie");
        galaxiesCountLabel.setText("1");

        setGlobalInfos(g);
    }

    /**
     * Affiche les informations communes aux amas et aux galaxies.
     *
     * @param e element a afficher
     */
    private void setGlobalInfos(CosmosElement e) {
        identLabel.setText(String.valueOf(e.getIdent()));
        velocityLabel.setText(String.valueOf(e.getVelocity()));
        distanceLabel.setText(String.valueOf(e.getDistance()));
        sglongLabel.setText(String.valueOf(e.getSuperGalacticLon()));
        sglatLabel.setText(String.valueOf(e.getSuperGalacticLat()));
    }

    /**
     * Masque la fenetre lorsque l'utilisateur clique sur l'icone de fermeture.
     *
     * @param event evennement associe au clique de la souris
     */
    @FXML
    private void hidePane(MouseEvent event) {
        setVisible(false);
    }

    /**
     * Deplace la fenetre maintient le clique gauche de la souris enfonce et la deplace.
     *
     * @param event evennement associe au clique de la souris
     */
    @FXML
    private void movePane(MouseEvent event) {
        if(event.isPrimaryButtonDown()) {
            setTranslateX(getTranslateX() + event.getX() - getWidth() / 2);
            setTranslateY(getTranslateY() + event.getY() - controlBar.getHeight() / 2);
        }
    }

}
