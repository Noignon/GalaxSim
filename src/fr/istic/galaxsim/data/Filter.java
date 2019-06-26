package fr.istic.galaxsim.data;

import javafx.geometry.Point3D;

/**
 * classe de filtre
 * @author Yusoig
 *
 */
public class Filter {
	
	/**
	 * boolean indiquant si le filtre de distance est activ� ou pas
	 */
	private static boolean filterDistanceActived = false;
		
	/**
	 * boolean indiquant si le filtre de masse est activ� ou pas
	 */
	private static boolean filterMassActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonn�e x min est activ�
	 */ 
	private static boolean filterCoordinateXMinActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonn�e x max est activ�
	 */ 
	private static boolean filterCoordinateXMaxActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonn�e y min est activ�
	 */ 
	private static boolean filterCoordinateYMinActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonn�e y max est activ�
	 */ 
	private static boolean filterCoordinateYMaxActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonn�e z min est activ�
	 */ 
	private static boolean filterCoordinateZMinActived = false;
	
	/**
	 * boolean indiquant si le filtre de coordonn�e z max est activ�
	 */ 
	private static boolean filterCoordinateZMaxActived = false;
	
	/**
	 * boolean indiquant si le filtre de marge d'erreur est activ� ou pas
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
	 * filtre de la coordonn�e x
	 */
	private static double xMin = -1, xMax = -1;
	
	/**
	 * filtre de la coordonn�e y
	 */
	private static double yMin = -1, yMax = -1;
	
	/**
	 * filtre de la coordonn�e z
	 */
	private static double zMin = -1, zMax = -1;
	
	/**
	 * m�thode permettant de savoir si un amas correspond bien au filtres
	 * @param a l'amas
	 * @return boolean
	 */
	public static boolean goodAmas(Amas a){
		return (goodDistance(a) && goodMassAmas(a) && goodDeviationUncertainty(a));
	}
	
	/**
	 * m�thode permettant de savoir si une galaxy correspond bien au filtres
	 * @param g la galaxy
	 * @return boolean
	 */
	public static boolean goodGalaxies(Galaxy g){
		return (goodDistance(g) && goodDeviationUncertainty(g));
	}
	
	/**
	 * m�thode permettant de savoir si un objet � une coordonn�e correcte
	 * @param cosmosElement l'objet
	 * @return boolean
	 */
	public static boolean goodCoordinate(CosmosElement cosmosElement) {
		return (goodCoordinateX(cosmosElement) && goodCoordinateY(cosmosElement) && goodCoordinateZ(cosmosElement));
	}
	
	/**
	 * m�thode permettant de changer le filtre de distance max
	 * @param d la distance max
	 */
	public static void setDistanceFilter(double d){
		distance = d;
		filterDistanceActived = true;
	}
		
	/**
	 * m�thode permettant de changer le filtre de masse max
	 * @param m la masse max
	 */
	public static void setMassFilter(double m){
		mass = m;
		filterMassActived = true;
	}
	
	/**
	 * m�thode permettant de changer le filtre de marge d'erreur
	 * @param du la marge d'erreur max
	 */
	public static void setDeviationUncertaintyFilter(double du){
		deviationUncertainty = du;
		filterDeviationUncertaintyActived = true;
	}
	
	/**
	 * m�thode permettant de changer le filtre de coordonn�e x min
	 * @param x le x min
	 */
	public static void setCoordinateXMinFilter(double x){
		xMin = x;
		filterCoordinateXMinActived = true;
	}
	
	/**
	 * m�thode permettant de changer le filtre de coordonn�e x max
	 * @param x le x max
	 */
	public static void setCoordinateXMaxFilter(double x){
		xMax = x;
		filterCoordinateXMaxActived = true;
	}
	
	/**
	 * m�thode permettant de changer le filtre de coordonn�e y min
	 * @param y le y min
	 */
	public static void setCoordinateYMinFilter(double y){
		yMin = y;
		filterCoordinateYMinActived = true;
	}
	
	/**
	 * m�thode permettant de changer le filtre de coordonn�e y max
	 * @param y le y max
	 */
	public static void setCoordinateYMaxFilter(double y){
		yMax = y;
		filterCoordinateYMaxActived = true;
	}
	
	/**
	 * m�thode permettant de changer le filtre de coordonn�e z min
	 * @param z le z min
	 */
	public static void setCoordinateZMinFilter(double z){
		zMin = z;
		filterCoordinateZMinActived = true;
	}
	
	/**
	 * m�thode permettant de changer le filtre de coordonn�e z max
	 * @param z le z max
	 */
	public static void setCoordinateZMaxFilter(double z){
		zMax = z;
		filterCoordinateZMaxActived = true;
	}
	
	/**
	 * m�thode permettant de supprimer les veleurs de filtres
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
	 * methode permettant de savoir si un objet se situe � un distance inf�rieure ou �gale � celle indiqu�e en param�tre
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
	 * methode permettant de savoir si un amas a une masse inf�rieure ou �gale � celle indiqu�e en param�tre
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
	 * methode permettant de savoir si un objet � une marge d'erreur acc�ptable
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
	 * m�thode permettant de savoir si un objet � une coordonn�e x correct
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
	 * m�thode permettant de savoir si un objet � une coordonn�e y correct
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
	 * m�thode permettant de savoir si un objet � une coordonn�e z correct
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
