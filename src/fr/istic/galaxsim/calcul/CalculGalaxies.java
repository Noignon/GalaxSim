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

	/**
	 * calcul des coordonnee de la galaxie g1 au temps t=0
	 * 
	 * @param g1 galaxie
	 */
	
	public static void calculCoordInit(Galaxy g1) {
		// calcul des coordonnees
		double z = Math.sin(Math.toRadians(g1.getSuperGalacticLat())) * g1.getDistance();
		double hypothenus = Math.cos(Math.toRadians(g1.getSuperGalacticLat())) * g1.getDistance();
		double x = Math.cos(Math.toRadians(g1.getSuperGalacticLon())) * hypothenus;
		double y = Math.sin(Math.toRadians(g1.getSuperGalacticLon())) * hypothenus;

		// enregistrement des donnees initiales
		g1.addCoordinate(new Vector(x, y, z));
	}

	/**
	 * 
	 * @param g1 galaxie
	 * @param a amas etant parmi les plus massif
	 * @param t indicateur de temps
	 * @return la force d'attraction qu'exerce l'amas a sur la galaxie g1
	 */
	
	public static double forceAttraction(Galaxy g1, Amas a, int t) {

		// distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		double x = Math.pow(a.getCoordinate(t).getX() - g1.getCoordinate(t).getX(), 2);
		double y = Math.pow(a.getCoordinate(t).getY() - g1.getCoordinate(t).getY(), 2);
		double z = Math.pow(a.getCoordinate(t).getZ() - g1.getCoordinate(t).getZ(), 2);
		double distance = x + y + z;
		
		// Force de gravitation = (G * Masse1 * Masse2) / distance^2
		distance = distance * CalcsProcessing.MparsecEnMetre * CalcsProcessing.MparsecEnMetre;
		double m1 = a.getMass() * CalcsProcessing.MsolaireEnKilo;
		double F = (CalcsProcessing.G * m1) / distance;

		return F;
	}

	/**
	 * 
	 * @param g1 galaxie 
	 * @param a amas parmi les plus massif
	 * @param t indicateur de temps
	 * @return l'angle longitude que forme le vecteur force entre a et g1
	 */
	
	public static double attractionLongitude(Galaxy g1, Amas a, int t) {
		double x = a.getCoordinate(t).getX() - g1.getCoordinate(t).getX();
		double y = a.getCoordinate(t).getY() - g1.getCoordinate(t).getY();

		if(x < 0 && y < 0 ) {
            return -Math.PI + Math.atan(y/x);
        }
        
        if((x > 0 && y < 0) || (x > 0 && y >= 0)) {
            return Math.atan(y/x);
        }
        
        if(x < 0 && y > 0) {
            return Math.PI/2 + Math.atan((-x)/y);
        }
        
        if(x == 0 && y < 0) {
            return -Math.PI/2;
        }
        
        if(x == 0 && y > 0) {
            return Math.PI/2;
        }
        
        return 0.0;
	}

	/**
	 * 
	 * @param g1 galaxie 
	 * @param a amas parmi les plus massif
	 * @param t indicateur de temps
	 * @return l'angle latitude que forme le vecteur force entre a et g1
	 */
	
	public static double attractionLatitude(Galaxy g1, Amas a, int t) {
		double x = Math.pow(a.getCoordinate(t).getX() - g1.getCoordinate(t).getX(), 2);
		double y = Math.pow(a.getCoordinate(t).getY() - g1.getCoordinate(t).getY(), 2);
		double z = a.getCoordinate(t).getZ() - g1.getCoordinate(t).getZ();
		double hypothenus = Math.sqrt(x + y);

		if(hypothenus < 0 && z < 0 ) {
            return -Math.PI + Math.atan(z/hypothenus);
        }
        
        if((hypothenus > 0 && z < 0) || (hypothenus > 0 && z >= 0)) {
            return Math.atan(z/hypothenus);
        }
        
        if(hypothenus < 0 && z > 0) {
            return Math.PI/2 + Math.atan((-hypothenus)/z);
        }
        
        if(hypothenus == 0 && z < 0) {
            return -Math.PI/2;
        }
        
        if(hypothenus == 0 && z > 0) {
            return Math.PI/2;
        }
        
        return 0.0;
	}

	/**
	 * 
	 * @param g1 galaxie
	 * @param a amas parmi les plus massif
	 * @param t indicateur de temps
	 * @param F force d'attraction entre la galaxie g1 et l'amas a, calculee par la fonction forceAttraction
	 * @return force d'attraction exercee entre g1 et a sur l'axe X
	 */
	
	public static double forceX(Galaxy g1, Amas a, int t, double F) {
		double longitude = attractionLongitude(g1, a, t);
		double latitude = attractionLatitude(g1, a, t);
		F = F * Math.cos(latitude);

		return F * Math.cos(longitude);
	}

	/**
	 * 
	 * @param g1 galaxie
	 * @param a amas parmi les plus massif
	 * @param t indicateur de temps
	 * @param F force d'attraction entre la galaxie g1 et l'amas a, calculee par la fonction forceAttraction
	 * @return force d'attraction exercee entre g1 et a sur l'axe Y
	 */
	
	public static double forceY(Galaxy g1, Amas a, int t, double F) {
		double longitude = attractionLongitude(g1, a, t);
		double latitude = attractionLatitude(g1, a, t);
		F = F * Math.cos(latitude);

		return F * Math.sin(longitude);
	}

	/**
	 * 
	 * @param g1 galaxie
	 * @param a amas parmi les plus massif
	 * @param t indicateur de temps
	 * @param F force d'attraction entre la galaxie g1 et l'amas a, calculee par la fonction forceAttraction
	 * @return force d'attraction exercee entre g1 et a sur l'axe Z
	 */
	
	public static double forceZ(Galaxy g1, Amas a, int t, double F) {
		double latitude = attractionLatitude(g1, a, t);

		return F * Math.sin(latitude);
	}

	/**
	 * 
	 * @param g1 galaxie
	 * @return vitesse initiale de la galaxie sur l'axe X
	 */
	
	public static double velocityX(Galaxy g1) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity() - velocity;
		velocity = Math.cos(Math.toRadians(g1.getSuperGalacticLat())) * velocity;

		return velocity * Math.cos(Math.toRadians(g1.getSuperGalacticLon()));
	}

	/**
	 * 
	 * @param g1 galaxie
	 * @return vitesse initiale de la galaxie sur l'axe Y
	 */
	
	public static double velocityY(Galaxy g1) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity() - velocity;
		velocity = Math.cos(Math.toRadians(g1.getSuperGalacticLat())) * velocity;

		return velocity * Math.sin(Math.toRadians(g1.getSuperGalacticLon()));

	}

	/**
	 * 
	 * @param g1 galaxie
	 * @return vitesse initiale de la galaxie sur l'axe Z
	 */
	
	public static double velocityZ(Galaxy g1) {
		double velocity = 71 * g1.getDistance();
		velocity = g1.getVelocity() - velocity;

		return velocity * Math.sin(Math.toRadians(g1.getSuperGalacticLat()));
	}

	/**
	 * Calcul des coordonnees de la galaxie g1, ainsi que de sa vitesse, au temps t+1
	 * 
	 * @param g1 galaxie
	 * @param forceX somme des forces d'attaction exercee sur a1 par les amas les plus massif par rapport a l'axe X
	 * @param forceY somme des forces d'attaction exercee sur a1 par les amas les plus massif par rapport a l'axe Y
	 * @param forceZ somme des forces d'attaction exercee sur a1 par les amas les plus massif par rapport a l'axe Z
	 * @param t indicateur de temps
	 */
	
	public static void coordByTime(Galaxy g1, double forceX, double forceY, double forceZ, int t) {
		
		double Ax = forceX;
		double Ay = forceY;
		double Az = forceZ;

		double Vx = g1.getVelocity(t).getX();
		double Vy = g1.getVelocity(t).getY();
		double Vz = g1.getVelocity(t).getZ();

		double time = t * CalcsProcessing.Time;
		
		Vx = (Ax * time) * 1000 + Vx;
		Vy = (Ay * time) * 1000 + Vy;
		Vz = (Az * time) * 1000 + Vz;
		
		g1.addVelocity(new Vector(Vx, Vy, Vz));

		double kilometreEnMparsec = 1000 / CalcsProcessing.MparsecEnMetre;
		
		double x = (time * Vx) * kilometreEnMparsec + g1.getCoordinate(t).getX();
		double y = (time * Vy) * kilometreEnMparsec + g1.getCoordinate(t).getY();
		double z = (time * Vz) * kilometreEnMparsec + g1.getCoordinate(t).getZ();

		g1.addCoordinate(new Vector(x, y, z));
	}
}
