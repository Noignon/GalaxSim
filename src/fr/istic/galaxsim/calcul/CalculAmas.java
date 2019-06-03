package fr.istic.galaxsim.calcul;
import fr.istic.galaxsim.data.Amas;
import fr.istic.galaxsim.data.Coordinate;

public class CalculAmas {

	/**
	*
	*	Classe permettant de réaliser les calculs de position
	* pour les galaxies et amas de galaxies
	*
	*/

			//private static final double masseRef = 0.0;
			//private static final double distanceRef = 0.0;

			// calcule les coordonnées initiales d'une galaxie
		  public static void calculCoordInit(Amas a1)
		  {
				//variables coordonnées
				double x;
				double y;
				double z;

				//calcul des coordonnées
			 	z = Math.sin(Math.toRadians(a1.getSuperGalacticLat())) * a1.getDistance();
			 	double hypothenus = Math.cos(Math.toRadians(a1.getSuperGalacticLat())) * a1.getDistance();
			 	x = Math.cos(Math.toRadians(a1.getSuperGalacticLon())) * hypothenus;
			 	y = Math.sin(Math.toRadians(a1.getSuperGalacticLon())) * hypothenus;

				//enregistrement des données initiales
				Coordinate coord = new Coordinate(x, y, z);
				a1.addCoordinate(coord);
		  }

			// calcule la force d'attraction entre deux galaxies
		  public static double forceAttraction(Amas a1, Amas a2, int t)
		  {
				/*if(a2.getMass() < masseRef/1000)
				{
					return 0;
				}*/

		    // distance = racine carre de ((x1 + x2)^2 + (y1 + y2)^2 + (z1 + z2)^2)
		    double x = Math.pow(a1.getCoordinate(t).getX() - a2.getCoordinate(t).getX(), 2);
		    double y = Math.pow(a1.getCoordinate(t).getY() - a2.getCoordinate(t).getY(), 2);
		    double z = Math.pow(a1.getCoordinate(t).getZ() - a2.getCoordinate(t).getZ(), 2);
		    double distance = Math.sqrt(x + y + z);

			/*	if( distance < distanceRef / 35)
				{
					return 0;
				}*/

		    // Force de gravitation = (G * Masse1 * Masse2) / distance^2
		    distance = distance * 3.085677581 * Math.pow(10, 22);
		    distance = Math.pow(distance, 2);
		    double G = 6.67408 * Math.pow(10, -11);
		    double m1 = a1.getMass() * 1.991 * Math.pow(10, 42);
		    double m2 = a2.getMass() * 1.991 * Math.pow(10, 42);
		    double F = (G * m1 * m2) / distance;

		    return F;
		  }

			//calcule la longitude entre deux galaxies
			public static double longitudeAttraction(Amas a1, Amas a2, int t)
		 	{
				double x = a2.getCoordinate(t).getX() - a1.getCoordinate(t).getX();
		    double y = a2.getCoordinate(t).getY() - a1.getCoordinate(t).getY();

				return Math.atan(x/y);
		 	}

			//calcule la latitude entre deux galaxies
			public static double latitudeAttraction(Amas a1, Amas a2, int t)
		 	{
				double x = Math.pow(a2.getCoordinate(t).getX() - a1.getCoordinate(t).getX(), 2);
		    double y = Math.pow(a2.getCoordinate(t).getY() - a1.getCoordinate(t).getY(), 2);
				double z = a2.getCoordinate(t).getZ() - a1.getCoordinate(t).getZ();
				double hypothenus = Math.sqrt(x + y);

				return Math.atan(z/hypothenus);
		 	}

			//retourne la force d'attraction entre deux galaxies sur l'axe X
			public static double forceX(Amas a1, Amas a2, int t, double F)
			{
				double longitude = longitudeAttraction(a1, a2, t);

				return F * Math.cos(longitude);
			}

			//retourne la force d'attraction entre deux galaxies sur l'axe Y
			public static double forceY(Amas a1, Amas a2, int t, double F)
			{
				double longitude = longitudeAttraction(a1, a2, t);

				return F * Math.sin(longitude);
			}

			//retourne la force d'attraction entre deux galaxies sur l'axe Z
			public static double forceZ(Amas a1, Amas a2, int t, double F)
			{
				double latitude = latitudeAttraction(a1, a2, t);

				return F * Math.sin(latitude);
			}

			//retourne la vitesse de la galaxie sur l'axe X
			public static double vitesseX(Amas a1, double sommeForceX, double sommeForceY)
			{
				double angle;
				double vitesse = 71 * a1.getDistance();
				vitesse = a1.getVelocity() - vitesse;

				if((sommeForceX > 0 && sommeForceY >= 0) || (sommeForceX < 0 && sommeForceY <= 0))
				{
					angle = Math.atan(sommeForceY/sommeForceX);
					return vitesse* Math.cos(angle);
				}
				
				else
				{
					angle = Math.atan(sommeForceX/sommeForceY);
					return vitesse* Math.cos(angle);
				}
			
			}

			//retourne la vitesse de la galaxie sur l'axe Y
			public static double vitesseY(Amas a1, double sommeForceX, double sommeForceY)
			{
				double angle;
				double vitesse = 71 * a1.getDistance();
				vitesse = a1.getVelocity() - vitesse;

				if( (sommeForceX > 0 && sommeForceY >= 0) || (sommeForceX < 0 && sommeForceY <= 0) )
				{
					angle = Math.atan(sommeForceY/sommeForceX);
					return vitesse* Math.sin(angle);
				}
				
				else
				{
					angle = Math.atan(sommeForceX/sommeForceY);
					return vitesse* Math.sin(angle);
				}
				
				//return 0.0;
			}

			//retourne la vitesse de la galaxie sur l'axe Z
			public static double vitesseZ(Amas a1, double sommeForceX, double sommeForceY, double sommeForceZ)
			{
				double angle;
				double vitesse = 71 * a1.getDistance();
				vitesse = a1.getVelocity() - vitesse;

				double x = Math.pow(sommeForceX,2);
				double y = Math.pow(sommeForceY,2);

				double Zprim = Math.sqrt(x+y);

				if( (Zprim > 0 && sommeForceZ >= 0) || (Zprim < 0 && sommeForceZ <= 0) )
				{
					angle = Math.atan(sommeForceZ/Zprim);
					return vitesse* Math.sin(angle);
				}
				else
				{
					angle = Math.atan(Zprim/sommeForceZ);
					return vitesse* Math.sin(angle);
				}
			}

			public static void coordByTime(Amas a1, double sommeForceX, double sommeForceY, double sommeForceZ, int t)
			{
				double Ax = sommeForceX / a1.getMass();
				double Ay = sommeForceY / a1.getMass();
				double Az = sommeForceZ / a1.getMass();

				double Vx = vitesseX(a1, sommeForceX, sommeForceY);
				double Vy = vitesseY(a1, sommeForceX, sommeForceY);
				double Vz = vitesseZ(a1, sommeForceX, sommeForceY, sommeForceZ);

				double time = t * Math.pow(10, 7);

				double x = (Ax * time * time)/2 + time*Vx + a1.getCoordinate(t).getX();
				double y = (Ay * time * time)/2 + time*Vy + a1.getCoordinate(t).getY();
				double z = (Az * time * time)/2 + time*Vz + a1.getCoordinate(t).getZ();

				Coordinate coord = new Coordinate(x, y, z);
				a1.addCoordinate(coord);
			}
		}
