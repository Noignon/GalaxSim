package fr.istic.galaxsim.data;

/**
 * classe de tri par distance
 * @author anaofind
 *
 */
public class SortingByDistance extends AbstractSorting{
	
	@Override
	public int compareCosmosElement(CosmosElement ce1, CosmosElement ce2) {
		if (ce2.getDistance() > ce1.getDistance()){
			return -1;
		}
		if (ce2.getDistance() < ce1.getDistance()){
			return 1;
		}
		return 0;
	}

}
