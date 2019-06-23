package fr.istic.galaxsim.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * classe abstraite de parser de fichier
 * @author anaofind
 *
 */
public abstract class AbstractParserFile {
	
	/**
	 * code de recuperation de type de donnees
	 * 0 : mots par mots
	 * 1 : ligne par ligne (un espace entre chaque mot)
	 * 2 : ligne par ligne (espaces originals)
	 * 3 : bloc par bloc (un espace entre chaque mot)
	 * 4 : bloc par bloc (espaces originals)
	 */
	public static final int DATAS_WORD = 0, DATAS_LINE = 1, DATAS_ORIGINAL_LINE = 2,  DATAS_BLOC = 3, DATAS_ORIGINAL_BLOC = 4;
	
	/**
	 * le fichier
	 */
	private FileDatas file;
	
	/**
	 * le code de recuperation de donnees
	 */
	private int codeDatas = DATAS_LINE;
	
	/**
	 * le chemin du fichier
	 */
	private String pathFile;


	private IntegerProperty bytesReadProperty = new SimpleIntegerProperty();
	
	/**
	 * constructeur
	 * @param pathFile le chemin du fichier
	 */
	public AbstractParserFile(String pathFile){
		this.pathFile = pathFile;
		this.file = new FileDatas(pathFile);
	}
	
	/**
	 * constructeur
	 * @param pathFile le chemin du fichier
	 * @param codeDatas le code de recuperation de donnees
	 */
	public AbstractParserFile(String pathFile, int codeDatas){
		this.pathFile = pathFile;
		this.file = new FileDatas(pathFile);
		this.codeDatas = codeDatas;
	}
	
	/**
	 * methode permettant de parser le fichier
	 */
	public void toParse(){
		
		file.openFile();
		bytesReadProperty.set(0);
		
		if (file.isOpen()){
			String data  = getNextDatasFile();
			while (data != null){
				//numDataProperty.increaseValue();
				executeAction(data);
				bytesReadProperty.set(file.getBytesRead());
				data = getNextDatasFile();
			}
		}
		
		file.closeFile();
	}
	
	/**
	 * methode permettant de parser le fichier en donnant le nombre de données suivantes à traiter
	 * @param nbDatas le nombre de données suivante à traiter
	 */
	public void toParse(int nbDatas){
		int nb = 0;
		
		if (! file.isOpen()){
			file.openFile();
			bytesReadProperty.set(0);
		}
	
		if (file.isOpen() && nb < nbDatas){
			String data  = getNextDatasFile(); 
			while (data != null && nb < nbDatas){
				//numDataProperty.increaseValue();
				executeAction(data);
				bytesReadProperty.set(file.getBytesRead());
				nb++;
				if (nb < nbDatas){
					data = getNextDatasFile();
				}
			}
			if (data == null){
				file.closeFile();
			}
		}
	}
	
	/**
	 * methode permettant de recuperer les prochaines donnees
	 * @return
	 */
	private String getNextDatasFile(){
		switch (codeDatas){
		case DATAS_WORD :
			return file.nextWord();
		case DATAS_LINE :
			return file.nextLine();
		case DATAS_ORIGINAL_LINE :
			return file.nextOriginalLine();
		case DATAS_BLOC :
			return file.nextBloc();
		case DATAS_ORIGINAL_BLOC :
			return file.nextOriginalBloc();
		default : 
			System.out.println("ERROR CODE DATAS");
		}
		return null;
	}
	
	/**
	 * methode abstraite permettant d'excecuter une action pour une ligne lue
	 * @param line la ligne lue
	 */
	public abstract void executeAction(String line);
	
	public IntegerProperty getBytesReadProperty() {
		return bytesReadProperty;
	}

	public long getFileLength() {
		return file.getFileLength();
	}

}
