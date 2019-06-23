package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.CosmosElement;
import fr.istic.galaxsim.data.Vector;

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
     * Calcul la longitude du vecteur coord1coord2.
     *
     * @param coord1 premiere coordonnee a l'instant t
     * @param coord2 deuxieme coordonnee a l'instant t
     * @return la longitude du vecteur coord1coord2
     */

    public static double attractionLongitude(Vector coord1, Vector coord2) {
        double x = coord2.getX() - coord1.getX();
        double y = coord2.getY() - coord1.getY();
        double h = Math.sqrt(x * x + y * y);

        if (y > 0) {
            return Math.acos(x / h);
        }
        else {
            return (Math.PI / 2) + Math.acos(x / h);
        }
    }

    /**
     * Calcul la latitude du vecteur coord1coord2.
     * @param coord1 coordonnee a l'instant t de l'amas principal
     * @param coord2 coordonnee a l'instant t de l'autre amas etant parmi les plus massif
     * @return la latitude du vecteur  coord1coord2
     */

    public static double attractionLatitude(Vector coord1, Vector coord2) {
        double x = coord2.getX() - coord1.getX();
        double y = coord2.getY() - coord1.getY();
        double z = coord1.getZ() - coord2.getZ();

        double hypothenus = Math.sqrt(x * x + y * y);
        double distance = Math.sqrt(hypothenus * hypothenus + z * z);

        if(z > 0) {
            return Math.acos(hypothenus / distance);
        }
        else {
            return (Math.PI / 2)+Math.acos(hypothenus / distance);
        }
    }

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
        e.addCoordinate(new Vector(x, y, z));
    }

    /**
     * Calcul de la force d'attraction exercee entre a1 et a2 sur l'axe X.
     *
     * @param cosLon cosinus de la longitude obtenue avec {@link Calculations#attractionLongitude}
     * @param cosLat cosinus de la latitude obtenue avec {@link Calculations#attractionLatitude}
     * @param F force d'attraction exercee entre a1 et a2, calculee par {@link CalculGalaxies#forceAttraction}
     * @return force d'attraction exercee entre a1 et a2 sur l'axe X
     */
    public static double forceX(double cosLon, double cosLat, double F) {
        return F * cosLon * cosLat;
    }

    /**
     * Calcul de la force d'attraction exercee entre a1 et a2 sur l'axe Y.
     *
     * @param sinLon sinus de la longitude obtenue avec {@link Calculations#attractionLongitude}
     * @param cosLat cosinus de la latitude obtenue avec {@link Calculations#attractionLatitude}
     * @param F force d'attraction exercee entre a1 et a2, calculee par {@link CalculGalaxies#forceAttraction}
     * @return force d'attraction exercee entre a1 et a2 sur l'axe Y
     */
    public static double forceY(double sinLon, double cosLat, double F) {
        return F * cosLat * sinLon;
    }

    /**
     * Calcul de la force d'attraction exercee entre a1 et a2 sur l'axe Z.
     *
     * @param sinLat sinus de la latitude obtenue avec {@link Calculations#attractionLatitude}
     * @param F force d'attraction exercee entre a1 et a2, calculee par {@link CalculGalaxies#forceAttraction}
     * @return force d'attraction exercee entre a1 et a2 sur l'axe Z
     */
    public static double forceZ(double sinLat, double F) {
        return F * sinLat;
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
