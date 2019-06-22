package fr.istic.galaxsim.gui.form;

import fr.istic.galaxsim.gui.ErrorDialog;
import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * Controleur sur un champ de texte recevant un nombre decimal.
 */
public class DoubleFieldControl extends FieldControl {

    private final TextField field;
    private final BoundsControl<Double> boundsControl = new BoundsControl<>();

    /**
     * Creer une nouvelle instance de DoubleFieldControl.
     *
     * @param field champ de texte a controler
     * @param fieldName nom du champ
     * @param required indique si le champ est obligatoire ou non
     */
    public DoubleFieldControl(TextField field, String fieldName, boolean required) {
        super(fieldName, required);

        this.field = field;
        field.textProperty().addListener((obs, oldValue, newValue) -> {
            field.setText(newValue.replaceAll("[^0-9\\-\\.]", ""));
        });
    }

    /**
     * Creer une nouvelle instance de DoubleFieldControl.
     *
     * @param field champ de texte a controler
     * @param fieldName nom du champ
     * @param required indique si le champ est obligatoire ou non
     * @param lowerBound valeur de la borne inferieure (inclue)
     * @param higherBound valeur de la borne superiere (exclue)
     */
    public DoubleFieldControl(TextField field, String fieldName, boolean required, double lowerBound, double higherBound) {
        this(field, fieldName, required);

        boundsControl.setHigherBound(higherBound);
        boundsControl.setLowerBound(lowerBound);
    }

    /**
     * Retourne le controleur de bornes.
     */
    public BoundsControl<Double> getBoundsControl() {
        return boundsControl;
    }

    /**
     * Retourne la valeur du champ si celui-ci est rempli.
     * A utiliser pour les champs non obligatoires.
     */
    public Optional<Double> getOptionalValue() {
        try {
            double value = getValue();
            return Optional.of(value);
        } catch(NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Recupere la valeur du champ de texte et la convertie en un nombre decimal.
     *
     * @throws NumberFormatException si la valeur saisie n'est pas valide
     */
    public Double getValue() {
        return Double.valueOf(field.getText());
    }

    @Override
    public void hideError() {
        field.getStyleClass().remove("field-error");
    }

    /**
     * Determine si la valeur du champ est bien comprise entre la borne inferieure (inclue).
     * et la borne superieure (exclue).
     *
     * @return true si la valeur est comprise dans l'interval, false sinon
     */
    @Override
    public boolean isValid() {
        if(field.getText().isEmpty() && !required) {
            return true;
        }

        double value;
        try {
            value = getValue();
        } catch(NumberFormatException e) {
            ErrorDialog.show("La valeur du champ " + fieldName + " n'est pas valide");
            return false;
        }

        if(!boundsControl.satisfiesLowerBound(value)) {
            ErrorDialog.show("La valeur du champ " + fieldName + " doit etre superieure ou egale a " + boundsControl.getLowerBound());
            return false;
        }
        else if(!boundsControl.satisfiesHigherBound(value)) {
            ErrorDialog.show("La valeur du champ " + fieldName + " doit etre inferieure a " + boundsControl.getHigherBound());
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void showError() {
        field.getStyleClass().add("field-error");
    }
}
