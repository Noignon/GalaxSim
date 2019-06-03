package fr.istic.galaxsim.gui.form;

public abstract class FieldControl {

    /**
     * Nom du champ controle, utile pour l'affichage des erreurs
     */
    protected String fieldName;

    /**
     * Indique si le champ doit etre rempli ou non
     */
    protected boolean required;

    public FieldControl(String fieldName, boolean required) {
        this.fieldName = fieldName;
        this.required = required;
    }

    public abstract void hideError();
    public abstract boolean isValid();
    public abstract  void showError();

}
