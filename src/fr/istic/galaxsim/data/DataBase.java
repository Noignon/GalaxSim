package fr.istic.galaxsim.data;

import java.util.*;

/**
 * classe qui permet de stocker les galaxies et les amas
 * @author anaofind
 *
 */
public class DataBase {
	
	/**
	 * code de trie :
	 * 0: identifiant
	 * 1: distance
	 * 2: vitesse
	 * 3: la mass (seulement pour les amas)
	 */
	public static final int SORTING_ID = 0, SORTING_DISTANCE = 1, SORTING_VELOCITY = 2, SORTING_MASS = 3;
	
	/**
	 * liste de toutes les galaxies
	 */
	private static ArrayList<Galaxy> listAllGalaxies = new ArrayList<Galaxy>();
	
	/**
	 * liste de tout les amas
	 */
	private static ArrayList<Amas> listAllAmas = new ArrayList<Amas>();
	
	
	/**
	 * table de galaxies
	 */
	public static ArrayList<Galaxy> tableGalaxies = new ArrayList<Galaxy>();
	
	/**
	 * table d'amas 
	 */
	public static ArrayList<Amas> tableAmas = new ArrayList<Amas>();
	
	/**
	 * methode permettant d'initialiser la table d'amas
	 * @param datasAmas les donnees des amas
	 */
	public static void initAmas(String[][] datasAmas){
		for (int i = 0; i<datasAmas.length; i++){
			Amas a = Amas.create(datasAmas[i]);	
			if (a.getNbGalaxies() > 1){
				addAmas(a);
			} else {
				addGalaxy(a.transformInGalaxy());
			}
		}
	}
	
	/**
	 * methode permettant d'initialiser la table de galaxies
	 * @param datasGalaxies les données de galaxies
	 */
	public static void initGalaxies(String[][] datasGalaxies){
		for (int i = 0; i<datasGalaxies.length; i++){
			Galaxy g = Galaxy.create(datasGalaxies[i]);
			addGalaxy(g);
		}
	}
	
	/**
	 * methode permettant de recuperer le tableau de toutes les galaxies
	 * @return le tableau de toutes les galaxies
	 */
	public static Galaxy[] getAllGalaxies(){
		return listAllGalaxies.toArray(new Galaxy[listAllGalaxies.size()]);
	}
	
	/**
	 * methode permettant de recuperer le tableau de tout les amas
	 * @return le tableau de tout les amas
	 */
	public static Amas[] getAllAmas(){
		return listAllAmas.toArray(new Amas[listAllAmas.size()]);
	}
	
	/**
	 * methode permettant d'ajouter à la table une galaxies
	 * @param g la galaxie que l'on souhaite ajouter
	 */
	public static void addGalaxy(Galaxy g){
		listAllGalaxies.add(g);
		if (Filter.goodGalaxies(g)) {
			tableGalaxies.add(g);
		}
	}
	
	/**
	 * methode permettant d'ajouter à la table un amas
	 * @param a l'amas que l'on souhaite ajouter
	 */
	public static void addAmas(Amas a){
		listAllAmas.add(a);
		if (Filter.goodAmas(a)) {
			tableAmas.add(a);
		}
	}
	
	/**
	 * methode permettant de recuperer un amas
	 * @param l'indice de l'amas
	 * @return l'amas
	 */
	public static Amas getAmas(int indice){
		if (indice >= 0 && indice <= tableAmas.size()) {
			return tableAmas.get(indice);
		}
		return null;
	}
	
	/**
	 * methode permettant de recuperer une galaxy
	 * @param l'indice de la galaxie
	 * @return la galaxy
	 */
	public static Galaxy getGalaxy(int indice){
		if (indice >= 0 && indice <= tableGalaxies.size()) {
			return tableGalaxies.get(indice);
		}
		return null;
	}
	
