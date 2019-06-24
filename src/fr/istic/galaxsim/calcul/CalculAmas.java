package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.Amas;
import javafx.geometry.Point3D;

/**
 * Classe permettant de calculer les différentes valeures utiles à la localisation des Amas.
 *
 * @author Lucas, Mathieu, Maxime
 *
 */
public class CalculAmas {

	/**
	 * Calcul de la force d'attraction entre deux amas.
	 *
	 * @param m1 masse de l'amas principal
	 * @param m2 masse de l'autre amas
	 * @return la force d'attraction qu'excercent les deux amas l'un envers l'autre
	 */
	public static Point3D forceAttraction(Point3D p, double m1, double m2) {
		double m = p.magnitude();
		Point3D u = p.multiply(1 / m);

		// distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		double distance2 = m * m * Calculations.MparsecEnMetre * Calculations.MparsecEnMetre;

		// Force de gravitation = (G * Masse1 * Masse2) / distance^2
		double f = (-Calculations.G * m1 * m2  * Calculations.MsolaireEnKilo) / distance2;

		return u.multiply(f);
	}

	/**
	 * Calcul des coordonnees de l'amas a1, ainsi que de sa vitesse, au temps t+1
	 * 
	 * @param a1     amas principal
	 * @param forceX somme des forces d'attaction exercee sur a1 par les autres amas
	 *               par rapport a l'axe X
	 * @param forceY somme des forces d'attaction exercee sur a1 par les autres amas
	 *               par rapport a l'axe Y
	 * @param forceZ somme des forces d'attaction exercee sur a1 par les autres amas
	 *               par rapport a l'axe Z
	 * @param t      indicateur de temps
	 */
	public static void coordByTime(Amas a1, double forceX, double forceY, double forceZ, int t) {
		double divMass = 1 / a1.getMass();

		double Ax = forceX * divMass;
		double Ay = forceY * divMass;
		double Az = forceZ * divMass;

		Point3D vitesse = a1.getVelocity(t);

		double Vx = vitesse.getX();
		double Vy = vitesse.getY();
		double Vz = vitesse.getZ();

		double time = t * Calculations.Time;

		Vx = (Ax * time) * 1000 + Vx;
		Vy = (Ay * time) * 1000 + Vy;
		Vz = (Az * time) * 1000 + Vz;

		a1.addVelocity(new Point3D(Vx, Vy, Vz));

		double kilometreEnMparsec = 1000 / Calculations.MparsecEnMetre;

		double x = (time * Vx) * kilometreEnMparsec + a1.getCoordinate(t).getX();
		double y = (time * Vy) * kilometreEnMparsec + a1.getCoordinate(t).getY();
		double z = (time * Vz) * kilometreEnMparsec + a1.getCoordinate(t).getZ();

		a1.addCoordinate(new Point3D(x, y, z));
	}
}
