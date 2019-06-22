package fr.istic.galaxsim.gui.form;

/**
 * Verification de la validite des formulaires
 */
public class FormControl {

    /**
     * Verifie la validite des champs passes en parametre.
     *
     * @param controls champs a verifier
     * @return true si tous les champs sont valides, false sinon
     */
    public static boolean isValid(FieldControl... controls) {
        boolean valid = true;
        for(FieldControl control : controls) {
            if(!control.isValid()) {
                valid = false;
                control.showError();
            }
            else {
                control.hideError();
            }
        }

        return valid;
    }

}
