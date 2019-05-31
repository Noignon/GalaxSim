package fr.istic.galaxsim.data;

/**
 * classe de filtre
 * @author Yusoig
 *
 */
public class Filter {
	
	/**
	 * boolean indiquant si le filtre de distance est activé ou pas
	 */
	private static boolean filterDistanceActived = false;
	
	/**
	 * boolean indiquant si le filtre de vitesse est activé ou pas
	 */
	private static boolean filterVelocityActived = false;
	
	/**
	 * boolean indiquant si le filtre de masse est activé ou pas
	 */
	private static boolean filterMassActived = false;
	
	/**
	 * boolean indiquant si le filtre de longitude est activé ou pas
	 */
	private static boolean filterLonActived = false;
	
	/**
	 * boolean indiquant si le filtre de latitude est activé ou pas
	 */
	private static boolean filterLatActived = false;
	
	/**
	 * boolean indiquant si le filtre de marge d'erreur est activé ou pas
	 */
	private static boolean filterDeviationUncertaintyActived = false;
	
	/**
	 * filtre de distance
	 */
	private static double distance = -1;
	
	/**
	 * filtre de vitesse
	 */
	private static double velocity = -1;
	
	/**
	 * filtre de masse
	 */
	private static double mass = -1;
	
	/**
	 * filtre de marge d'erreur
	 */
	private static double deviationUncertainty = -1;
	
	/**
	 * filtre de longitude super galactique
	 */
	private static double superGalacticLonMin = -1, superGalacticLonMax = -1;
	
	/**
	 * filtre de latitude super galactique
	 */
	private static double superGalacticLatMin = -1, superGalacticLatMax = -1;
	
	/**
	 * méthode permettant de savoir si un amas correspond bien au filtres
	 * @param a l'amas
	 * @return boolean
	 */
	public static boolean goodAmas(Amas a){
		return (goodDistance(a) && goodVelocity(a) && goodMassAmas(a) && goodDeviationUncertainty(a) && goodLon(a) && goodLat(a));
	}
	
	/**
	 * méthode permettant de savoir si une galaxy correspond bien au filtres
	 * @param g la galaxy
	 * @return boolean
	 */
	public static boolean goodGalaxies(Galaxy g){
		return (goodDistance(g) && goodVelocity(g) && goodDeviationUncertainty(g) && goodLon(g) && goodLat(g));
	}
	
	/**
	 * méthode permettant de changer le filtre de distance max
	 * @param d la distance max
	 */
	public static void setDistanceFilter(double d){
		distance = d;
		filterDistanceActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de vitesse max
	 * @param v la vitesse max
	 */
	public static void setVelocityFilter(double v){
		velocity = v;
		filterVelocityActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de masse max
	 * @param m la masse max
	 */
	public static void setMassFilter(double m){
		mass = m;
		filterMassActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de marge d'erreur
	 * @param m la marge d'erreur max
	 */
	public static void setDeviationUncertaintyFilter(double du){
		deviationUncertainty = du;
		filterDeviationUncertaintyActived = true;
	}
	
	/**
	 * méthode permettant de changer le fltre de longitude min et max
	 * @param sgLon1 la longitude super galactique 1
	 * @param sgLon2 la longitude super galactique 2
	 */
	public static void setLonFilter(double sgLon1, double sgLon2){
		superGalacticLonMin = sgLon1;
		superGalacticLonMax = sgLon2;	
		
		if (sgLon2 < sgLon1) {
			superGalacticLonMin = sgLon2;
			superGalacticLonMax = sgLon1;
		}
		
		filterLonActived = true;
	}
	
	/**
	 * méthode permettant de changer le fltre de latitude min et max
	 * @param sgLat1 la latitude super galactique 1
	 * @param sgLat2 la latitude super galactique 2
	 */
	public static void setLatFilter(double sgLat1, double sgLat2){
		superGalacticLatMin = sgLat1;
		superGalacticLatMax = sgLat2;
		
		if (sgLat2 < sgLat1) {
			superGalacticLatMin = sgLat2;
			superGalacticLatMax = sgLat1;
		}
		
		filterLatActived = true;
	}
	
	/**
	 * méthode permettant de supprimer les veleurs de filtres
	 */
	public static void removeAllFilter(){
		distance = -1;
		velocity = -1;
		mass = -1;
		superGalacticLonMin = -1;
		superGalacticLonMax = -1;
		superGalacticLatMin = -1;
		superGalacticLatMax = -1;
		deviationUncertainty = -1;
		
		filterDistanceActived = false;
		filterVelocityActived = false;
		filterMassActived = false;
		filterDeviationUncertaintyActived = false;
		filterLonActived = false;
		filterLatActived = false;
	}
	
	/**
	 * methode permettant de savoir si un objet se situe à un distance inférieure ou égale à celle indiquée en paramètre
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodDistance(CosmosElement cosmosElement) {
		if (cosmosElement.getDistance() <= distance) {
			return true;
		}
		return !filterDistanceActived;
	}
	
	/**
	 * methode permettant de savoir si un objet a une vitesse inférieure ou égale à celle indiquée en paramètre
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodVelocity(CosmosElement cosmosElement) {
		if (cosmosElement.getVelocity() <= velocity) {
			return true;
		}
		return !filterVelocityActived;
	}
		
	/**
	 * methode permettant de savoir si un amas a une masse inférieure ou égale à celle indiquée en paramètre
	 * @param amas l'amas
	 * @return boolean
	 */
	private static boolean goodMassAmas(Amas amas) {
		if (amas.getMass() >= mass) {
			return true;
		}
		return !filterMassActived;
	}
	
	/**
	 * methode permettant de savoir si un objet à une marge d'erreur accéptable
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodDeviationUncertainty(CosmosElement cosmosElement) {
		if (cosmosElement.getDeviationUncertainty() <= deviationUncertainty) {
			return true;
		}
		return !filterDeviationUncertaintyActived;
	}
	
	/**
	 * methode permettant de savoir si un objet a la bonne longitude super galatique
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodLon(CosmosElement cosmosElement) {
		if (cosmosElement.getSuperGalacticLon() >= superGalacticLonMin && cosmosElement.getSuperGalacticLon() <= superGalacticLonMax) {
			return true;
		}
		return !filterLonActived;
	}
	
	/**
	 * methode permettant de savoir si un objet a la bonne latitude super galatique
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodLat(CosmosElement cosmosElement) {
		if (cosmosElement.getSuperGalacticLat() >= superGalacticLatMin && cosmosElement.getSuperGalacticLat() <= superGalacticLatMax) {
			return true;
		}
		return !filterLatActived;
	}	
}
