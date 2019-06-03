package fr.istic.galaxsim.gui.form;

import fr.istic.galaxsim.gui.ErrorDialog;
import javafx.scene.control.TextField;

import java.util.Optional;

public class DoubleFieldControl extends FieldControl {

    private final TextField field;
    private final BoundsControl<Double> boundsControl = new BoundsControl<>();

    public DoubleFieldControl(TextField field, String fieldName, boolean required) {
        super(fieldName, required);

        this.field = field;
        field.textProperty().addListener((obs, oldValue, newValue) -> {
            field.setText(newValue.replaceAll("[^0-9\\-\\.]", ""));
        });
    }

    public DoubleFieldControl(TextField field, String fieldName, boolean required, double lowerBound, double higherBound) {
        this(field, fieldName, required);

        boundsControl.setHigherBound(higherBound);
        boundsControl.setLowerBound(lowerBound);
    }

    @Override
    public void hideError() {
        field.getStyleClass().remove("field-error");
    }

    public BoundsControl<Double> getBoundsControl() {
        return boundsControl;
    }

    /**
     * Retourne la valeur du champ si celui-ci est rempli
     * A utiliser pour les champ non obligatoires
     *
     * @return La valeur du champ
     */
    public Optional<Double> getOptionalValue() {
        try {
            double value = getValue();
            return Optional.of(value);
        } catch(NumberFormatException e) {
            return Optional.empty();
        }
    }

    public Double getValue() {
        return Double.valueOf(field.getText());
    }

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
