package fr.istic.galaxsim.gui.form;

public class FormControl {

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
