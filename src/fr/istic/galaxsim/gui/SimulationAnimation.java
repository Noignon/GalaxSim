package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.data.CosmosElement;
import fr.istic.galaxsim.data.Vector;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.shape.Shape3D;
import javafx.util.Duration;

import java.util.ArrayList;

public class SimulationAnimation {

    /**
     * Objet 3D a animer
     */
    private final Shape3D shape;

    /**
     * Vitesse de deplacement de l'objet dans l'animatuin
     */
    private double moveSpeed;

    /**
     * Indice de la prochaine position de l'objet
     */
    private int positionsIndex;

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

    public SimulationAnimation(Shape3D shape, CosmosElement element) {
        this.shape = shape;

        double d = 0.0;
        Vector firstCoord = element.getCoordinate(0);
        positions.add(new Point3D(firstCoord.getX(), firstCoord.getY(), firstCoord.getZ()));

        // Calcul de la distance totale entre la premire coordonnee
        // et la derniere. Chaque coordonnee est convertie en Point3D
        for(int i = 1;i < element.getSizeCoordinate();i++) {
            Vector coord = element.getCoordinate(i);
            Point3D p = new Point3D(coord.getX(), coord.getY(), coord.getZ());
            d += positions.get(i - 1).distance(p);
            positions.add(p);
        }

        totalDistance = d;
        positionsIndex = (positions.isEmpty()) ? 0 : 1;
        updateDirection();
    }

    public Shape3D getShape() {
        return shape;
    }

    private Point3D getShapePosition() {
        return new Point3D(shape.getTranslateX(), shape.getTranslateY(), shape.getTranslateZ());
    }

    /**
     * Reinitialise la position de la forme (dans notre cas la sphere)
     * pour la mettre a sa position initiale (t=0)
     * L'indice de la prochaine position est lui aussi reinitialise
     */
    public void resetInitialPosition() {
        positionsIndex = (positions.isEmpty()) ? 0 : 1;
        Point3D initialPosition = positions.get(0);

        shape.setTranslateX(initialPosition.getX());
        shape.setTranslateY(initialPosition.getY());
        shape.setTranslateZ(initialPosition.getZ());
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
     * Definit la nouvelle position de l'objet en fonction du temps ecoule
     * L'objet sera place a un point contenu dans la liste des positions
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

        shape.setTranslateX(checkpoint.getX());
        shape.setTranslateY(checkpoint.getY());
        shape.setTranslateZ(checkpoint.getZ());
        updateDirection();
    }

    /**
     * @param b limites de l'objet parent
     * @return true si l'objet est contenu dans son parent, false sinon
     */
    public boolean shapeInBounds(Bounds b) {
        return shape.getBoundsInParent().intersects(b);
    }

    /**
     * Met a jour la position de l'objet.
     * Cette fonction est appellee a chaque tick suivant la valeur de
     * la constante TICK_RATE de la classe Simulation
     */
    public void update() {
        Point3D shapePosition = getShapePosition();
        Point3D currentTarget = positions.get(positionsIndex);

        // Distance separant l'objet du point de destination
        double d = shapePosition.distance(currentTarget);

        if(d >= moveSpeed) {
            // L'objet n'est pas encore arrive a sa destination
            shape.setTranslateX(shape.getTranslateX() + currentDirection.getX());
            shape.setTranslateY(shape.getTranslateY() + currentDirection.getY());
            shape.setTranslateZ(shape.getTranslateZ() + currentDirection.getZ());
        }
        else {
            // Selection de la prochaine destination
            if(positionsIndex < positions.size() - 1) {
                positionsIndex++;
                updateDirection();
            }
        }
    }

    /**
     * Met a a jour le vecteur de direction en prenant en compte la destination
     * et la vitesse de deplacement
     */
    public void updateDirection() {
        currentDirection = positions.get(positionsIndex)
                                    .subtract(getShapePosition())
                                    .normalize().multiply(moveSpeed);
    }

}
