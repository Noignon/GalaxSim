package fr.istic.galaxsim.gui.form;

import fr.istic.galaxsim.gui.ErrorDialog;
import javafx.scene.control.TextField;

import java.util.Optional;

public class IntegerFieldControl extends FieldControl {

    private final TextField field;
    private Optional<Integer> lowerBound = Optional.empty();
    private Optional<Integer> higherBound = Optional.empty();

    public IntegerFieldControl(TextField field, String fieldName, boolean required) {
        super(fieldName, required);
        this.field = field;

        // Seuls les chiffres sont acceptes, les autres sont effaces
        field.textProperty().addListener((obs, oldValue, newValue) -> {
            field.setText(newValue.replaceAll("[^0-9\\-]", ""));
        });
    }

    public IntegerFieldControl(TextField field, String fieldName, boolean required, int lowBound, int hightBound) {
        this(field, fieldName, required);

        setHigherBound(hightBound);
        setLowerBound(lowBound);
    }

    public int getHigherBound() {
        return higherBound.get();
    }

    public int getLowerBound() {
        return lowerBound.get();
    }

    /**
     * Retourne la valeur du champ si celui-ci est rempli
     * A utiliser pour les champ non obligatoires
     *
     * @return La valeur du champ
     */
    public Optional<Integer> getOptionalValue() {
        try {
            int value = getValue();
            return Optional.of(value);
        } catch(NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Recupere la valeur du champ de texte et la convertir en un entier
     *
     * @throws NumberFormatException si la valeur saisie n'est pas valide
     * @return la valeur du champ
     */
    public int getValue() {
        return Integer.parseInt(field.getText());
    }

    /**
     * Determine si la valeur du champ est bien comprise entre la borne inferieure (inclue)
     * et la borne superieure (exclue)
     *
     * @return true si la valeur est comprise dans l'interval, false sinon
     */
    @Override
    public boolean isValid() {
        if(field.getText().isEmpty() && !required) {
            return true;
        }

        int value;
        try {
            value = getValue();
        } catch(NumberFormatException e) {
            ErrorDialog.show("La valeur du champ " + fieldName + " n'est pas valide");
            return false;
        }

        if(lowerBound.isPresent() && value < getLowerBound()) {
            ErrorDialog.show("La valeur du champ " + fieldName + " doit etre superieure ou egale a " + getLowerBound());
            return false;
        }
        else if(higherBound.isPresent() && value >= getHigherBound()) {
            ErrorDialog.show("La valeur du champ " + fieldName + " doit etre inferieure a " + getHigherBound());
            return false;
        }
        else {
            return true;
        }
    }

    public void setHigherBound(int value) {
        higherBound = Optional.of(value);
    }

    public void setLowerBound(int value) {
        lowerBound = Optional.of(value);
    }

    @Override
    public void hideError() {
        field.getStyleClass().remove("field-error");
    }

    @Override
    public void showError() {
        field.getStyleClass().add("field-error");
    }

}
