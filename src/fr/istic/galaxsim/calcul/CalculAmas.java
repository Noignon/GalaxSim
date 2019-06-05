package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.Amas;
import fr.istic.galaxsim.data.Coordinate;

public class CalculAmas {

	/**
	 *
	 * Classe permettant de realiser les calculs de position pour les galaxies et
	 * amas de galaxies
	 *
	 */

	// calcule les coordonnees initiales d'une galaxie
	public static void calculCoordInit(Amas a1) {
		// calcul des coordonnees
		double z = Math.sin(a1.getSuperGalacticLat()) * a1.getDistance();
		double hypothenus = Math.cos(a1.getSuperGalacticLat()) * a1.getDistance();
		double x = Math.cos(a1.getSuperGalacticLon()) * hypothenus;
		double y = Math.sin(a1.getSuperGalacticLon()) * hypothenus;

		// enregistrement des donnees initiales
		a1.addCoordinate(new Coordinate(x, y, z));
	}

	// calcule la force d'attraction entre deux galaxies
	public static double forceAttraction(Amas a1, Amas a2, int t) {

		// distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		double x = Math.pow(a1.getCoordinate(t).getX() - a2.getCoordinate(t).getX(), 2);
		double y = Math.pow(a1.getCoordinate(t).getY() - a2.getCoordinate(t).getY(), 2);
		double z = Math.pow(a1.getCoordinate(t).getZ() - a2.getCoordinate(t).getZ(), 2);
		double distance = x + y + z;

		// Force de gravitation = (G * Masse1 * Masse2) / distance^2
		distance = distance * 9.521 * Math.pow(10, 44);
		double G = 6.67408 * Math.pow(10, -11);
		double m1 = a1.getMass() * 1.991 * Math.pow(10, 42);
		double m2 = a2.getMass() * 1.991 * Math.pow(10, 42);
		double F = (G * m1 * m2) / distance;

		return F;
	}

	// calcule la longitude entre deux galaxies
	public static double attractionLongitude(Amas a1, Amas a2, int t) {
		double x = a2.getCoordinate(t).getX() - a1.getCoordinate(t).getX();
		double y = a2.getCoordinate(t).getY() - a1.getCoordinate(t).getY();

		return Math.atan(x / y);
	}

	// calcule la latitude entre deux galaxies
	public static double attractionLatitude(Amas a1, Amas a2, int t) {
		double x = Math.pow(a2.getCoordinate(t).getX() - a1.getCoordinate(t).getX(), 2);
		double y = Math.pow(a2.getCoordinate(t).getY() - a1.getCoordinate(t).getY(), 2);
		double z = a2.getCoordinate(t).getZ() - a1.getCoordinate(t).getZ();
		double hypothenus = Math.sqrt(x + y);

		return Math.atan(z / hypothenus);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe X
	public static double forceX(Amas a1, Amas a2, int t, double F) {
		double longitude = attractionLongitude(a1, a2, t);

		return F * Math.cos(longitude);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe Y
	public static double forceY(Amas a1, Amas a2, int t, double F) {
		double longitude = attractionLongitude(a1, a2, t);

		return F * Math.sin(longitude);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe Z
	public static double forceZ(Amas a1, Amas a2, int t, double F) {
		double latitude = attractionLatitude(a1, a2, t);

		return F * Math.sin(latitude);
	}

	// retourne la velocity de la galaxie sur l'axe X
	public static double velocityX(Amas a1, int t) {
		double velocity = 71 * a1.getDistance();
		velocity = a1.getVelocity(t) - velocity;

		return velocity * Math.cos(Math.toRadians(a1.getSuperGalacticLon()));
	}

	// retourne la velocity de la galaxie sur l'axe Y
	public static double velocityY(Amas a1, int t) {
		double velocity = 71 * a1.getDistance();
		velocity = a1.getVelocity(t) - velocity;

		return velocity * Math.sin(Math.toRadians(a1.getSuperGalacticLon()));
	}

	// retourne la velocity de la galaxie sur l'axe Z
	public static double velocityZ(Amas a1, int t) {
		double velocity = 71 * a1.getDistance();
		velocity = a1.getVelocity(t) - velocity;

		return velocity * Math.sin(Math.toRadians(a1.getSuperGalacticLat()));
	}

	public static void coordByTime(Amas a1, double forceX, double forceY, double forceZ, int t) {
		double Ax = forceX / (a1.getMass()* 1.991 * Math.pow(10, 42));
		double Ay = forceY / (a1.getMass()* 1.991 * Math.pow(10, 42));
		double Az = forceZ / (a1.getMass()* 1.991 * Math.pow(10, 42));

		double Vx = velocityX(a1, t);
		double Vy = velocityY(a1, t);
		double Vz = velocityZ(a1, t);

		double time = t * Math.pow(10, 17);

		double x = ((Ax * time * time) / 2) * 3.2408 *Math.pow(10, -23) + (time * Vx) * 3.2408 *Math.pow(10, -20) + a1.getCoordinate(t).getX();
		double y = ((Ay * time * time) / 2) * 3.2408 *Math.pow(10, -23) + (time * Vy) * 3.2408 *Math.pow(10, -20) + a1.getCoordinate(t).getY();
		double z = ((Az * time * time) / 2) * 3.2408 *Math.pow(10, -23) + (time * Vz) * 3.2408 *Math.pow(10, -20) + a1.getCoordinate(t).getZ();

		Vx = (Ax * time) * 1000 + Vx;
		Vy = (Ay * time) * 1000 + Vy;
		Vz = (Az * time) * 1000 + Vz;

		double velocity = Math.sqrt(Vx * Vx + Vy * Vy + Vz * Vz);
		a1.addVelocity(velocity);

		a1.addCoordinate(new Coordinate(x, y, z));
	}
}
