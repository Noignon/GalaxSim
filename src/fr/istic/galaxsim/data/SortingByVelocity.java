package fr.istic.galaxsim.data;

/**
 * classe de tri par vitesse
 * @author anaofind
 *
 */
public class SortingByVelocity extends AbstractSorting{
	
	@Override
	public int compareCosmosElement(CosmosElement ce1, CosmosElement ce2) {
		if (ce2.getVelocity() > ce1.getVelocity()){
			return -1;
		}
		if (ce2.getVelocity() < ce1.getVelocity()){
			return 1;
		}
		return 0;
	}

}
