package fr.istic.galaxsim.data;

/**
 * classe permettant de d�finir une coordonnee 3d
 * @author anaofind
 *
 */
public class Coordinate {
	
	/**
	 * coordonn�e x
	 */
	private double x;
	
	/**
	 * coordonn�e y
	 */
	private double y;
	
	/**
	 * coordonn�e z
	 */
	private double z;
	
	/**
	 * constructeur
	 * @param x la coordonn�e x
	 * @param y la coordonn�e y
	 * @param z la coordonn�e z
	 */
	public Coordinate(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * getter coordonn�e x
	 * @return la coordonn�e x
	 */
	public double getX() {
		return x;
	}

	/**
	 * getter coordonn�e y
	 * @return la coordonn�e y
	 */
	public double getY() {
		return y;
	}

	/**
	 * getter coordonn�e z
	 * @return la coordonn�e z
	 */
	public double getZ() {
		return z;
	}
	
	
}
