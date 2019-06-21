package fr.istic.galaxsim.calcul;
/**
 * classe permettant de calculer les différentes les positions dans le temps pour la simulation
 * @author unijere
 *
 */

import fr.istic.galaxsim.data.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

public class CalcsProcessing extends Task {

	public static final double Time =1 * Math.pow(10, 13);
	public static final double G = 6.67408 * Math.pow(10, -11);
	public static final double MsolaireEnKilo = 1.9891 * Math.pow(10, 42);
	public static final double MparsecEnMetre = 3.086 * Math.pow(10, 22);

	private static final int T = 10;

	/**
	 * Propriete d'avancement mise a jour apres chaque element traite
	 */
	public final DoubleProperty progressProperty = new SimpleDoubleProperty();

	/**
	 * Nombre d'elements a traiter
	 */
	private int elementsNumber;

	/**
	 * Nombre d'elements traites
	 */
	private int processedElements = 0;

	@Override
	protected Object call() throws Exception {
		progressProperty.set(0.0);

		//tri des amas par ordre decroissant de masse
		DataBase.sortAmas(DataBase.SORTING_MASS, true);

		//tableau des amas triÃ© dans l'ordre decroissant de masse
		Amas[] amas = DataBase.getAllAmas();

		//tri des amas par ordre croissant de distance
		DataBase.sortAmas(DataBase.SORTING_DISTANCE, false);	

		Amas[] amasProche = DataBase.getAllAmas();

		// Calcul du nombre d'elements a traiter :
		// chaque amas / galaxie possede T coordonnees
		elementsNumber = T * (amas.length + DataBase.tableGalaxies.size());

		// Calcul des coordonnees initiales
		initialCoordsCalculation();

		// boucle pour le nombre d'intervalle de coordonnees

		for (int t = 0; t < T; t++) {

			// boucle pour taiter chaque amas
			for (Amas a1 : amas) {
				double sumForceX = 0;
				double sumForceY = 0;
				double sumForceZ = 0;

				// boucle pour calculer les forces entre l'amas actuel et les 100 amas les plus massifs
				for (int i = 0; i < Math.min(amas.length, 100); i++) {
					Amas a2 = amas[i];
					if (a1 == a2) {
						continue;
					}

					double force = CalculAmas.forceAttraction(a1, a2, t);
					if (force != 0) {
						sumForceX += CalculAmas.forceX(a1, a2, t, force);
						sumForceZ += CalculAmas.forceZ(a1, a2, t, force);
					}
				}

				// boucle pour calculer les forces entre l'amas actuel et les 100 amas les plus proches
				for(int i = 0; i < Math.min(amasProche.length, 100); i++) {
					Amas a2 = amasProche[i];
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
				increaseProgress();
			}

			Galaxy[] g = DataBase.tableGalaxies.toArray(new Galaxy[DataBase.tableGalaxies.size()]);
			int nbGalaxies = DataBase.getNumberGalaxies();

			// boucle pour traiter chaque galaxies
			for ( int j = 0; j < nbGalaxies; j++) {
				double sumForceX = 0;
				double sumForceY = 0;
				double sumForceZ = 0;

				// boucle pour calculer les forces entre la galaxie actuelle et les 100 amas les plus massifs
				for (int i = 0; i < Math.min(amas.length, 100); i++) {
					Amas a = amas[i];
					double force = CalculGalaxies.forceAttraction(g[j], a, t);
					if (force != 0) {
						sumForceX += CalculGalaxies.forceX(g[j], a, t, force);
						sumForceY += CalculGalaxies.forceY(g[j], a, t, force);
						sumForceZ += CalculGalaxies.forceZ(g[j], a, t, force);
					}
				}

				// boucle pour calculer les forces entre la galaxie actuelle et les 100 amas les plus proches
				for (int i = 0; i < Math.min(amasProche.length, 100); i++) {
					Amas a = amasProche[i];
					double force = CalculGalaxies.forceAttraction(g[j], a, t);
					if (force != 0) {
						sumForceX += CalculGalaxies.forceX(g[j], a, t, force);
						sumForceY += CalculGalaxies.forceY(g[j], a, t, force);
						sumForceZ += CalculGalaxies.forceZ(g[j], a, t, force);
					}
				}
				CalculGalaxies.coordByTime(g[j], sumForceX, sumForceY, sumForceZ, t);
				increaseProgress();
			}
		}

		return null;
	}


	/**
	 * Met a jour la progression de la tache
	 * La valeur de processedElements est incrementee.
	 */
	private void increaseProgress() {
		progressProperty.setValue(++processedElements / (double) elementsNumber);
	}

	private void initialCoordsCalculation() {

		Galaxy[] g = DataBase.tableGalaxies.toArray(new Galaxy[DataBase.tableGalaxies.size()]);
		int nbGalaxies = DataBase.getNumberGalaxies();

		for (int i = 0; i< nbGalaxies; i++) {
			double Vx = CalculGalaxies.velocityX(g[i]);
			double Vy = CalculGalaxies.velocityY(g[i]);
			double Vz = CalculGalaxies.velocityZ(g[i]);
			g[i].addVelocity(new Vector(Vx, Vy, Vz));
			CalculGalaxies.calculCoordInit(g[i]);
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
