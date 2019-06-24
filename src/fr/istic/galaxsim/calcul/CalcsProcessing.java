package fr.istic.galaxsim.calcul;
/**
 * Classe permettant de calculer les differentes les positions dans le temps pour la simulation
 *
 * @author Lucas, Mathieu, Maxime
 */

import fr.istic.galaxsim.data.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.geometry.Point3D;

/**
 * Classe permettant de calculer les coordonnees des amas et des galaxies.
 *
 * Les calculs sont faits dans un autre thread pour ne pas bloquer
 * l'interface graphique.
 */
public class CalcsProcessing extends Task {

	/**
	 * Nombre de coordonnees pour chaque amas / galaxies
	 */
	public static final int T = 10;

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

		//tableau des amas trié dans l'ordre decroissant de masse
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
				double[] sumForce = { 0d, 0d, 0d };

				Point3D coord1 = a1.getCoordinate(t);

				// boucle pour calculer les forces entre l'amas actuel et les 100 amas les plus massifs
				for (int i = 0; i < Math.min(amas.length, 100); i++) {
					Amas a2 = amas[i];
					if (a1 == a2) {
						continue;
					}

					calcAmasForces(coord1, a2.getCoordinate(t), a1.getMass(), a2.getMass(), sumForce);
				}

				// boucle pour calculer les forces entre l'amas actuel et les 100 amas les plus proches
				for(int i = 0; i < Math.min(amasProche.length, 100); i++) {
					Amas a2 = amasProche[i];
					if (a1 == a2) {
						continue;
					}

					calcAmasForces(coord1, a2.getCoordinate(t), a1.getMass(), a2.getMass(), sumForce);
				}
				CalculAmas.coordByTime(a1, sumForce[0], sumForce[1], sumForce[2], t);
				increaseProgress();
			}

			// boucle pour traiter chaque galaxies
			for (Galaxy g : DataBase.tableGalaxies) {
				double[] sumForce = { 0d, 0d, 0d };

				Point3D coord1 = g.getCoordinate(t);

				// boucle pour calculer les forces entre la galaxie actuelle et les 100 amas les plus massifs
				for (int i = 0; i < Math.min(amas.length, 100); i++) {
					Amas a = amas[i];

					calcGalaxiesForces(coord1, a.getCoordinate(t), a.getMass(), sumForce);
				}

				// boucle pour calculer les forces entre la galaxie actuelle et les 100 amas les plus proches
				for (int i = 0; i < Math.min(amasProche.length, 100); i++) {
					Amas a = amasProche[i];

					calcGalaxiesForces(coord1, a.getCoordinate(t), a.getMass(), sumForce);
				}
				CalculGalaxies.coordByTime(g, sumForce[0], sumForce[1], sumForce[2], t);
				increaseProgress();
			}
		}

		return null;
	}

	private void calcAmasForces(Point3D coord1, Point3D coord2, double m1, double m2, double[] sumForce) {
		double lat = Calculations.attractionLatitude(coord1, coord2);
		double lon = Calculations.attractionLongitude(coord1, coord2);
		Point3D force = CalculAmas.forceAttraction(coord1, coord2, m1, m2);

		//calcForces(lat, lon, force, sumForce);
		sumForce[0] += force.getX();
		sumForce[1] += force.getY();
		sumForce[2] += force.getZ();
	}

	private void calcGalaxiesForces(Point3D coord1, Point3D coord2, double m, double[] sumForce) {
		double lat = Calculations.attractionLatitude(coord1, coord2);
		double lon = Calculations.attractionLongitude(coord1, coord2);
		Point3D force = CalculGalaxies.forceAttraction(coord1, coord2, m);

		sumForce[0] += force.getX();
		sumForce[1] += force.getY();
		sumForce[2] += force.getZ();

		//calcForces(lat, lon, force, sumForce);
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
			double Vx = Calculations.velocityX(g[i]);
			double Vy = Calculations.velocityY(g[i]);
			double Vz = Calculations.velocityZ(g[i]);
			g[i].addVelocity(new Point3D(Vx, Vy, Vz));
			Calculations.calculCoordInit(g[i]);
		}

		for (Amas a : DataBase.getAllAmas()) {
			double Vx = Calculations.velocityX(a);
			double Vy = Calculations.velocityY(a);
			double Vz = Calculations.velocityZ(a);
			a.addVelocity(new Point3D(Vx, Vy, Vz));
			Calculations.calculCoordInit(a);
		}
	}
}
