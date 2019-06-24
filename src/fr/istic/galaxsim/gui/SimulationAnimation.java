package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.calcul.Calculations;
import fr.istic.galaxsim.data.CosmosElement;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Animation d'un element representant un amas ou une galaxie
 *
 * @see Simulation
 */
public class SimulationAnimation {

    /**
     * Nombre maximal de points representant la trainee de l'objet
     */
    private final static int MAX_TRAIL = 7;

    /**
     * Texture des points de la trainee
     */
    private final static PhongMaterial TRAIL_MATERIAL = new PhongMaterial(Color.LIGHTBLUE);

    /**
     * Objet 3D a animer
     */
    private final Shape3D shape;

    /**
     * Groupe contenant les points de la trainee de l'objet
     */
    private final Group directionTrail;

    /**
     * Dernier point
     */
    private Point3D lastTrailPoint;

    /**
     * Indice du prochain element dans la liste des points de la trainee
     */
    private int nextTrailElement;

    /**
     * Distance entre le dernier point de la trainee et la derniere position
     * de l'objet
     */
    private double lastTrailDistance;

    /**
     * Vitesse de deplacement de l'objet dans l'animation
     */
    private double moveSpeed;

    /**
     * Indice de la prochaine position de l'objet
     */
    private int nextPositionsIndex;

    /**
     * Liste des points de destination de l'objet
     */
    private final ArrayList<Point3D> positions = new ArrayList<>();

    /**
     * Distance total a parcourir par l'objet pour passer par toutes les positions
     */
    private final double totalDistance;

    /**
     * Duree totale de l'animation
     */
    private Duration totalDuration;

    /**
     * Vecteur de direction pointant vers le prochain point de l'objet a animer
     */
    private Point3D currentDirection;

    private final BooleanProperty trailVisibility;

    /**
     * Creer une nouvelle instance de SimulationAnimation.
     *
     * @param shape objet a animer
     * @param directionTrail groupe qui contiendra la trainee de l'objet
     * @param element element (amas ou galaxie) a representer
     * @param trailVisibility propriete de visibilite de la trainee (partagee par toutes les animations)
     */
    public SimulationAnimation(Shape3D shape, Group directionTrail, CosmosElement element, BooleanProperty trailVisibility) {
        this.shape = shape;
        this.directionTrail = directionTrail;
        this.trailVisibility = trailVisibility;

        double d = 0.0;
        Point3D firstCoord = element.getCoordinate(0);
        positions.add(firstCoord);

        // Calcul de la distance totale entre la premire coordonnee
        // et la derniere. Chaque coordonnee est convertie en Point3D
        for(int i = 1;i < element.getSizeCoordinate();i++) {
            Point3D p = element.getCoordinate(i);
            d += positions.get(i - 1).distance(p);
            positions.add(p);
        }

        totalDistance = d;
        nextPositionsIndex = (positions.isEmpty()) ? 0 : 1;
        updateDirection();

        // Initialisation de la trainee de l'objet
        lastTrailPoint = positions.get(0);
        lastTrailDistance = 0.0;
        nextTrailElement = 0;

        // Creation des points de trainee
        // Ils sont masques par defaut
        for(int i = 0;i < MAX_TRAIL;i++) {
            addTrailPoint();
        }
    }

    /**
     * Cree un nouveau point (sphere) de trainee et l'ajoute au
     * groupe directionTrail.
     * Par defaut l'element n'est pas affiche.
     */
    private void addTrailPoint() {
        Sphere s = new Sphere(0.2f);
        s.setMaterial(TRAIL_MATERIAL);
        s.setVisible(false);

        directionTrail.getChildren().add(s);
    }

    /**
     * Retourne un vecteur contenant la position de l'objet a animer.
     */
    private Point3D getShapePosition() {
        return new Point3D(shape.getTranslateX(), shape.getTranslateY(), shape.getTranslateZ());
    }

    /**
     * Masque tous les points de la trainee.
     */
    public void hideTrail() {
        for(Node node : directionTrail.getChildren()) {
            node.setVisible(false);
        }
    }

