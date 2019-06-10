package fr.istic.galaxsim.calcul;

import fr.istic.galaxsim.data.*;

public class CalcsProcessing {

	private static final int T = 10;

	public static void process() {
		// Cette ligne ne sert theoriquement pas
		// DataBase.sortAmas(DataBase.SORTING_MASS, true);

		Amas[] amas = DataBase.getAllAmas();

		// boucle pour le nombre d'intervalle de coordonnees
		for (int t = 0; t < T; t++) {

			// boucle pour taiter chaque amas
			for (Amas a1 : amas) {
				double sumForceX = 0;
				double sumForceY = 0;
				double sumForceZ = 0;

				// boucle pour calculer les forces entre l'amas actuel et tous les autres
				for (int i = 0; i < Math.min(amas.length, 100); i++) {
					Amas a2 = amas[i];
					if (a1 == a2) {
						continue;
					}

					double force = CalculAmas.forceAttraction(a1, a2, t);
					if (force != 0) {
						sumForceX += CalculAmas.forceX(a1, a2, t, force);
						sumForceY += CalculAmas.forceY(a1, a2, t, force);
						sumForceZ += CalculAmas.forceZ(a1, a2, t, force);
					}
				}
				CalculAmas.coordByTime(a1, sumForceX, sumForceY, sumForceZ, t);
			}

			// boucle pour traiter chaque galaxies
			for (Galaxy g : DataBase.getAllGalaxies()) {
				double sumForceX = 0;
				double sumForceY = 0;
				double sumForceZ = 0;

				// boucle pour calculer les forces entre la galaxie actuelle et tous les autres
				// amas
				for (int i = 0; i < Math.min(amas.length, 100); i++) {
					Amas a = amas[i];
					double force = CalculGalaxies.forceAttraction(g, a, t);
					if (force != 0) {
						sumForceX += CalculGalaxies.forceX(g, a, t, force);
						sumForceY += CalculGalaxies.forceY(g, a, t, force);
						sumForceZ += CalculGalaxies.forceZ(g, a, t, force);
					}
				}
				CalculGalaxies.coordByTime(g, sumForceX, sumForceY, sumForceZ, t);
			}
		}
	}

	public static void coordsCalculation() {
		DataBase.sortAmas(DataBase.SORTING_MASS, true);
		for (Galaxy g : DataBase.getAllGalaxies()) {
			double Vx = CalculGalaxies.velocityX(g);
			double Vy = CalculGalaxies.velocityY(g);
			double Vz = CalculGalaxies.velocityZ(g);
			g.addVelocity(new Vector(Vx, Vy, Vz));
			CalculGalaxies.calculCoordInit(g);
		}

		for (Amas a : DataBase.getAllAmas()) {
			double Vx = CalculAmas.velocityX(a);
			double Vy = CalculAmas.velocityY(a);
			double Vz = CalculAmas.velocityZ(a);
			a.addVelocity(new Vector(Vx, Vy, Vz));
			CalculAmas.calculCoordInit(a);
		}
	}
}
