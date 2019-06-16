package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.Amas;
import fr.istic.galaxsim.data.Vector;

public class CalculAmas {
	/**
	 *
	 * Classe permettant de realiser les calculs de position pour les galaxies et
	 * amas de galaxies
	 *
	 */

	/**
	 * Calcul des coordonnees de l'amas au temps 0
	 * 
	 * @param a1 l'amas principal
	 */
	
	public static void calculCoordInit(Amas a1) {
		// calcul des coordonnees
		double z = Math.sin(Math.toRadians(a1.getSuperGalacticLat())) * a1.getDistance();
		double hypothenus = Math.cos(Math.toRadians(a1.getSuperGalacticLat())) * a1.getDistance();
		double x = Math.cos(Math.toRadians(a1.getSuperGalacticLon())) * hypothenus;
		double y = Math.sin(Math.toRadians(a1.getSuperGalacticLon())) * hypothenus;

		// enregistrement des donnees initiales
		a1.addCoordinate(new Vector(x, y, z));
	}

	/**
	 * 
	 * @param a1 amas principal
	 * @param a2 autre amas etant parmi les plus massif
	 * @param t indicateur de temps
	 * @return la force d'attraction qu'excercent les deux amas l'un envers l'autre
	 */
	
	public static double forceAttraction(Amas a1, Amas a2, int t) {

		// distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		double x = Math.pow(a1.getCoordinate(t).getX() - a2.getCoordinate(t).getX(), 2);
		double y = Math.pow(a1.getCoordinate(t).getY() - a2.getCoordinate(t).getY(), 2);
		double z = Math.pow(a1.getCoordinate(t).getZ() - a2.getCoordinate(t).getZ(), 2);
		double distance = x + y + z;

		// Force de gravitation = (G * Masse1 * Masse2) / distance^2
		distance = distance * CalcsProcessing.MparsecEnMetre * CalcsProcessing.MparsecEnMetre;
		double m1 = a1.getMass() * CalcsProcessing.MsolaireEnKilo;
		double m2 = a2.getMass() * CalcsProcessing.MsolaireEnKilo;
		double F = (CalcsProcessing.G * m1 * m2) / distance;

		return F;
	}

	/**
	 * 
	 * @param a1 amas principal
	 * @param a2 autre amas etant parmi les plus massif
	 * @param t indicateur de temps
	 * @return l'angle longitude que forme le vecteur force entre a1 et a2
	 */
	
