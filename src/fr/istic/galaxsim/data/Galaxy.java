package fr.istic.galaxsim.data;

/**
 * classe permettant de définir une galaxie
 * @author anaofind
 *
 */
public class Galaxy extends CosmosElement{

	/**
	 * l'identifiant de l'amas de la galaxie
	 */
	private int amasIdent;
		
	/**
	 * le type de la galaxie
	 */
	private String type;
	
	/**
	 * la masse de l'amas associé
	 */
	private double amasMass;
	
	/**
	 * constructeur
	 * @param ident l'identifiant
	 * @param distance la distance
	 * @param velocity la vitesse
	 * @param deviationUncertainty l'écart d'incertitude
	 * @param type le type de la galaxy
	 * @param amasMass la masse de l'amas associé
	 */
	public Galaxy(int ident, double distance, double velocity, double deviationUncertainty, String type, double amasMass) {
		super(ident, distance, velocity, deviationUncertainty);
		this.type = type;
		this.amasMass = amasMass;
	}

	/**
	 * getter identifiant de l'amas
	 * @return l'identifiant de l'amas
	 */
	public int getAmasIdent() {
		return amasIdent;
	}

	/**
	 * setter identifiant de l'amas
	 * @param amasIdent l'identifiant de l'amas
	 */
	public void setAmasIdent(int amasIdent) {
		this.amasIdent = amasIdent;
	}

	/**
	 * getter type
	 * @return le type de la galaxie
	 */
	public String getType() {
		return type;
	}

	/**
	 * getter amasMass
	 * @return amasMass
	 */
	public double getAmasMass() {
		return amasMass;
	}

	/**
	 * methode permettant de creer une galaxy à partir d'un taleau de données
	 * @param datas le tableau de donnéees
	 * @return la nouvelle galaxy
	 */
	public static Galaxy create(String[] datas){
		if (datas.length == 11){
			String stringId = datas[0];
			String name = datas[1];
			String type = datas[2];
			String stringIdAmas = datas[3];
			String stringDist = datas[4];
			String stringGLon = datas[5];
			String stringGLat = datas[6];
			String stringSGLon = datas[7];
			String stringSGLat = datas[8];
			String stringVelo = datas[9];
			String stringMassAmas = datas[10];
			String stringDeviationUncertainty = datas[11];
			
			int id = -1;
			double dist = -1;
			double velo = -1;
			int idAmas = -1;
			double massAmas = 0;
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
			if (stringIdAmas != null){
				idAmas = Integer.parseInt(stringIdAmas);
			}
			if (stringMassAmas != null){
				massAmas = Double.parseDouble(stringMassAmas);
			}
			if (stringDeviationUncertainty != null){
				deviationUncertainty = Double.parseDouble(stringDeviationUncertainty);
			}
			
			Galaxy newGalaxy = new Galaxy(id, dist, velo, deviationUncertainty, type, massAmas);
			
			newGalaxy.setName(name);
			newGalaxy.setAmasIdent(idAmas);
			
			if (stringGLon != null && stringGLat != null){
				double GLon = Double.parseDouble(stringGLon);
				double GLat = Double.parseDouble(stringGLat);
				newGalaxy.setGalacticLongLat(GLon, GLat);
			}
			
			if (stringSGLon != null && stringSGLat != null){
				double SGLon = Double.parseDouble(stringSGLon);
				double SGLat = Double.parseDouble(stringSGLat);
				newGalaxy.setSuperGalacticLongLat(SGLon, SGLat);
			}
			
			return newGalaxy;
		}
		
		return null;
	}
	
	/**
	 * methode toString
	 */
	public String toString(){
		return super.toString() + " | name: " + getName() + " | " + 
				"type: " + type + " | " + 
				"amasIdent: " + amasIdent + " | " + 
				"amasMass: " + amasMass;
	}
	
}
