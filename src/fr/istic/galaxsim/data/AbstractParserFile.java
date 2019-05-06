package fr.istic.galaxsim.data;

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
		if (file.isOpen()){
			String data  = getNextDatasFile(); 
			while (data != null){
				executeAction(data);
				data = getNextDatasFile();
			}
		}
		
		file.closeFile();
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
	 * methode abstraite permettant d'excecuter une action pour une ligne lu
	 * @param line la ligne lu
	 */
	public abstract void executeAction(String line);
	
	/**
	 * methode permettant de changer le separateur de bloc
	 * @param separatorBloc le separateur de bloc
	 */
	public void setSeparatorBloc(String separatorBloc){
		this.file = new FileDatas(this.pathFile, separatorBloc);
	}
}