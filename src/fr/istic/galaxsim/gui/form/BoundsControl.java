package fr.istic.galaxsim.gui.form;

import java.util.Optional;

public class BoundsControl<T extends Comparable<T>> {

    private Optional<T> higherBound = Optional.empty();
    private Optional<T> lowerBound = Optional.empty();

    public T getHigherBound() {
        return higherBound.get();
    }

    public T getLowerBound() {
        return lowerBound.get();
    }

    public boolean satisfiesHigherBound(T value) {
        return !higherBound.isPresent() || value.compareTo(getHigherBound()) < 0;
    }

    public boolean satisfiesLowerBound(T value) {
        return !lowerBound.isPresent() || value.compareTo(getLowerBound()) >= 0;
    }

    public void setHigherBound(T value) {
        higherBound = Optional.of(value);
    }

    public void setLowerBound(T value) {
        lowerBound = Optional.of(value);
    }

}
