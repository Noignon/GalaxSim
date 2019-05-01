package fr.istic.galaxsim.data;

/**
 * classe qui permet d'analyser et de recuperer les donn�es d'amas
 * @author anaofind
 *
 */
public class ParserAmasDatas extends ParserCosmosDatas{

	/**
	 * constructeur
	 * @param pathFile le chemin du fichier
	 */
	public ParserAmasDatas(String pathFile) {
		super(pathFile);
	}

	/**
	 * constructeur
	 * @param pathFile le chemin du fichier
	 * @param sizeMin la taille min des donn�es
	 * @param sizeMax la taille max des donn�es
	 */
	public ParserAmasDatas(String pathFile, int sizeMin, int sizeMax) {
		super(pathFile, sizeMin, sizeMax);
	}

	/**
	 * methode permettant de verifier que la donnee est correcte
	 * @param data la donnee
	 */
	@Override
	public boolean isCorrectData(String data, int indice) {
		// TODO Auto-generated method stub
		// � completer
		return true;
	}

	/**
	 * methode permettant de savoir si la donnee est interressante
	 * @param data la donnee
	 * @param l'indice de la donn�e
	 * @return boolean indiquant si la donnee est interressante
	 */
	@Override
	public boolean isImportantData(String data, int indice) {
		// TODO Auto-generated method stub
		// � completer
		return true;
	}


}
