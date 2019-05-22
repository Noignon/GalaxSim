package fr.istic.galaxsim.data;

import java.util.ArrayList;
/**
 * classe de parser de donn�es de galaxies
 * @author Yusoig
 *
 */
public class Filter {

	/**
	 * methode retournant la liste de galaxies se situant � un distance inf�rieure ou �gale � celle indiqu�e en param�tre
	 * @param distance 
	 * @return une liste de galaxies
	 */
	public static ArrayList<Galaxy> filterDistanceGalaxy(double distance) {
		ArrayList<Galaxy> listGalaxy= new ArrayList<Galaxy>();
		for(int i =0 ; i<DataBase.tableGalaxies.size();i++) {
			if(DataBase.tableGalaxies.get(i).getDistance()<=distance) {
				listGalaxy.add(DataBase.tableGalaxies.get(i));
			}
		}
		return listGalaxy;
	}
	
	/**
	 * methode retournant la liste d'amas se situant � un distance inf�rieure ou �gale � celle indiqu�e en param�tre
	 * @param distance 
	 * @return une liste de d'amas
	 */
	public static ArrayList<Amas> filterDistanceAmas(double distance) {
		ArrayList<Amas> listAmas= new ArrayList<Amas>();
		for(int i =0 ; i<DataBase.tableAmas.size();i++) {
			if(DataBase.tableAmas.get(i).getDistance()<=distance) {
				listAmas.add(DataBase.tableAmas.get(i));
			}
		}
		return listAmas;
	}
	
	
	/**
	 * methode retournant la liste de galaxies aillant une v�locit� inf�rieure ou �gale � celle indiqu�e en param�tre
	 * @param velocity 
	 * @return une liste de galaxies
	 */
	public static ArrayList<Galaxy> filterVelocityGalaxy(double velocity) {
		ArrayList<Galaxy> listGalaxy= new ArrayList<Galaxy>();
		for(int i =0 ; i<DataBase.tableGalaxies.size();i++) {
			if(DataBase.tableGalaxies.get(i).getVelocity()<=velocity) {
				listGalaxy.add(DataBase.tableGalaxies.get(i));
			}
		}
		return listGalaxy;
	}
	
	/**
	 * methode retournant la liste d'amas aillant une v�locit� inf�rieure ou �gale � celle indiqu�e en param�tre
	 * @param velocity 
	 * @return une liste de d'amas
	 */
	public static ArrayList<Amas> filterVelocityAmas(double velocity) {
		ArrayList<Amas> listAmas= new ArrayList<Amas>();
		for(int i =0 ; i<DataBase.tableAmas.size();i++) {
			if(DataBase.tableAmas.get(i).getVelocity()<=velocity) {
				listAmas.add(DataBase.tableAmas.get(i));
			}
		}
		return listAmas;
	}
	
	
	/**
	 * methode retournant la liste d'amas aillant une masse inf�rieure ou �gale � celle indiqu�e en param�tre
	 * @param mass 
	 * @return une liste de d'amas
	 */
	public static ArrayList<Amas> filterMassAmas(double mass) {
		ArrayList<Amas> listAmas= new ArrayList<Amas>();
		for(int i =0 ; i<DataBase.tableAmas.size();i++) {
			if(DataBase.tableAmas.get(i).getMass()<=mass) {
				listAmas.add(DataBase.tableAmas.get(i));
			}
		}
		return listAmas;
	}
}
