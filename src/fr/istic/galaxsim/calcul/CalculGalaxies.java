package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.Vector;
import fr.istic.galaxsim.data.Galaxy;

/**
 * Classe permettant de calculer les différentes valeures utiles à la localisation des Galaxies.
 * @author unijere
 *
 */
public class CalculGalaxies {

	/**
	 * Calcul de la force d'attraction d'un amas (aCoord) sur une galaxie (gCoord).
	 *
	 * @param gCoord coordonnee a l'instant t de la galaxie
	 * @param aCoord coordonne a l'instant t de l'amas qui est parmis les plus massifs
	 * @param m masse de l'amas
	 * @return la force d'attraction qu'exerce l'amas a sur la galaxie g1
	 */
	public static double forceAttraction(Vector gCoord, Vector aCoord, double m) {
		// distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		double x = (gCoord.getX() - aCoord.getX()) * (gCoord.getX() - aCoord.getX());
		double y = (gCoord.getY() - aCoord.getY()) * (gCoord.getY() - aCoord.getY());
		double z = (gCoord.getZ() - aCoord.getZ()) * (gCoord.getZ() - aCoord.getZ());
		double distance2 = (x + y + z) * Calculations.MparsecEnMetre * Calculations.MparsecEnMetre;

		// Force de gravitation = (G * Masse1 * Masse2) / distance^2
		double m1 = m * Calculations.MsolaireEnKilo;

		return (Calculations.G * m1) / distance2;
	}

	/**
	 * Calcul des coordonnees de la galaxie g1, ainsi que de sa vitesse, au temps
	 * t+1
	 * 
	 * @param g1     galaxie
	 * @param forceX somme des forces d'attaction exercee sur a1 par les amas les
	 *               plus massif par rapport a l'axe X
	 * @param forceY somme des forces d'attaction exercee sur a1 par les amas les
	 *               plus massif par rapport a l'axe Y
	 * @param forceZ somme des forces d'attaction exercee sur a1 par les amas les
	 *               plus massif par rapport a l'axe Z
	 * @param t      indicateur de temps
	 */
	public static void coordByTime(Galaxy g1, double forceX, double forceY, double forceZ, int t) {

		double Ax = forceX;
		double Ay = forceY;
		double Az = forceZ;

		Vector velocity = g1.getVelocity(t);
		double Vx = velocity.getX();
		double Vy = velocity.getY();
		double Vz = velocity.getZ();

		double time = t * Calculations.Time;

		Vx = (Ax * time) * 1000 + Vx;
		Vy = (Ay * time) * 1000 + Vy;
		Vz = (Az * time) * 1000 + Vz;

		g1.addVelocity(new Vector(Vx, Vy, Vz));

		double kilometreEnMparsec = 1000 / Calculations.MparsecEnMetre;

		Vector coord = g1.getCoordinate(t);
		double x = (time * Vx) * kilometreEnMparsec + coord.getX();
		double y = (time * Vy) * kilometreEnMparsec + coord.getY();
		double z = (time * Vz) * kilometreEnMparsec + coord.getZ();

		g1.addCoordinate(new Vector(x, y, z));
	}
}
