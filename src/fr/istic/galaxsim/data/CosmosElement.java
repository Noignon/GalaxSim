package fr.istic.galaxsim.data;

import java.util.*;

/**
 * classe abstraite permettant de définir un element du cosmos
 * @author anaofind
 *
 */
public abstract class CosmosElement{
	
	/**
	 * l'interval de temps séparant deux coordonnées cote à cote dans la liste "coordinatesInTime"
	 */
	public static double intervalTime = 1;
	
	/**
	 * les coordonnées du corps dans le temps (indice = temps)
	 */
	private ArrayList<Vector> coordinatesInTime = new ArrayList<Vector>();  
	
	/**
	 * les vitesses du corps dans le temps (indice = temps)
	 */
	private ArrayList<Vector> velocitiesInTime = new ArrayList<Vector>(); 
	
	/**
	 * l'identifiant
	 */
	private int ident;
	
	/**
	 * la distance en Mpc
	 */
	private double distance;
	
	/**
	 * la vitesse en km/s
	 */
	private double velocity;
	
	/**
	 * la longitude galactique
	 */
	private double galacticLon = 0;
	
	/**
	 * la latitude galactique
	 */
	private double galacticLat = 0;
	
	/**
	 * la longitude super galactique
	 */
	private double superGalacticLon = 0;
	
	/**
	 * la latitude super galactique
	 */
	private double superGalacticLat = 0;
	
	/**
	 * l'écart d'incertitude
	 */
	private double deviationUncertainty;
	
	/**
	 * constructeur 
	 * @param ident l'identifiant
	 * @param distance la distance
	 * @param velocity la vitesse
	 * @param deviationUncertainty l'écart d'incertitude
	 */
	public CosmosElement(int ident, double distance, double velocity, double deviationUncertainty){
		this.ident = ident;
		this.distance = distance;
		this.velocity = velocity;
		this.deviationUncertainty = deviationUncertainty;
	}
	
	/**
	 * methode permettant d'ajouter une coordonnée à la liste
	 */
	public void addCoordinate(Vector coordinate) {
		this.coordinatesInTime.add(coordinate);
	}
	
	/**
	 * methode permettant d'ajouter une vitesse à la liste
	 */
	public void addVelocity(Vector velocity) {
		this.velocitiesInTime.add(velocity);
	}
	
	/**
	 * methode permettant de supprimer toutes les coordonnées
	 */
	public void removeAllCoordinate(){
		this.coordinatesInTime = new ArrayList<Vector>();
	}
	
	/**
	 * methode permettant de supprimer toutes les vitesses
	 */
	public void removeAllVelocities(){
		this.velocitiesInTime = new ArrayList<Vector>();
	}
	
	/**
	 * methode permettant de recuperer une coordonnée dans le temps
	 * @param time le temps
	 */
	public Vector getCoordinate(int time){
		if (time >= 0 && time < this.coordinatesInTime.size()){
			return this.coordinatesInTime.get(time);
		}
		return null;
	}
	
	/**
	 * methode permettant de recuperer une vitesse dans le temps
	 * @param time le temps
	 */
	public Vector getVelocity(int time){
		if (time >= 0 && time < this.velocitiesInTime.size()){
			return this.velocitiesInTime.get(time);
		}
		return null;
	}
	
	/**
	 * methode permettant de recuperer le nombre de coordonnée
	 * @return le nombre de coordonnée
	 */
	public int getSizeCoordinate(){
		return this.coordinatesInTime.size();
	}

	/**
	 * methode permettant de recuperer le nombre de vitesses
	 * @return le nombre de coordonnée
	 */
	public int getSizeVelocity(){
		return this.velocitiesInTime.size();
	}
	
	/**
	 * getter identifiant
	 * @return l'identifiant
	 */
	public int getIdent() {
		return ident;
	}
	
	/**
	 * setter ident
	 * @param ident
	 */
	public void setIdent(int ident) {
		this.ident = ident;
	}
	
	/**
	 * getter distance
	 * @return la distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * getter vitesse
	 * @return la vitesse
	 */
	public double getVelocity() {
		return velocity;
	}
	
	/**
	 * methode permettant de changer la longitude et la latidue galactique
	 * @param lon la longitude galactique
	 * @param lat la latitude galactique
	 */
	public void setGalacticLongLat(double lon, double lat){
		this.galacticLon = lon;
		this.galacticLat = lat;
	}
	
	/**
	 * methode permettant de changer la longitude et la latidue super galactique
	 * @param lon la longitude super galactique
	 * @param lat la latitude super galactique
	 */
	public void setSuperGalacticLongLat(double lon, double lat){
		this.superGalacticLon = lon;
		this.superGalacticLat = lat;
	}

	/**
	 * getter longitude galactique
	 * @return la longitude galactique
	 */
	public double getGalacticLon() {
		return galacticLon;
	}
	
	/**
	 * getter latitude galactique
	 * @return la latitude galactique
	 */
	public double getGalacticLat() {
		return galacticLat;
	}

	/**
	 * getter longitude super galactique
	 * @return la longitude super galactique
	 */
	public double getSuperGalacticLon() {
		return superGalacticLon;
	}

	/**
	 * getter latitude super galactique
	 * @return la latitude super galactique
	 */
	public double getSuperGalacticLat() {
		return superGalacticLat;
	}
	
	/**
	 * getter deviationUncertainty
	 * @return deviationUncertainty
	 */
	public double getDeviationUncertainty() {
		return deviationUncertainty;
	}

	/**
	 * methode toString
	 */
	public String toString(){
		return ident + " -> distance: " + distance + " | vitesse: " + velocity + " | " +
				"longLatG: (" + galacticLon + "," + galacticLat + ")" + " | " + 
				"longLatSG: (" + superGalacticLon + "," + superGalacticLat + ")" + 
				" | incertitude: " + deviationUncertainty;
	}
}
