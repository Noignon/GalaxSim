package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.CosmosElement;
import fr.istic.galaxsim.data.Galaxy;
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

    public static void coordByTime(CosmosElement e, Point3D f, int t) {
        Point3D velocity = e.getVelocity(t);

        double time = t * Calculations.Time;

        double velocityTime = time * 1000;
        Point3D nextVelocity = velocity.add(f.multiply(velocityTime));
        e.addVelocity(nextVelocity);

        double kilometreEnMparsec = 1000 / Calculations.MparsecEnMetre;

        Point3D pos = e.getCoordinate(t);
        double positionTime = time * kilometreEnMparsec;
        Point3D nextPosition = pos.add(nextVelocity.multiply(positionTime));

        e.addCoordinate(nextPosition);
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
     * Calcul de la force d'attraction entre deux amas (Famas2/amas1).
     *
     * @param amas1 position de l'amas principal
     * @param amas2 position de l'autre amas
     * @param m1 masse de l'amas principal
     * @param m2 masse de l'autre amas
     * @return la force d'attraction qu'excerce le deuxieme amas sur le premier
     */
    public static Point3D forceAttractionAmas(Point3D amas1, Point3D amas2, double m1, double m2) {
        Point3D p = amas1.subtract(amas2);
        double m = p.magnitude();
        Point3D u = p.multiply(1 / m);

        // distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
        double distance2 = m * m * Calculations.MparsecEnMetre * Calculations.MparsecEnMetre;

        // Force de gravitation = (G * Masse1 * Masse2) / distance^2
        double f = (-Calculations.G * m1 * m2  * Calculations.MsolaireEnKilo) / distance2;

        return u.multiply(f);
    }

    /**
     * Calcul de la force d'attraction entre une galaxie et un amas.
     *
     * @param gP position de la galaxie
     * @param aP position de l'amas
     * @param mA masse de l'amas
     * @return la force d'attraction qu'exerce l'amas sur la galaxie
     */
    public static Point3D forceAttractionGalaxy(Point3D gP, Point3D aP, double mA) {
        Point3D p = gP.subtract(aP);
        double m = p.magnitude();
        Point3D u = p.multiply(1 / m);

        double distance2 = m * m * Calculations.MparsecEnMetre * Calculations.MparsecEnMetre;

        // Force de gravitation = (G * Masse1 * Masse2) / distance^2
        double m1 = mA * Calculations.MsolaireEnKilo;

        return u.multiply((-Calculations.G * m1) / distance2);
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
