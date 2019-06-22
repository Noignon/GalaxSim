package fr.istic.galaxsim.gui.form;

/**
 * Controleur abstrait sur un champ de formulaire.
 */
public abstract class FieldControl {

    /**
     * Nom du champ controle, utile pour l'affichage des erreurs
     */
    protected String fieldName;

    /**
     * Indique si le champ doit etre rempli ou non
     */
    protected boolean required;

    /**
     * Creer une nouvelle instance de FieldControl.
     *
     * @param fieldName nom du champ controle
     * @param required indique si le champ est obligatoire ou non
     */
    public FieldControl(String fieldName, boolean required) {
        this.fieldName = fieldName;
        this.required = required;
    }

    /**
     * Masque l'erreur.
     */
    public abstract void hideError();

    /**
     * Verifie la validite du champ et la retourne.
     */
    public abstract boolean isValid();

    /**
     * Affiche une erreur.
     */
    public abstract  void showError();

}
