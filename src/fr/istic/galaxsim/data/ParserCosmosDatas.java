package fr.istic.galaxsim.data;

import java.util.*;
/**
 * classe de parser de données de galaxies
 * @author anaofind
 *
 */
public abstract class ParserCosmosDatas extends AbstractParserFile{
	
	/**
	 * les donnees
	 */
	private ArrayList<String[]> allDatas = new ArrayList<String[]>();
		
	/**
	 * la taille final des donnees apres recuperation
	 */
	private int sizeFinal = 0;
	
	/**
	 * constructeurs
	 * @param pathFile le chemin du fichier
	 */
	public ParserCosmosDatas(String pathFile) {
		super(pathFile, AbstractParserFile.DATAS_ORIGINAL_LINE);
	}
	
	/**
	 * constructeurs
	 * @param pathFile le chemin du fichier
	 */
	public ParserCosmosDatas(String pathFile, int sizeFinal) {
		super(pathFile, AbstractParserFile.DATAS_ORIGINAL_LINE);
		this.sizeFinal = sizeFinal;
	}
	

	@Override
	public void executeAction(String datas) {
		Scanner sc = new Scanner(datas);
		
		int indice = 0;
		String data = "";
		String[] importantDatas = getEmptyDatas();
		boolean correct = true;
		
		while (correct && indice < datas.length()){
			if (datas.charAt(indice) != ' '){
				data = sc.next();
				if (this.isCorrectData(data, indice+1)){
					if (this.isImportantData(data, indice+1)){
						int indiceFinalTable = this.getFinalIndiceData(indice+1);
						if (indiceFinalTable >= 0 && indiceFinalTable<sizeFinal) {
							importantDatas[indiceFinalTable] = data;
						}
					}
				} else {
					correct = false;
				}
				indice += data.length();
			} else {
				indice ++;
			}
		}
		
		if (correct){
			this.allDatas.add(importantDatas);
		} else {
			//this.printErrorData();
		}
		sc.close();
	}
	
	
	
	/**
	 * methode qui retourne les donnees sous forme de tableau
	 * @return les donnees 
	 */
	public String[][] getAllDatas(){
		if (this.allDatas.size() > 0){
			int size = this.allDatas.size();
			String[][] d = new String[size][sizeFinal];
			for (int i = 0; i<size; i++){
				d[i] = this.allDatas.get(i);
			}
			return d;
		}
		return null;
	}
	
	/**
	 * methode permettant d'optenir un tableau de données vide
	 * @return un tableau de données vide
	 */
	private String[] getEmptyDatas(){
		String[] datas = new String[sizeFinal];
		for (int i = 0; i<datas.length; i++){
			datas[i] = null;
		}
		return datas;
	}
	
	/**
	 * methode permettant de verifier que la donnee est correcte
	 * @param data la donnee
	 * @param indice de la donnée
	 */
	public abstract boolean isCorrectData(String data, int indice);
	
	/**
	 * methode permettant de savoir si la donnee est interressante
	 * @param data la donnee
	 * @param indice de la donnée
	 * @return boolean indiquant si la donnee est interressante
	 */
	public abstract boolean isImportantData(String data, int indice);
	
	/**
	 * methode permettant d'obtenir l'indice de la donnée dans le tableau final
	 * @param indice l'indice de la donnée dans le fichier
	 * @return l'indice de la donnée dans le tableau final
	 */
	public abstract int getFinalIndiceData(int indice);
}
