package fr.istic.galaxsim.calcul;
import fr.istic.galaxsim.data.*;

public class Traitement {
	
	private static final int T = 4;
	
	public static void traitementGalaxies()
	{

	    int nbGal = DataBase.getNumberGalaxies();
	    int nbAmas = DataBase.getNumberAmas();
	    
	    Galaxy[] allGal = DataBase.getAllGalaxies();
	    Amas[] allAmas = DataBase.getAllAmas();

	    // boucle pour le nombre d'intervalle de coordonnees
	    for(int t = 0; t < T; t++)
	    {

	    	//boucle pour taiter chaque galaxies
		      for(int idGal = 0; idGal < nbGal; idGal++)
		      {
		        double sommeForceX = 0;
		        double sommeForceY = 0;
		        double sommeForceZ = 0;
		        Galaxy g1 = allGal[idGal];

		        //boucle pour calculer les forces entre la galaxie actuelle et toutes les autres galaxies
		        for(int idAmas = 0; idAmas< nbAmas; idAmas++)
		        {
		        
		        	Amas a = allAmas[idAmas];
		        	double force = CalculGalaxies.forceAttraction(g1, a, t);
		        	if(force != 0)
		        	{
		        		sommeForceX = CalculGalaxies.forceX(g1, a, t, force);
		        		sommeForceY = CalculGalaxies.forceY(g1, a, t, force);
		        		sommeForceZ = CalculGalaxies.forceZ(g1, a, t, force);
		        	}
		        }
		        CalculGalaxies.coordByTime(g1, sommeForceX, sommeForceY, sommeForceZ, t);
		      }
	    }
	  }

	public static void traitementAmas()
	{

	    int nbAmas = DataBase.getNumberAmas();
	    
	    Amas[] allAmas = DataBase.getAllAmas();

	    // boucle pour le nombre d'intervalle de coordonnees
	    for(int t = 0; t < T; t++)
	    {

	    	//boucle pour taiter chaque galaxies
		      for(int idAmas = 0; idAmas < nbAmas; idAmas++)
		      {
		        double sommeForceX = 0;
		        double sommeForceY = 0;
		        double sommeForceZ = 0;
		        Amas a = allAmas[idAmas];

		        //boucle pour calculer les forces entre la galaxie actuelle et toutes les autres galaxies
		        for(int idAmasBis = 0; idAmasBis< nbAmas; idAmasBis++)
		        {
		        
		        	Amas a1 = allAmas[idAmasBis];
		        	double force = CalculAmas.forceAttraction(a, a1, t);
		        	if(force != 0)
		        	{
		        		sommeForceX = CalculAmas.forceX(a, a1, t, force);
		        		sommeForceY = CalculAmas.forceY(a, a1, t, force);
		        		sommeForceZ = CalculAmas.forceZ(a, a1, t, force);
		        	}
		        }
		        CalculAmas.coordByTime(a, sommeForceX, sommeForceY, sommeForceZ, t);
		      }
	    }
	 }
	
	public static void calculCoordonnee() {
		int nbGal = DataBase.getNumberGalaxies();
	    int nbAmas = DataBase.getNumberAmas();
	    
	    Galaxy[] allGal = DataBase.getAllGalaxies();
	    Amas[] allAmas = DataBase.getAllAmas();
	    
	    for(int i = 0; i < nbGal; i++) {
	    	CalculGalaxies.calculCoordInit(allGal[i]);
	    }
	    
	    for(int j = 0; j < nbAmas; j++) {
	    	CalculAmas.calculCoordInit(allAmas[j]);
	    }
	}
}
