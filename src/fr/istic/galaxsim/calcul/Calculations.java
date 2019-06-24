package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.CosmosElement;
import javafx.geometry.Point3D;

/**
 * Classe permettant de calculer l'attraction et la velocite
 * des amas et des galaxies.
 *
 * @author Lucas, Mathieu, Maxime
 */
public class Calculations {

    /**
     * Ecart en secondes entre chaque coordonnees
     */
    public static final double Time = Math.pow(10, 13);

    /**
     * Constante gravitationnelle
     */
    public static final double G = 6.67408 * Math.pow(10, -11);

    /**
     * Constante permettant de convertir une masse solaire en kilogramme
     */
    public static final double MsolaireEnKilo = 1.9891 * Math.pow(10, 42);

    /**
     * Constante permettant de convertir des megaparsec en metre
     */
    public static final double MparsecEnMetre = 3.086 * Math.pow(10, 22);

    /**
     * Calcul des coordonnees de l'element e au temps t=0
     *
     * @param e amas ou galaxie
     */
    public static void calculCoordInit(CosmosElement e) {
        // calcul des coordonnees

        double slonr = Math.toRadians(e.getSuperGalacticLon());
        double slatr = Math.toRadians(e.getSuperGalacticLat());

        double z = Math.sin(slatr) * e.getDistance();
        double hypothenus = Math.cos(slatr) * e.getDistance();
        double x = Math.cos(slonr) * hypothenus;
        double y = Math.sin(slonr) * hypothenus;

        // enregistrement des donnees initiales
        e.addCoordinate(new Point3D(x, y, z));
    }

    /**
     * Calcule la distance au carre entre deux points 3d.
     *
     * @param a premier point
     * @param b deuxieme point
     * @return distance au carre en pixels
     */
    public static double distance2(Point3D a, Point3D b) {
        return  Math.pow(a.getX() - b.getX(), 2) +
                Math.pow(a.getY() - b.getY(), 2) +
                Math.pow(a.getZ() - b.getZ(), 2);
    }

    /**
     * Calcul la vitesse initiale de l'element sur l'axe X.
     *
     * @param e amas ou galaxie
     * @return vitesse initiale de l'element sur l'axe X
     */
    public static double velocityX(CosmosElement e) {
        double velocity = 71 * e.getDistance();
        velocity = e.getVelocity() - velocity;
        velocity = Math.cos(Math.toRadians(e.getSuperGalacticLat())) * velocity;

        return velocity * Math.cos(Math.toRadians(e.getSuperGalacticLon()));
    }

    /**
     * Calcul la vitesse initiale de l'element sur l'axe Y.
     *
     * @param e amas ou galaxie
     * @return vitesse initiale de l'element sur l'axe Y
     */
    public static double velocityY(CosmosElement e) {
        double velocity = 71 * e.getDistance();
        velocity = e.getVelocity() - velocity;
        velocity = Math.cos(Math.toRadians(e.getSuperGalacticLat())) * velocity;

        return velocity * Math.sin(Math.toRadians(e.getSuperGalacticLon()));
    }

    /**
     * Calcul la vitesse initiale de l'element sur l'axe Z.
     *
     * @param e amas ou galaxie
     * @return vitesse initiale de l'element sur l'axe Z
     */
    public static double velocityZ(CosmosElement e) {
        double velocity = 71 * e.getDistance();
        velocity = e.getVelocity() - velocity;

        return velocity * Math.sin(Math.toRadians(e.getSuperGalacticLat()));
    }

}
