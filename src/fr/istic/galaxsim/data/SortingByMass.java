package fr.istic.galaxsim.data;

/**
 * classe de tri par mass (seulement pour les amas)
 * @author anaofind
 *
 */
public class SortingByMass extends AbstractSorting{

	@Override
	public int compareCosmosElement(CosmosElement ce1, CosmosElement ce2) {
		Amas a1 = (Amas)ce1;
		Amas a2 = (Amas)ce2;
		
		if (a2.getMass() > a1.getMass()){
			return -1;
		}
		if (a2.getMass() < a1.getMass()){
			return 1;
		}
		return 0;
	}

}
