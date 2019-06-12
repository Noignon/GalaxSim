package fr.istic.galaxsim.data;

import fr.istic.galaxsim.gui.form.DoubleFieldControl;
import fr.istic.galaxsim.gui.form.IntegerFieldControl;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Optional;

public class DataExtractionTask extends Task<DataFileType> {

    private final DataFileType dataType;
    private final String filePath;
    private final IntegerFieldControl distanceFieldControl;
    private final IntegerFieldControl massFieldControl;
    private final DoubleFieldControl uncertaintyFieldControl;
    private final ArrayList<DoubleFieldControl> coordsControls;

    private ParserCosmosDatas parser;
    public final DoubleProperty progressProperty = new SimpleDoubleProperty();

    /**
     * Tache permettant d'extraire les donnees du fichier specifie en appliquant des filtres optionnels
     *
     * @param typeDescription description du type de donnees (liste de choix dans le formulaire)
     * @param filePath chemin vers le fichier contenant les donnees
     * @param distanceFieldControl controleur de champ du filtre distance
     * @param massFieldControl controleur de champ du filtre masse
     * @param uncertaintyFieldControl controleur de champ du filtre de marge d'erreur
     * @param coordsControls controleurs des champs de masquage de coordonnees
     */
    public DataExtractionTask(/*DoubleProperty progressProperty, */String typeDescription, String filePath, IntegerFieldControl distanceFieldControl, IntegerFieldControl massFieldControl, DoubleFieldControl uncertaintyFieldControl, ArrayList<DoubleFieldControl> coordsControls) {
        super();
        this.dataType = DataFileType.getTypeFromDescription(typeDescription);
        this.filePath = filePath;
        this.distanceFieldControl = distanceFieldControl;
        this.massFieldControl = massFieldControl;
        this.uncertaintyFieldControl = uncertaintyFieldControl;
        this.coordsControls = coordsControls;
    }

    @Override
    protected DataFileType call() throws Exception {
        // Creation du parser selon le type de donnees a extraire
        switch(dataType) {
            case AMAS:
                parser = new ParserAmasDatas(filePath);
                break;
            case GALAXIES:
                parser = new ParserGalaxiesDatas(filePath);
                break;
            default:
                cancel(true);
                return null;
        }

        parser.getBytesReadProperty().addListener((observableValue, oldValue, newValue) -> {
            double value = (int) newValue;
            double progress = value / (double) parser.getFileLength();
            progressProperty.setValue(progress);
        });

        // Reinitialisation des anciens filtres
        Filter.removeAllFilter();

        // Parametrage des filtres
        Optional<Integer> distanceFilterValue = distanceFieldControl.getOptionalValue();
        if(distanceFilterValue.isPresent()) {
            Filter.setDistanceFilter(distanceFilterValue.get());
        }
        else {
            Filter.setDistanceFilter(100);
        }

        Optional<Integer> massFilterValue = massFieldControl.getOptionalValue();
        if(massFilterValue.isPresent()) {
            Filter.setMassFilter(massFilterValue.get());
        }

        Optional<Double> uncertaintyFilterValue = uncertaintyFieldControl.getOptionalValue();
        if(uncertaintyFilterValue.isPresent()) {
            Filter.setDeviationUncertaintyFilter(uncertaintyFilterValue.get());
        }

        for(int i = 0;i < coordsControls.size();i++) {
            DoubleFieldControl control = coordsControls.get(i);
            Optional<Double> value = control.getOptionalValue();
            if(value.isPresent()) {
                switch(i) {
                    case 0:
                        Filter.setCoordinateXMinFilter(value.get());
                        break;
                    case 1:
                        Filter.setCoordinateXMaxFilter(value.get());
                        break;
                    case 2:
                        Filter.setCoordinateYMinFilter(value.get());
                        break;
                    case 3:
                        Filter.setCoordinateYMaxFilter(value.get());
                        break;
                    case 4:
                        Filter.setCoordinateZMinFilter(value.get());
                        break;
                    case 5:
                        Filter.setCoordinateZMaxFilter(value.get());
                        break;
                }
            }
        }

        // Extraction des donnees
        parser.toParse();

        // Suppression des anciennes donnees
        DataBase.removeAllAmas();
        DataBase.removeAllGalaxies();

        // Ajout des donnees dans la base de donnees
        switch(dataType) {
            case AMAS:
                DataBase.initAmas(parser.getAllDatas());
                break;
            case GALAXIES:
                DataBase.initGalaxies(parser.getAllDatas());
                break;
        }

        return dataType;
    }

}
