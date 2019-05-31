package fr.istic.galaxsim.data;

/**
 * classe qui permet d'analyser et de recuperer les données d'amas
 * @author anaofind
 *
 */
public class ParserAmasDatas extends ParserCosmosDatas{

	/**
	 * constructeur
	 * @param pathFile le chemin du fichier
	 */
	public ParserAmasDatas(String pathFile) {
		super(pathFile, 11);
	}
	
	/**
	 * methode permettant de verifier que la donnee est correcte
	 * @param data la donnee
	 */
	@Override
	public boolean isCorrectData(String data, int indice) {
		if (indice >= 0 && indice <= 6){
			if (CheckData.isInteger(data)){
				int i = Integer.parseInt(data);
				return (i >= 100001 && i <= 400002) || i == 0;
			}
			return false;
		}
		
		if (indice >= 8 && indice <= 10){
			if (CheckData.isInteger(data)){
				int i = Integer.parseInt(data);
				return (i >= 1 && i <= 161);
			}
			return false;
		}
		
		if (indice >= 18 && indice <= 21){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= 0.02 && d <= 0.54);
			}
			return false;
		}
		
		if (indice >= 23 && indice <= 27){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= 0 && d <= 494.3);
			}
			return false;
		}
		
		if (indice >= 29 && indice <= 33){
			return true;
		}
		
		if (indice >= 57 && indice <= 62){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 64 && indice <= 69){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 71 && indice <= 78){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 80 && indice <= 87){
			return CheckData.isDouble(data);
		}
		
		if (indice >= 127 && indice <= 131){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= -158 && d <= 29882);
			}
			return false;
		}
		
		if (indice >= 160 && indice <= 168){
			if (CheckData.isDouble(data)){
				double d = Double.parseDouble(data);
				return (d >= 0 && d <= 24401);
			}
			return false;
		}

		return true;
	}

	/**
	 * methode permettant de savoir si la donnee est interressante
	 * @param data la donnee
	 * @param l'indice de la donnée
	 * @return boolean indiquant si la donnee est interressante
	 */
	@Override
	public boolean isImportantData(String data, int indice) {
		return ( (indice >= 0 && indice <= 6) ||
				(indice >= 8 && indice <= 10) ||
				(indice >= 18 && indice <= 21) ||
				(indice >= 23 && indice <= 27) || 
				(indice >= 29 && indice <= 33) || 
				(indice >= 57 && indice <= 62) || 
				(indice >= 64 && indice <= 69) || 
				(indice >= 71 && indice <= 78) || 
				(indice >= 80 && indice <= 87) ||
				(indice >= 127 && indice <= 131) || 
				(indice >= 160 && indice <= 168) );
	}

	/**
	 * methode permettant d'obtenir l'indice de la donnée dans le tableau final
	 * @param indice l'indice de la donnée dans le fichier
	 * @return l'indice de la donnée dans le tableau final
	 */
	@Override
	public int getFinalIndiceData(int indice) {
		if (indice >= 0 && indice <= 6){
			return 0;
		}
		
		if (indice >= 8 && indice <= 10) {
			return 9;
		}
		
		if (indice >= 18 && indice <= 21){
			return 10;
		}
		
		if (indice >= 23 && indice <= 27){
			return 2;
		}
		
		if (indice >= 29 && indice <= 33){
			return 1;
		}
		
		if (indice >= 57 && indice <= 62){
			return 3;
		}
		
		if (indice >= 64 && indice <= 69){
			return 4;
		}
		
		if (indice >= 71 && indice <= 78){
			return 5;
		}
		
		if (indice >= 80 && indice <= 87){
			return 6;
		}
		
		if (indice >= 127 && indice <= 131){
			return 7;
		}
		
		if (indice >= 160 && indice <= 168){
			return 8;
		}
		
		return -1;
	}


}
