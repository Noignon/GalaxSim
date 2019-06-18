package fr.istic.galaxsim.gui;

import javafx.animation.Transition;
import javafx.geometry.Bounds;
import javafx.util.Duration;

import java.util.ArrayList;

public class Simulation extends Transition {

    /**
     * Nombre de fois que la position de chaque objet est mise a jour par seconde
     */
    public final static int TICK_RATE = 30;

    /**
     * Liste des animations des amas / galaxies
     */
    private ArrayList<SimulationAnimation> animations = new ArrayList<>();

    /**
     * Limites du cube representant l'univers
     */
    private final Bounds bounds;

    public Simulation(Bounds bounds) {
        super(TICK_RATE);

        this.bounds = bounds;
    }

    /**
     * Ajoute une nouvelle animation a la liste
     *
     * La vitesse de deplacement de l'objet est calculee
     *
     * @param animation
     */
    public void addAnimation(SimulationAnimation animation) {
        animations.add(animation);
        animation.updateMoveSpeed(cycleDurationProperty().get());
    }

    /**
     * Mise a jour des positions de chaque objet a animer
     *
     * @param v position de l'animation dans le temps (parametre non utilise)
     */
    @Override
    protected void interpolate(double v) {
        for(SimulationAnimation animation : animations) {
            animation.update();

            // les objets qui sont en-dehors du cube ne sont pas affiches
            animation.getShape().setVisible(animation.shapeInBounds(bounds));
        }
    }

    /**
     * Modifie la duree de l'animation
     *
     * La vitesse de deplacement de chaque objet est calculee a nouveau calcule
     *
     * @param duration duree de l'animation
     */
    public void setDuration(Duration duration) {
        if(cycleDurationProperty().get() != duration) {
            setCycleDuration(duration);
            setDuration(duration);

            for(SimulationAnimation animation : animations) {
                animation.updateMoveSpeed(duration);
            }
        }
    }

    /**
     * Positionne chaque objet a la position correspondante au temps
     * passe en parametre.
     * Le temps ecoule de l'animation est modifie.
     *
     * @param t
     */
    public void setTimePosition(double t) {
        jumpTo(Duration.seconds(t));

        for(SimulationAnimation animation : animations) {
            animation.setTransitionPosition(t);
            animation.getShape().setVisible(animation.shapeInBounds(bounds));
        }
    }

    /**
     * Arrete l'animation
     *
     * Toutes les animations sont reinitialisees et tous les objets retournent
     * a leur position initiale.
     */
    public void stopSimulation() {
        stop();
        for(SimulationAnimation animation : animations) {
            animation.resetInitialPosition();
            animation.getShape().setVisible(animation.shapeInBounds(bounds));
        }
    }

}
