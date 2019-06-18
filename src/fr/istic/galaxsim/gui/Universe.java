package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.data.Amas;
import fr.istic.galaxsim.data.CosmosElement;
import fr.istic.galaxsim.data.Galaxy;
import fr.istic.galaxsim.data.Vector;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Universe extends Group {

    private final static PhongMaterial amasMaterial = new PhongMaterial(Color.GREEN);
    private final static PhongMaterial galaxyMaterial = new PhongMaterial(Color.RED);
    private final static PhongMaterial selectedElementMaterial = new PhongMaterial(Color.BLUE);

    private final CosmosElementInfos cosmosElementInfos;

    private Group elements = new Group();

    private final Text leftScaleText;
    private final Text rightScaleText;
    private final Rotate scaleTextRotateX = new Rotate(-20, Rotate.X_AXIS);
    private final Rotate scaleTextRotateY = new Rotate(-45, Rotate.Y_AXIS);

    private final Translate translate = new Translate();
    public final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
    public final Rotate rotateY = new Rotate(45, Rotate.Y_AXIS);

    private double lastMouseClickPosX;
    private double lastMouseClickPosY;

    private Sphere lastSelectedSphere = null;
    private boolean isLastSelectedGalaxy = false;

    private final Simulation sim;

    public Universe(Node parentContainer, CosmosElementInfos cosmosElementInfos) {
        this.cosmosElementInfos = cosmosElementInfos;

        Box box = new Box(200, 200, 200);
        box.setDrawMode(DrawMode.LINE);

        sim = new Simulation(box.getBoundsInParent());

        // Affichage de l'echelle en positionnant les limites du cube
        // en bas a gauche et a droite
        leftScaleText = new Text(String.format("-%d Mpc", (int) box.getWidth() / 2));
        leftScaleText.setFont(new Font(12));
        leftScaleText.getTransforms().addAll(scaleTextRotateX, scaleTextRotateY);

        Bounds b = leftScaleText.getBoundsInLocal();
        leftScaleText.setTranslateX(-(box.getWidth() + b.getWidth()) / 2);
        leftScaleText.setTranslateY(box.getHeight() / 2 + b.getHeight() + 3);
        leftScaleText.setTranslateZ(-box.getDepth() / 2);

        rightScaleText = new Text(String.format("%d Mpc", (int) box.getWidth() / 2));
        rightScaleText.setFont(new Font(12));
        rightScaleText.getTransforms().addAll(scaleTextRotateX, scaleTextRotateY);

        b = rightScaleText.getBoundsInLocal();
        rightScaleText.setTranslateX((box.getWidth() - b.getWidth()) / 2);
        rightScaleText.setTranslateY(box.getHeight() / 2 + b.getHeight() + 3);
        rightScaleText.setTranslateZ(-box.getDepth() / 2);

        getChildren().addAll(box, elements, leftScaleText, rightScaleText);

        getTransforms().addAll(rotateX, rotateY, translate);

        /* Sauvegarde des coordonnees de la souris lorsque le bouton droit
        ou le clique molette est active afin de calculer le deplacement de
        la camera */
        parentContainer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(event.isSecondaryButtonDown() || event.isMiddleButtonDown()) {
                lastMouseClickPosX = event.getSceneX();
                lastMouseClickPosY = event.getSceneY();
            }
        });

        /* Deplacement de la camera a l'aide du clique droit ou du clique
        molette */
        parentContainer.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double mouseDeltaX = event.getSceneX() - lastMouseClickPosX;
            double mouseDeltaY = event.getSceneY() - lastMouseClickPosY;

            if(event.isMiddleButtonDown()) {
                rotateX.setAngle(rotateX.getAngle() - mouseDeltaY);
                rotateY.setAngle(rotateY.getAngle() + mouseDeltaX);

                // Rotation du texte de l'echelle pour que celui-ci soit
                // toujours dans le sens de la lecture
                scaleTextRotateY.setAngle(scaleTextRotateY.getAngle() - mouseDeltaX);
                scaleTextRotateX.setAngle(scaleTextRotateX.getAngle() + mouseDeltaY);
            }
            else if(event.isSecondaryButtonDown()) {
                translate.setX(translate.getX() + mouseDeltaX);
                translate.setY(translate.getY() + mouseDeltaY);
            }

            // Sauvegarde de la position de la souris pour les prochains deplacements
            lastMouseClickPosX = event.getSceneX();
            lastMouseClickPosY = event.getSceneY();

        });

        parentContainer.addEventHandler(ScrollEvent.ANY, event -> {
            double amount = (event.getDeltaY() < 0.0) ? 20f : -20f;
            setTranslateZ(getTranslateZ() + amount);
        });
    }

    /**
     * Ajoute un nouvel amas aux elements de l'univers
     *
     * @param a amas a ajouter
     */
    public void addAmas(Amas a) {
    	// la taille des spheres est calculees en fonction de leurs masses
    	// les valeurs des logs ont ete calculees en fonction du max et du min des masses
    	double radius = a.getMass() * Math.log(1.045) / Math.log(22000);
        
        Sphere s = createCosmosElementSphere(radius, a);
        s.setMaterial(amasMaterial);
    }

    /**
     * Ajoute une nouvelle galaxie aux elements de l'univers
     *
     * @param g galaxie a ajouter
     */
    public void addGalaxy(Galaxy g) {
        Sphere s = createCosmosElementSphere(0.4f, g);
        s.setMaterial(galaxyMaterial);
    }

    /**
     * Supprime tous les elements (amas et galaxies) de l'univers
     */
    public void clear() {
        elements.getChildren().clear();
    }

    /**
     * Creer un nouvel element 3D representant un amas ou une galaxie
     *
     * L'element reagit au clique gauche de la souris pour afficher ses informations.
     * Une animation est ajoutee a la simulation pour cet element.
     *
     * @param radius rayon de la sphere a creer
     * @param cosmosElement
     * @return sphere 3D representant un amas ou une galaxie
     */
    private Sphere createCosmosElementSphere(double radius, CosmosElement cosmosElement) {
        Sphere s = new Sphere(radius);

        Vector coord = cosmosElement.getCoordinate(0);
        s.setTranslateX(coord.getX());
        s.setTranslateY(coord.getY());
        s.setTranslateZ(coord.getZ());

        elements.getChildren().add(s);

        s.setOnMouseClicked((e) -> {
            if(lastSelectedSphere != null) {
                // Reinitialisation de la couleur par default de la sphere
                // dernierement selectionnee
                if(isLastSelectedGalaxy) {
                    lastSelectedSphere.setMaterial(galaxyMaterial);
                }
                else {
                    lastSelectedSphere.setMaterial(amasMaterial);
                }
            }

            // La sphere selectionne possede la couleur bleue
            s.setMaterial(selectedElementMaterial);
            if(cosmosElement instanceof Galaxy) {
                cosmosElementInfos.setGalaxy((Galaxy) cosmosElement);
                isLastSelectedGalaxy = true;
            }
            else {
                cosmosElementInfos.setAmas((Amas) cosmosElement);
                isLastSelectedGalaxy = false;
            }

            cosmosElementInfos.setVisible(true);
            lastSelectedSphere = s;
        });

        sim.addAnimation(new SimulationAnimation(s, cosmosElement));

        return s;
    }

    public Simulation getSimulation() {
        return sim;
    }

}
