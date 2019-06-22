package fr.istic.galaxsim.gui.form;

import fr.istic.galaxsim.gui.ErrorDialog;

import java.io.File;

/**
 * Controleur du champ de selection de fichier.
 */
public class BrowseFieldControl extends FieldControl {

    private final BrowseField field;

    /**
     * Creer une nouvelle instance de BrowseFieldControl.
     *
     * @param field champ a controler
     * @param required indique si le champ est obligatoire ou non
     */
    public BrowseFieldControl(BrowseField field, boolean required) {
        super(null, required);
        this.field = field;
    }

    @Override
    public void hideError() {
        field.hideError();
    }

    @Override
    public boolean isValid() {
        File file = field.getFile();
        if(file == null && !required) {
            return true;
        }
        if(file == null || !file.isFile()) {
            ErrorDialog.show("Le fichier selectionne n'existe pas ou n'est pas valide");
            return false;
        }
        return true;
    }

    @Override
    public void showError() {
        field.showError();
    }
}