	public static double attractionLongitude(Amas a1, Amas a2, int t) {
        double x = a2.getCoordinate(t).getX() - a1.getCoordinate(t).getX();
        double y = a2.getCoordinate(t).getY() - a1.getCoordinate(t).getY();
        
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
	 * @param a1 amas principal
	 * @param a2 autre amas etant parmi les plus massif
	 * @param t indicateur de temps
	 * @return l'angle latitude que forme le vecteur force entre a1 et a2
	 */
	
	public static double attractionLatitude(Amas a1, Amas a2, int t) {
		double x = Math.pow(a1.getCoordinate(t).getX() - a2.getCoordinate(t).getX(), 2);
		double y = Math.pow(a1.getCoordinate(t).getY() - a2.getCoordinate(t).getY(), 2);
		double z = a1.getCoordinate(t).getZ() - a2.getCoordinate(t).getZ();
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
	 * @param a1 amas principal
	 * @param a2 autre amas etant parmi les plus massif
	 * @param t indicateur de temps
	 * @param F force d'attraction exercee entre a1 et a2, calculee par la fonction forceAttraction
	 * @return force d'attraction exercee entre a1 et a2 sur l'axe X
	 */
	
	public static double forceX(Amas a1, Amas a2, int t, double F) {
		double longitude = attractionLongitude(a1, a2, t);
		double latitude = attractionLatitude(a1, a2, t);
		F = F * Math.cos(latitude);
		
		return F * Math.cos(longitude);
	}

	/**
	 * 
	 * @param a1 amas principal
	 * @param a2 autre amas etant parmi les plus massif
	 * @param t indicateur de temps
	 * @param F force d'attraction exercee entre a1 et a2, calculee par la fonction forceAttraction
	 * @return force d'attraction exercee entre a1 et a2 sur l'axe Y
	 */
	
	public static double forceY(Amas a1, Amas a2, int t, double F) {
		double longitude = attractionLongitude(a1, a2, t);
		double latitude = attractionLatitude(a1, a2, t);
		F = F * Math.cos(latitude);

		return F * Math.sin(longitude);
	}

	/**
	 * 
	 * @param a1 amas principal
	 * @param a2 autre amas etant parmi les plus massif
	 * @param t indicateur de temps
	 * @param F force d'attraction exercee entre a1 et a2, calculee par la fonction forceAttraction
	 * @return force d'attraction exercee entre a1 et a2 sur l'axe Z
	 */
	
	public static double forceZ(Amas a1, Amas a2, int t, double F) {
		double latitude = attractionLatitude(a1, a2, t);

		return F * Math.sin(latitude);
	}

	/**
	 * 
	 * @param a1 amas principal
	 * @return vitesse initiale de l'amas sur l'axe X
	 */
	
	public static double velocityX(Amas a1) {
		double velocity = 71 * a1.getDistance();
		velocity = a1.getVelocity() - velocity;
		velocity = Math.cos(Math.toRadians(a1.getSuperGalacticLat())) * velocity;

		return velocity * Math.cos(Math.toRadians(a1.getSuperGalacticLon()));
	}

	/**
	 * 
	 * @param a1 amas principal
	 * @return vitesse initiale de l'amas sur l'axe Y
	 */
	
	public static double velocityY(Amas a1) {
		double velocity = 71 * a1.getDistance();
		velocity = a1.getVelocity() - velocity;
		velocity = Math.cos(Math.toRadians(a1.getSuperGalacticLat())) * velocity;

		return velocity * Math.sin(Math.toRadians(a1.getSuperGalacticLon()));
	}

	/**
	 * 
	 * @param a1 amas principal
	 * @return vitesse initiale de l'amas sur l'axe Z
	 */
	
	public static double velocityZ(Amas a1) {
		double velocity = 71 * a1.getDistance();
		velocity = a1.getVelocity() - velocity;

		return velocity * Math.sin(Math.toRadians(a1.getSuperGalacticLat()));
	}

	/**
	 * Calcul des coordonnees de l'amas a1, ainsi que de sa vitesse, au temps t+1
	 * 
	 * @param a1 amas principal
	 * @param forceX somme des forces d'attaction exercee sur a1 par les autres amas par rapport a l'axe X
	 * @param forceY somme des forces d'attaction exercee sur a1 par les autres amas par rapport a l'axe Y
	 * @param forceZ somme des forces d'attaction exercee sur a1 par les autres amas par rapport a l'axe Z
	 * @param t indicateur de temps
	 */
	
	public static void coordByTime(Amas a1, double forceX, double forceY, double forceZ, int t) {
		double divMass = 1/a1.getMass();
		
		double Ax = forceX * divMass;
		double Ay = forceY * divMass;
		double Az = forceZ * divMass;

		double Vx = a1.getVelocity(t).getX();
		double Vy = a1.getVelocity(t).getY();
		double Vz = a1.getVelocity(t).getZ();

		double time = t * CalcsProcessing.Time;
		
		Vx = (Ax * time) * 1000 + Vx;
		Vy = (Ay * time) * 1000 + Vy;
		Vz = (Az * time) * 1000 + Vz;
		
		a1.addVelocity(new Vector(Vx, Vy, Vz));

		double kilometreEnMparsec = 1000 / CalcsProcessing.MparsecEnMetre;
		
		double x = (time * Vx) * kilometreEnMparsec + a1.getCoordinate(t).getX();
		double y = (time * Vy) * kilometreEnMparsec + a1.getCoordinate(t).getY();
		double z = (time * Vz) * kilometreEnMparsec + a1.getCoordinate(t).getZ();

		a1.addCoordinate(new Vector(x, y, z));
	}
}
