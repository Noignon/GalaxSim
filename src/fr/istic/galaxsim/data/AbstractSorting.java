package fr.istic.galaxsim.data;

import java.util.Comparator;

/**
 * classe représentant un tri
 * @author anaofind
 *
 */
public abstract class AbstractSorting implements Comparator<CosmosElement> {

	/**
	 * boolean indiquant si le tri doit se faire de manière decroissante ou pas
	 */
	public boolean descending = false;
	
	
	@Override
	public int compare(CosmosElement ce1, CosmosElement ce2) {
		int compareValue = compareCosmosElement(ce1, ce2);
		if (descending){
			return - compareValue;
		}
		
		return compareValue;
	}
	
	/**
	 * methode abstraite permettant de comparer deux objets CosmosElement
	 * @param ce1 objet1
	 * @param ce2 objet2
	 * @return -1 si o2 est plus grand, 1 si o1 est plus grand et 0 si o1=o2
	 */
	public abstract int compareCosmosElement(CosmosElement ce1, CosmosElement ce2);

	/**
	 * setter descending
	 * @param descending boolean
	 */
	public void setDescending(boolean descending){
		this.descending = descending;
	}
	
}