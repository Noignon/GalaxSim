package fr.istic.galaxsim.data;

import javafx.geometry.Point3D;

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
	 * boolean indiquant si le filtre de masse est activé ou pas
	 */
	private static boolean filterMassActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonnée x min est activé
	 */ 
	private static boolean filterCoordinateXMinActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonnée x max est activé
	 */ 
	private static boolean filterCoordinateXMaxActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonnée y min est activé
	 */ 
	private static boolean filterCoordinateYMinActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonnée y max est activé
	 */ 
	private static boolean filterCoordinateYMaxActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonnée z min est activé
	 */ 
	private static boolean filterCoordinateZMinActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonnée z max est activé
	 */ 
	private static boolean filterCoordinateZMaxActived = false;
	
	/**
	 * boolean indiquant si le filtre de marge d'erreur est activé ou pas
	 */
	private static boolean filterDeviationUncertaintyActived = false;
	
	/**
	 * filtre de distance
	 */
	private static double distance = -1;
		
	/**
	 * filtre de masse
	 */
	private static double mass = -1;
	
	/**
	 * filtre de marge d'erreur
	 */
	private static double deviationUncertainty = -1;
	
	/**
	 * filtre de la coordonnée x
	 */
	private static double xMin = -1, xMax = -1;
	
	/**
	 * filtre de la coordonnée y
	 */
	private static double yMin = -1, yMax = -1;
	
	/**
	 * filtre de la coordonnée z
	 */
	private static double zMin = -1, zMax = -1;
	
	/**
	 * méthode permettant de savoir si un amas correspond bien au filtres
	 * @param a l'amas
	 * @return boolean
	 */
	public static boolean goodAmas(Amas a){
		return (goodDistance(a) && goodMassAmas(a) && goodDeviationUncertainty(a));
	}
	
	/**
	 * méthode permettant de savoir si une galaxy correspond bien au filtres
	 * @param g la galaxy
	 * @return boolean
	 */
	public static boolean goodGalaxies(Galaxy g){
		return (goodDistance(g) && goodDeviationUncertainty(g));
	}
	
	/**
	 * méthode permettant de savoir si un objet à une coordonnée correcte
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	public static boolean goodCoordinate(CosmosElement cosmosElement) {
		return (goodCoordinateX(cosmosElement) && goodCoordinateY(cosmosElement) && goodCoordinateZ(cosmosElement));
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
	 * méthode permettant de changer le filtre de masse max
	 * @param m la masse max
	 */
	public static void setMassFilter(double m){
		mass = m;
		filterMassActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de marge d'erreur
	 * @param du la marge d'erreur max
	 */
	public static void setDeviationUncertaintyFilter(double du){
		deviationUncertainty = du;
		filterDeviationUncertaintyActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de coordonnée x min
	 * @param x le x min
	 */
	public static void setCoordinateXMinFilter(double x){
		xMin = x;
		filterCoordinateXMinActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de coordonnée x max
	 * @param x le x max
	 */
	public static void setCoordinateXMaxFilter(double x){
		xMax = x;
		filterCoordinateXMaxActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de coordonnée y min
	 * @param y le y min
	 */
	public static void setCoordinateYMinFilter(double y){
		yMin = y;
		filterCoordinateYMinActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de coordonnée y max
	 * @param y le y max
	 */
	public static void setCoordinateYMaxFilter(double y){
		yMax = y;
		filterCoordinateYMaxActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de coordonnée z min
	 * @param z le z min
	 */
	public static void setCoordinateZMinFilter(double z){
		zMin = z;
		filterCoordinateZMinActived = true;
	}
	
	/**
	 * méthode permettant de changer le filtre de coordonnée z max
	 * @param z le z max
	 */
	public static void setCoordinateZMaxFilter(double z){
		zMax = z;
		filterCoordinateZMaxActived = true;
	}
	
	/**
	 * méthode permettant de supprimer les veleurs de filtres
	 */
	public static void removeAllFilter(){
		distance = -1;
		mass = -1;
		deviationUncertainty = -1;
		
		filterDistanceActived = false;
		filterMassActived = false;
		filterDeviationUncertaintyActived = false;
		filterCoordinateXMinActived = false;
		filterCoordinateXMaxActived = false;
		filterCoordinateYMinActived = false;
		filterCoordinateYMaxActived = false;
		filterCoordinateZMinActived = false;
		filterCoordinateZMaxActived = false;
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
	 * méthode permettant de savoir si un objet à une coordonnée x correct
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodCoordinateX(CosmosElement cosmosElement){
		boolean min = !filterCoordinateXMinActived;
		boolean max = !filterCoordinateXMaxActived;
		
		Point3D c = cosmosElement.getCoordinate(0);
		
		if (c != null) {
			if (filterCoordinateXMinActived) {
				min = (c.getX() >= xMin);
			}
			if (filterCoordinateXMaxActived) {
				max = (c.getX() <= xMax);
			}
			
			return (min && max);	
		}
		
		return false;
	}
	
	/**
	 * méthode permettant de savoir si un objet à une coordonnée y correct
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodCoordinateY(CosmosElement cosmosElement){
		boolean min = !filterCoordinateYMinActived;
		boolean max = !filterCoordinateYMaxActived;

		Point3D c = cosmosElement.getCoordinate(0);
		
		if (c != null) {
			if (filterCoordinateYMinActived) {
				min = (c.getY() >= yMin);
			}
			if (filterCoordinateYMaxActived) {
				max = (c.getY() <= yMax);
			}
			
			return (min && max);	
		}
		
		return false;
	}
	
	/**
	 * méthode permettant de savoir si un objet à une coordonnée z correct
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	private static boolean goodCoordinateZ(CosmosElement cosmosElement){
		boolean min = !filterCoordinateZMinActived;
		boolean max = !filterCoordinateZMaxActived;

		Point3D c = cosmosElement.getCoordinate(0);
		
		if (c != null) {
			if (filterCoordinateZMinActived) {
				min = (c.getZ() >= zMin);
			}
			if (filterCoordinateZMaxActived) {
				max = (c.getZ() <= zMax);
			}
			
			return (min && max);
		}
		
		return false;
	}
}
