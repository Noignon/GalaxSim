package fr.istic.galaxsim.data;

/**
 * classe permettant de définir un amas
 * @author anaofind
 *
 */
public class Amas extends CosmosElement{

	/**
	 * la masse en TMsun
	 */
	private double mass; 
	
	/**
	 * le nombre de galaxies présent dans l'amas
	 */
	private int nbGalaxies;
	
	/**
	 * constructeur
	 * @param ident l'identifiant
	 * @param distance la distance
	 * @param velocity la vitesse
	 * @param deviationUncertainty l'écart d'incertitude
	 * @param mass la masse de l'amas
	 * @param nbGalaxies le nombre de galaxies
	 */
	public Amas(int ident, double distance, double velocity, double deviationUncertainty, double mass, int nbGalaxies) {
		super(ident, distance, velocity, deviationUncertainty);
		this.mass = mass;
		this.nbGalaxies = nbGalaxies;
	}

	/**
	 * getter masse 
	 * @return la masse
	 */
	public double getMass() {
		return mass;
	}
	
	/**
	 * getter nbGalaxies
	 * @return nbGalaxies
	 */
	public int getNbGalaxies() {
		return nbGalaxies;
	}

	/**
	 * methode permettant de creer un amas à partir d'un taleau de données
	 * @param datas le tableau de donnéees
	 * @return le nouvel amas
	 */
	public static Amas create(String datas[]){
		if (datas.length == 12){
			String stringId = datas[0];
			String stringDist = datas[2];
			String stringGLon = datas[3];
			String stringGLat = datas[4];
			String stringSGLon = datas[5];
			String stringSGLat = datas[6];
			String stringVelo = datas[7];
			String stringMass = datas[8];
			String stringNbGalaxies = datas[9];
			String stringDeviationUncertainty = datas[10];
			String name = datas[11];
			
			int id = -1;
			double dist = -1;
			double velo = -1;
			double mass = -1;
			int nbGalaxies = 1;
			double deviationUncertainty = 0;
			
			if (stringId != null) {
				id = Integer.parseInt(stringId);
			}
			if (stringDist != null){
				dist = Double.parseDouble(stringDist);
			}
			if (stringVelo != null){
				velo = Double.parseDouble(stringVelo);
			}
			if (stringMass != null){
				mass = Double.parseDouble(stringMass);
			}
			if (stringNbGalaxies != null){
				nbGalaxies = Integer.parseInt(stringNbGalaxies);
			}
			if (stringDeviationUncertainty != null){
				deviationUncertainty = Double.parseDouble(stringDeviationUncertainty);
			}
			
			Amas newAmas = new Amas(id, dist, velo, deviationUncertainty,  mass, nbGalaxies);
			newAmas.setName(name);
			
			if (stringGLon != null && stringGLat != null){
				double GLon = Double.parseDouble(stringGLon);
				double GLat = Double.parseDouble(stringGLat);
				newAmas.setGalacticLongLat(GLon, GLat);
			}
			
			if (stringSGLon != null && stringSGLat != null){
				double SGLon = Double.parseDouble(stringSGLon);
				double SGLat = Double.parseDouble(stringSGLat);
				newAmas.setSuperGalacticLongLat(SGLon, SGLat);
			}
			
			return newAmas;
		}
		
		return null;
	}
	
	/**
	 * methode permettant de transformer un amas en galaxy
	 * @return la transformation en galaxy
	 */
	public Galaxy transformInGalaxy(){
		Galaxy g = new Galaxy(this.getIdent(), this.getDistance(), this.getVelocity(), this.getDeviationUncertainty(), null,this.getMass());
		g.setGalacticLongLat(this.getGalacticLon(), this.getGalacticLat());
		g.setSuperGalacticLongLat(this.getSuperGalacticLon(), this.getSuperGalacticLat());
		
		return g;
	}
	
	/**
	 * methode toString
	 */
	public String toString(){
		return super.toString() + " | masse: " + mass + " | " + "nbGalaxies: " + nbGalaxies;
	}
}
