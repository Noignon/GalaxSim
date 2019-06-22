package fr.istic.galaxsim.gui.form;

import java.util.Optional;

/**
 * Classe permettant de restreindre la valeur d'un nombre entre des bornes (optionnelles).
 *
 * @param <T> Type de l'objet a restreindre
 */
public class BoundsControl<T extends Comparable<T>> {

    private Optional<T> higherBound = Optional.empty();
    private Optional<T> lowerBound = Optional.empty();

    /**
     * Valeur de la borne superieure (exclue).
     */
    public T getHigherBound() {
        return higherBound.get();
    }

    /**
     * Valeur de la borne superiere (inclue).
     */
    public T getLowerBound() {
        return lowerBound.get();
    }

    /**
     * Indique si la valeur satisfait la borne superiere si celle-ci
     * a ete definie.
     *
     * @param value valeur a tester
     */
    public boolean satisfiesHigherBound(T value) {
        return !higherBound.isPresent() || value.compareTo(getHigherBound()) < 0;
    }

    /**
     * Indique si la valeur satisfait la borne inferieure si celle-ci
     * a ete definie.
     *
     * @param value valeur a tester
     */
    public boolean satisfiesLowerBound(T value) {
        return !lowerBound.isPresent() || value.compareTo(getLowerBound()) >= 0;
    }

    /**
     * Definit la valeur de la borne superiere (exclue).
     *
     * @param value valeur de la borne superieure
     */
    public void setHigherBound(T value) {
        higherBound = Optional.of(value);
    }

    /**
     * Definit la valeur de la borne inferieure (inclue).
     *
     * @param value valeur de la borne inferieure
     */
    public void setLowerBound(T value) {
        lowerBound = Optional.of(value);
    }

}
