package fr.istic.galaxsim.data;

/**
 * classe permettant de définir une coordonnee 3d
 * @author anaofind
 *
 */
public class Coordinate {
	
	/**
	 * coordonnée x
	 */
	private double x;
	
	/**
	 * coordonnée y
	 */
	private double y;
	
	/**
	 * coordonnée z
	 */
	private double z;
	
	/**
	 * constructeur
	 * @param x la coordonnée x
	 * @param y la coordonnée y
	 * @param z la coordonnée z
	 */
	public Coordinate(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * getter coordonnée x
	 * @return la coordonnée x
	 */
	public double getX() {
		return x;
	}

	/**
	 * getter coordonnée y
	 * @return la coordonnée y
	 */
	public double getY() {
		return y;
	}

	/**
	 * getter coordonnée z
	 * @return la coordonnée z
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * methode permetant de savoir si le point est ou non dans les intervalles de coordonées [x1,x2], [y1,y2], [z1,z2]
	 * @return 1 si le poit est présent dans l'intervalle, sinon 0
	 */
	public boolean isIn(double x1,double x2, double y1,double y2,double z1,double z2) {
		return x1<=x && x2>=x && y1<= y && y2>=y && z1<=z && z2>=z;
	}
	
	
}