package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.Amas;
import fr.istic.galaxsim.data.Coordinate;
import fr.istic.galaxsim.data.Galaxy;

public class CalculGalaxies {
	/**
	 *
	 * Classe permettant de realiser les calculs de position pour les galaxies et
	 * amas de galaxies
	 *
	 */

	// calcule les coordonnees initiales d'une galaxie
	public static void calculCoordInit(Galaxy g1) {
		// variables coordonnees
		double x;
		double y;
		double z;

		// calcul des coordonnees
		z = Math.sin(Math.toRadians(g1.getSuperGalacticLat())) * g1.getDistance();
		double hypothenus = Math.cos(Math.toRadians(g1.getSuperGalacticLat())) * g1.getDistance();
		x = Math.cos(Math.toRadians(g1.getSuperGalacticLon())) * hypothenus;
		y = Math.sin(Math.toRadians(g1.getSuperGalacticLon())) * hypothenus;

		// enregistrement des donnees initiales
		Coordinate coord = new Coordinate(x, y, z);
		g1.addCoordinate(coord);
	}

	// calcule la force d'attraction entre deux galaxies
	public static double forceAttraction(Galaxy g1, Amas a, int t) {

		// distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		double x = Math.pow(g1.getCoordinate(t).getX() - a.getCoordinate(t).getX(), 2);
		double y = Math.pow(g1.getCoordinate(t).getY() - a.getCoordinate(t).getY(), 2);
		double z = Math.pow(g1.getCoordinate(t).getZ() - a.getCoordinate(t).getZ(), 2);
		double distance = x + y + z;
		// Force de gravitation = (G * Masse1 * Masse2) / distance^2
		distance = distance * 9.521 * Math.pow(10, 44);
		double G = 6.67408 * Math.pow(10, -11);
		double mass = a.getMass() * 1.991 * Math.pow(10, 42);
		double F = (G * mass) / distance;

		return F;
	}

	// calcule la longitude entre deux galaxies
	public static double attractionLongitude(Galaxy g1, Amas a, int t) {
		double x = a.getCoordinate(t).getX() - g1.getCoordinate(t).getX();
		double y = a.getCoordinate(t).getY() - g1.getCoordinate(t).getY();

		return Math.atan(x / y);
	}

	// calcule la latitude entre deux galaxies
	public static double attractionLatitude(Galaxy g1, Amas a, int t) {
		double x = Math.pow(a.getCoordinate(t).getX() - g1.getCoordinate(t).getX(), 2);
		double y = Math.pow(a.getCoordinate(t).getY() - g1.getCoordinate(t).getY(), 2);
		double z = a.getCoordinate(t).getZ() - g1.getCoordinate(t).getZ();
		double hypothenus = Math.sqrt(x + y);

		return Math.atan(z / hypothenus);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe X
	public static double forceX(Galaxy g1, Amas a, int t, double F) {
		double longitude = attractionLongitude(g1, a, t);

		return F * Math.cos(longitude);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe Y
	public static double forceY(Galaxy g1, Amas a, int t, double F) {
		double longitude = attractionLongitude(g1, a, t);

		return F * Math.sin(longitude);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe Z
	public static double forceZ(Galaxy g1, Amas a, int t, double F) {
		double latitude = attractionLatitude(g1, a, t);

		return F * Math.sin(latitude);

	}

	// retourne la velocity de la galaxie sur l'axe X
	public static double velocityX(Galaxy g1, int t)
	{
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity(t) - velocity;

		return velocity * Math.cos(Math.toRadians(g1.getSuperGalacticLon()));

	}

	// retourne la velocity de la galaxie sur l'axe Y
	public static double velocityY(Galaxy g1, int t) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity(t) - velocity;

		return velocity * Math.sin(Math.toRadians(g1.getSuperGalacticLon()));

	}

	// retourne la velocity de la galaxie sur l'axe Z
	public static double velocityZ(Galaxy g1, int t) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity(t) - velocity;

		return velocity * Math.sin(Math.toRadians(g1.getSuperGalacticLat()));
	}

	public static void coordByTime(Galaxy g1, double forceX, double forceY, double forceZ, int t) {
		double Ax = forceX;
		double Ay = forceY;
		double Az = forceZ;

		double Vx = velocityX(g1, t);
		double Vy = velocityY(g1, t);
		double Vz = velocityZ(g1, t);

		double time = t * Math.pow(10, 15);

		double x = (Ax * time * time) / 2 + time * Vx + g1.getCoordinate(t).getX();
		double y = (Ay * time * time) / 2 + time * Vy + g1.getCoordinate(t).getY();
		double z = (Az * time * time) / 2 + time * Vz + g1.getCoordinate(t).getZ();

		Vx = Ax * time + Vx;
		Vy = Ax * time + Vy;
		Vz = Ax * time + Vz;

		Math.pow(Vx, 2);
		Math.pow(Vy, 2);
		Math.pow(Vz, 2);

		double velocity = Math.sqrt(Vx + Vy + Vz);
		g1.addVelocity(velocity);

		Coordinate coord = new Coordinate(x, y, z);
		g1.addCoordinate(coord);
	}
}
