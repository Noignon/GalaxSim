package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.Amas;
import fr.istic.galaxsim.data.Vector;
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
		// calcul des coordonnees
		double z = Math.sin(Math.toRadians(g1.getSuperGalacticLat())) * g1.getDistance();
		double hypothenus = Math.cos(Math.toRadians(g1.getSuperGalacticLat())) * g1.getDistance();
		double x = Math.cos(Math.toRadians(g1.getSuperGalacticLon())) * hypothenus;
		double y = Math.sin(Math.toRadians(g1.getSuperGalacticLon())) * hypothenus;

		// enregistrement des donnees initiales
		g1.addCoordinate(new Vector(x, y, z));
	}

	// calcule la force d'attraction entre deux galaxies
	public static double forceAttraction(Galaxy g1, Amas a, int t) {

		// distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		double x = Math.pow(a.getCoordinate(t).getX() - g1.getCoordinate(t).getX(), 2);
		double y = Math.pow(a.getCoordinate(t).getY() - g1.getCoordinate(t).getY(), 2);
		double z = Math.pow(a.getCoordinate(t).getZ() - g1.getCoordinate(t).getZ(), 2);
		double distance = x + y + z;
		
		// Force de gravitation = (G * Masse1 * Masse2) / distance^2
		distance = distance * 9.521 * Math.pow(10, 44);
		double G = 6.67408 * Math.pow(10, -11);
		double m1 = a.getMass() * 1.991 * Math.pow(10, 42);
		double F = (G * m1) / distance;

		return F;
	}

	// calcule la longitude entre deux galaxies
	public static double attractionLongitude(Galaxy g1, Amas a, int t) {
		double x = a.getCoordinate(t).getX() - g1.getCoordinate(t).getX();
		double y = a.getCoordinate(t).getY() - g1.getCoordinate(t).getY();

		return Math.acos(x/(Math.sqrt((x*x) + (y*y))));
	}

	// calcule la latitude entre deux galaxies
	public static double attractionLatitude(Galaxy g1, Amas a, int t) {
		double x = Math.pow(a.getCoordinate(t).getX() - g1.getCoordinate(t).getX(), 2);
		double y = Math.pow(a.getCoordinate(t).getY() - g1.getCoordinate(t).getY(), 2);
		double z = a.getCoordinate(t).getZ() - g1.getCoordinate(t).getZ();
		double hypothenus = Math.sqrt(x + y);

		return Math.acos(hypothenus/(Math.sqrt((hypothenus*hypothenus) + (z*z))));
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe X
	public static double forceX(Galaxy g1, Amas a, int t, double F) {
		double longitude = attractionLongitude(g1, a, t);
		F = F * Math.cos(longitude);

		return F * Math.cos(longitude);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe Y
	public static double forceY(Galaxy g1, Amas a, int t, double F) {
		double longitude = attractionLongitude(g1, a, t);
		F = F * Math.cos(longitude);

		return F * Math.sin(longitude);
	}

	// retourne la force d'attraction entre deux galaxies sur l'axe Z
	public static double forceZ(Galaxy g1, Amas a, int t, double F) {
		double latitude = attractionLatitude(g1, a, t);

		return F * Math.sin(latitude);

	}

	// retourne la velocity de la galaxie sur l'axe X
	public static double velocityX(Galaxy g1) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity() - velocity;
		velocity = Math.cos(Math.toRadians(g1.getSuperGalacticLat())) * velocity;

		return velocity * Math.cos(Math.toRadians(g1.getSuperGalacticLon()));

	}

	// retourne la velocity de la galaxie sur l'axe Y
	public static double velocityY(Galaxy g1) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity() - velocity;
		velocity = Math.cos(Math.toRadians(g1.getSuperGalacticLat())) * velocity;

		return velocity * Math.sin(Math.toRadians(g1.getSuperGalacticLon()));

	}

	// retourne la velocity de la galaxie sur l'axe Z
	public static double velocityZ(Galaxy g1) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity() - velocity;

		return velocity * Math.sin(Math.toRadians(g1.getSuperGalacticLat()));
	}
/**
 * permet de fepzj pa	z
 * @param g1 
 * @param forceX
 * @param forceY
 * @param forceZ
 * @param t
 * 
 */
	public static void coordByTime(Galaxy g1, double forceX, double forceY, double forceZ, int t) {
		double cons = 1;
	/**	if (g1.getAmasMass() > 0) {
			cons = 1/(g1.getAmasMass()*1.991*Math.pow(10,42));
		} 
		else {
			cons = 1/(1.991*Math.pow(10,42));
		}*/
		
		double Ax = forceX * cons;
		double Ay = forceY * cons;
		double Az = forceZ * cons;

		double Vx = g1.getVelocity(t).getX();
		double Vy = g1.getVelocity(t).getY();
		double Vz = g1.getVelocity(t).getZ();

		double time = t * Math.pow(10, 14);
		
		Vx = (Ax * time) * 1000 + Vx;
		Vy = (Ay * time) * 1000 + Vy;
		Vz = (Az * time) * 1000 + Vz;

		double x = (time * Vx) * 3.2408 *Math.pow(10, -20) + g1.getCoordinate(t).getX();
		double y = (time * Vy) * 3.2408 *Math.pow(10, -20) + g1.getCoordinate(t).getY();
		double z = (time * Vz) * 3.2408 *Math.pow(10, -20) + g1.getCoordinate(t).getZ();
		
		
		g1.addVelocity(new Vector(Vx,Vy,Vz));
		
		g1.addCoordinate(new Vector(x, y, z));
	}
}