	/**
	 * methode permettant de supprimer toutes les galaxies
	 */
	public static void removeAllGalaxies(){
		listAllGalaxies = new ArrayList<Galaxy>();
		tableGalaxies = new ArrayList<Galaxy>();
	}
	
	/**
	 * methode permettant de supprimer tout les amas
	 */
	public static void removeAllAmas(){
		listAllAmas = new ArrayList<Amas>();
		tableAmas = new ArrayList<Amas>();
	}
	
	/**
	 * methode permettant de sacoir combien il y a de galaxies dans la table
	 * @return le nombre de galaxies
	 */
	public static int getNumberAmas(){
		return tableAmas.size();
	}
	
	/**
	 * methode permettant de savoir combien il y a d'amas dans la table
	 * @return le nombre d'amas
	 */
	public static int getNumberGalaxies(){
		return tableGalaxies.size();
	}
	
	/**
	 * methode permettant de trier les amas
	 * @param codeSorting le code de tri
	 * @param descending boolean indiquant si le trie se faire de maniere décroissant ou pas
	 */
	public static void sortAmas(int codeSorting, boolean descending){
		AbstractSorting sortingComparator = getSortingComparatorAmas(codeSorting, descending);
		if (sortingComparator != null) {
			tableAmas.sort(sortingComparator);
			listAllAmas.sort(sortingComparator);
		}
	}
	
	/**
	 * methode permettant de trier les galaxies
	 * @param codeSorting le code de tri
	 * @param descending boolean indiquant si le trie se faire de maniere décroissant ou pas
	 */
	public static void sortGalaxies(int codeSorting, boolean descending){
		AbstractSorting sortingComparator = getSortingComparatorGalaxies(codeSorting, descending);
		if (sortingComparator != null) {
			tableGalaxies.sort(sortingComparator);
			listAllGalaxies.sort(sortingComparator);
		}
	}
	
	/**
	 * methode permettant de recuperer le type de comparateur pour le tri souhaité
	 * @param codeSorting le code de tri
	 * @param descending le boolean indiquant si le tri est de manière décroissante ou pas
	 * @return le comparateur approprié
	 */
	private static AbstractSorting getSortingComparator(int codeSorting, boolean descending){
		AbstractSorting sortingComparator = null;
		
		switch(codeSorting){
		case SORTING_ID :
			sortingComparator = new SortingById();
			break;
		case SORTING_DISTANCE :
			sortingComparator = new SortingByDistance();
			break;
		case SORTING_VELOCITY :
			sortingComparator = new SortingByVelocity();
			break;
		}
		
		if (sortingComparator != null) {
			sortingComparator.setDescending(descending);
		}
		
		return sortingComparator;
	}
	
	/**
	 * methode permettant de recuperer le type de comparateur pour le tri souhaité (seulement pour les amas)
	 * @param codeSorting le code de tri
	 * @param descending le boolean indiquant si le tri est de manière décroissante ou pas
	 * @return le comparateur approprié
	 */
	private static AbstractSorting getSortingComparatorAmas(int codeSorting, boolean descending){
		AbstractSorting sortingComparator = getSortingComparator(codeSorting, descending);
		
		if (sortingComparator != null) {
			return sortingComparator;
		}
		
		switch(codeSorting){
		case SORTING_MASS :
			sortingComparator = new SortingByMass();
			break;
		}
		
		if (sortingComparator != null) {
			sortingComparator.setDescending(descending);
		}
		
		return sortingComparator;
	}
	
	/**
	 * methode permettant de recuperer le type de comparateur pour le tri souhaité (seulement pour les galaxies)
	 * @param codeSorting le code de tri
	 * @param descending le boolean indiquant si le tri est de manière décroissante ou pas
	 * @return le comparateur approprié
	 */
	private static AbstractSorting getSortingComparatorGalaxies(int codeSorting, boolean descending){
		AbstractSorting sortingComparator = getSortingComparator(codeSorting, descending);
		
		return sortingComparator;
	}
}
