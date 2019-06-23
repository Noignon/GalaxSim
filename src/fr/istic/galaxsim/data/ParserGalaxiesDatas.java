package fr.istic.galaxsim.data;


/**
 * classe qui permet d'analyser et de recuperer les données de galaxies
 * @author anaofind
 *
 */
public class ParserGalaxiesDatas extends ParserCosmosDatas{

	/**
	 * constructeur
	 * @param pathFile le chemin du fichier
	 */
	public ParserGalaxiesDatas(String pathFile) {
		super(pathFile, 12);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * methode permettant de verifier que la donnee est correcte
	 * @param data la donnee
	 */
	@Override
	public boolean isCorrectData(String data, int indice) {
		if (indice >= 0 && indice <= 7){
			if (CheckData.isInteger(data)){
				int i = Integer.parseInt(data);
				return (i >= 4 && i <= 9003164);
			}
			return false;
		}
		
		if (indice>= 9 && indice <= 14){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= 0.05 && d <= 517.7);
			}
			return false;
		}
		
		if (indice>= 24 && indice <= 27){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= 0.1 && d <= 0.6);
			}
			return false;
		}
		
		if (indice >= 113 && indice <= 120){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 122 && indice <= 129){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 131 &&indice <= 138){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 140 && indice <= 147){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 149 && indice <= 152){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= -5 && d <= 10);
			}
			return false;
		}
		
		if (indice >= 178 && indice <= 182){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= -339 && d <= 34591);
			}
			return false;
		}
		
		if (indice >= 202 && indice <= 211){
			return true;
		}
		
		if (indice >= 213 && indice <= 218){
			if (CheckData.isInteger(data)){
				int i = Integer.parseInt(data);
				return (i >= 100001 && i <= 400002) || i == 0;
			}
			return false;
		}
		
		if (indice >= 370 && indice <= 378){
			if (CheckData.isDouble(data)){
				double i = Double.parseDouble(data);
				return (i >= 0.001 && i <= 24400.1) || i == 0;
			}
			return false;
		}
		
		return true;
	}

	/**
	 * methode permettant de savoir si la donnee est interressante
	 * @param data la donnee
	 * @param indice l'indice de la donnée
	 * @return boolean indiquant si la donnee est interressante
	 */
	@Override
	public boolean isImportantData(String data, int indice) {
		return ( (indice >= 0 && indice <= 7) || 
				(indice>= 9 && indice <= 14) || 
				(indice >= 24 && indice <= 27) ||
				(indice >= 113 && indice <= 120) || 
				(indice >= 122 && indice <= 129) || 
				(indice >= 131 && indice <= 138) || 
				(indice >= 140 && indice <= 147) ||
				(indice >= 149 && indice <= 152) || 
				(indice >= 178 && indice <= 182) || 
				(indice >= 202 && indice <= 211) || 
				(indice >= 213 && indice <= 218) ||
				(indice >= 370 && indice <= 378)) ;
	}

	@Override
	public int getFinalIndiceData(int indice) {
		if (indice >= 0 && indice <= 7){
			return 0;
		}
		
		if (indice>= 9 && indice <= 14){
			return 4;
		}
		
		if (indice >= 24 && indice <= 27){
			return 11;
		}
		
		if (indice >= 113 && indice <= 120){
			return 5;
		}
		
		if (indice >= 122 && indice <= 129){
			return 6;
		}
		
		if (indice >= 131 &&indice <= 138){
			return 7;
		}
		
		if (indice >= 140 && indice <= 147){
			return 8;
		}
		
		if (indice >= 149 && indice <= 152){
			return 2;
		}
		
		if (indice >= 178 && indice <= 182){
			return 9;
		}
		
		if (indice >= 202 && indice <= 211){
			return 1;
		}
		
		if (indice >= 213 && indice <= 218){
			return 3;
		}

		if (indice >= 370 && indice <= 378){
			return 10;
		}
		
		return -1;
	}



}