    /**
     * Reinitialise la position de la forme (dans notre cas la sphere)
     * pour la mettre a sa position initiale (t=0).
     * L'indice de la prochaine position est lui aussi reinitialise.
     */
    public void resetInitialPosition() {
        nextPositionsIndex = (positions.isEmpty()) ? 0 : 1;
        Point3D initialPosition = positions.get(0);

        setObjectPosition(shape, initialPosition);

        resetTrail();
        lastTrailPoint = initialPosition;
    }

    /**
     * Met a jour la vitesse de deplacement de l'objet en fonction de
     * la duree de l'animation.
     *
     * @param duration duree de l'animation
     */
    public void updateMoveSpeed(Duration duration) {
        totalDuration = duration;
        moveSpeed = totalDistance / duration.toSeconds() / Simulation.TICK_RATE;
        updateDirection();
    }

    /**
     * Reinitialise la trainee de l'objet.
     * Tous ses points sont masques.
     */
    private void resetTrail() {
        for(Node node : directionTrail.getChildren()) {
            node.setVisible(false);
        }

        lastTrailDistance = 0.0;
        nextTrailElement = 0;
    }

    /**
     * Positionne l'objet sur le point passe en parametre.
     *
     * @param node objet a deplacer
     * @param p nouvelle position de l'objet
     */
    private void setObjectPosition(Node node, Point3D p) {
        node.setTranslateX(p.getX());
        node.setTranslateY(p.getY());
        node.setTranslateZ(p.getZ());
    }

    /**
     * Definit la nouvelle position de l'objet en fonction du temps ecoule.
     * L'objet sera place a un point contenu dans la liste des positions.
     *
     * @param t temps ecoule en secondes depuis le debut de l'animation
     * doit etre compris entre 0 et totalDuration
     */
    public void setTransitionPosition(double t) {
        if(t < 0 || t > totalDuration.toSeconds()) {
            return;
        }

        int checkpointIndex = (int) (t * positions.size() / totalDuration.toSeconds());
        checkpointIndex = Math.max(checkpointIndex - 1, 0);

        Point3D checkpoint = positions.get(checkpointIndex);
        nextPositionsIndex = checkpointIndex;

        setObjectPosition(shape, checkpoint);
        updateDirection();

        resetTrail();
        lastTrailPoint = checkpoint;
    }

    /**
     * Indique si l'objet a animer est contenu dans les limites
     * passees en parametres.
     *
     * @param b limites de l'objet parent
     * @return true si l'objet est contenu dans son parent, false sinon
     */
    public boolean shapeInBounds(Bounds b) {
        return shape.getBoundsInParent().intersects(b);
    }

    /**
     * Met a jour la position de l'objet.
     * Cette fonction est appellee a chaque tick suivant la valeur de
     * {@link fr.istic.galaxsim.gui.Simulation#TICK_RATE}.
     */
    public void update() {
        Point3D shapePosition = getShapePosition();
        Point3D currentTarget = positions.get(nextPositionsIndex);

        lastTrailDistance += moveSpeed;

        if(trailVisibility.get() && lastTrailDistance > 1) {
            Node s = directionTrail.getChildren().get(nextTrailElement);
            s.setVisible(true);
            setObjectPosition(s, lastTrailPoint);

            nextTrailElement = (nextTrailElement + 1) % MAX_TRAIL;
            lastTrailPoint = shapePosition;
            lastTrailDistance = 0.0;
        }

        // Distance separant l'objet du point de destination
        double d = Calculations.distance2(shapePosition, currentTarget);

        if(d >= moveSpeed * moveSpeed) {
            // L'objet n'est pas encore arrive a sa destination
            setObjectPosition(shape, shapePosition.add(currentDirection));
        }
        else {
            // Selection de la prochaine destination
            if(nextPositionsIndex < positions.size() - 1) {
                nextPositionsIndex++;
                updateDirection();
            }
        }
    }

    /**
     * Met a a jour le vecteur de direction en prenant en compte la destination
     * et la vitesse de deplacement.
     */
    public void updateDirection() {
        currentDirection = positions.get(nextPositionsIndex)
                                    .subtract(getShapePosition())
                                    .normalize().multiply(moveSpeed);
    }

    /**
     * Definit la visibilite de l'objet a animer et de sa trainee
     *
     * @param v visibilite de l'objet
     */
    public void setShapeVisibility(boolean v) {
        shape.setVisible(v);
        directionTrail.setVisible(v);
    }

}
