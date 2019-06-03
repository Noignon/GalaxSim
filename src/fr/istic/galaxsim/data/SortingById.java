package fr.istic.galaxsim.data;

/**
 * classe de tri par identifiant
 * @author anaofind
 *
 */
public class SortingById extends AbstractSorting{
		
	@Override
	public int compareCosmosElement(CosmosElement ce1, CosmosElement ce2) {
		if (ce2.getIdent() > ce1.getIdent()){
			return -1;
		}
		if (ce2.getIdent() < ce1.getIdent()){
			return 1;
		}
		return 0;
	}



}
