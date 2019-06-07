package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.calcul.CalcsProcessing;
import fr.istic.galaxsim.data.*;
import fr.istic.galaxsim.gui.form.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;

public class MainWindow {

    // Elements de l'interface graphique
    @FXML
    private StackPane pane3D;
    @FXML
    private ChoiceBox<String> dataTypeField;
    @FXML
    private Label infoLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private BrowseField dataFileField;
    @FXML
    private VBox dataPane;
    @FXML
    private CosmosElementInfos cosmosElementInfos;

    // Champs de filtres
    @FXML
    private TextField massField;
    @FXML
    private TextField distanceField;
    @FXML
    private TextField uncertaintyField;
    @FXML
    private GridPane coordsFilterPane;

    // Controles de l'animation
    @FXML
    private Slider animationProgress;
    @FXML
    private ImageView playButton;
    @FXML
    private ImageView pauseButton;
    @FXML
    private ImageView stopButton;
    @FXML
    private TextField durationField;

    private Universe universe;

    // Controleurs de valeur pour les filtres
    private BrowseFieldControl dataFileFieldControl;
    private IntegerFieldControl distanceFieldControl;
    private IntegerFieldControl massFieldControl;
    private DoubleFieldControl uncertaintyFieldControl;
    private ArrayList<DoubleFieldControl> coordsFilterControls = new ArrayList<>();

    private IntegerFieldControl durationFieldControl;
    private boolean simulationRunning = false;

    public MainWindow(){

    }

    @FXML
    public void initialize() {
        // Ajout des types de donnees possibles dans le formulaire de selection
        dataTypeField.getItems().addAll(DataFileType.getDescriptions());
        dataTypeField.setValue(dataTypeField.getItems().get(0));

        // La barre de chargement est uniquement affichee lorsque des donnees sont traitees
        progressBar.setManaged(false);

        // Ajout de controles sur les champs pour verifier la validite des donnees
        dataFileFieldControl = new BrowseFieldControl(dataFileField, true);
        distanceFieldControl = new IntegerFieldControl(distanceField, "distance", false, 0, 101);

        massFieldControl = new IntegerFieldControl(massField, "masse", false);
        massFieldControl.getBoundsControl().setLowerBound(0);

        uncertaintyFieldControl = new DoubleFieldControl(uncertaintyField, "marge d'erreur", false);
        uncertaintyFieldControl.getBoundsControl().setLowerBound(0.0);

        // Creation des controles pour les filtres de masquage de coordonnees
        // La GridPane contient les 6 filtres
        String[] fieldNames = { "X min", "X max", "Y min", "Y max", "Z min", "Z max" };
        for(int i = 0; i < coordsFilterPane.getChildren().size();i++) {
            Node field = coordsFilterPane.getChildren().get(i);
            DoubleFieldControl control = new DoubleFieldControl((TextField) field, fieldNames[i], false);

            // Stockage des controles dans un tableau pour les utiliser
            // a la validation
            coordsFilterControls.add(control);
        }

        Group sceneRoot = new Group();

        universe = new Universe(pane3D, cosmosElementInfos);
        universe.setTranslateZ(450);

        AxesIndicator axes = new AxesIndicator(0.8f);
        //Translate t = new Translate(-50, 70, -80);
        Rotate rx = new Rotate(0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        rx.angleProperty().bind(universe.rotateX.angleProperty());
        ry.angleProperty().bind(universe.rotateY.angleProperty());
        axes.getTransforms().addAll(new Translate(), rx, ry);

        // Creation de la camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-250);
        camera.setFarClip(5000);
        camera.setNearClip(1);

        // Creation de la scene contenant la simulation
        SubScene simScene = new SubScene(sceneRoot, 1, 1);
        simScene.setCamera(camera);
        // La scene possede la meme taille que son pere (pane3d)
        simScene.widthProperty().bind(pane3D.widthProperty());
        simScene.heightProperty().bind(pane3D.heightProperty());

        sceneRoot.getChildren().addAll(universe, axes);
        pane3D.getChildren().addAll(simScene);

        // Positionnement du panneau en bas a droite de la fenetre
        cosmosElementInfos.widthProperty().addListener((obs, oldValue, newValue) -> {
            cosmosElementInfos.setTranslateX((pane3D.getWidth() - cosmosElementInfos.getWidth()) / 2 - 7);
        });
        cosmosElementInfos.heightProperty().addListener((obs, oldValue, newValue) -> {
            cosmosElementInfos.setTranslateY((pane3D.getHeight() - cosmosElementInfos.getHeight()) / 2 - 7);
        });

        // La fenetre doit etre affichee au premier plan
        cosmosElementInfos.setViewOrder(-1.0);
        cosmosElementInfos.setVisible(false);

        durationFieldControl = new IntegerFieldControl(durationField, "duree", true);
        durationFieldControl.getBoundsControl().setLowerBound(1);
        dataPane.setVisible(false);
    }

    @FXML
    /**
     * Verifie la validite des champs du formulaire de filtres et lance
     * l'extraction des donnees dans le fichier selectionne
     *
     * @param event evenemment associe au bouton du formulaire (non utilise)
     */
    private void startDataAnalysis(ActionEvent event) {
        // Verification de la validite des champs
        if(!FormControl.isValid(dataFileFieldControl, distanceFieldControl, massFieldControl, uncertaintyFieldControl) ||
                !FormControl.isValid(coordsFilterControls.toArray(new DoubleFieldControl[coordsFilterControls.size()]))) {
            return;
        }

        DataExtractionTask parserDataTask = new DataExtractionTask(dataTypeField.getValue(), dataFileField.getPath(),
                                                                    distanceFieldControl, massFieldControl,
                                                                    uncertaintyFieldControl, coordsFilterControls);

        // Mise en relation de l'avancement de l'extraction des donnees avec
        // la barre de chargement
        parserDataTask.progressProperty.bindBidirectional(progressBar.progressProperty());

        parserDataTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, taskEvent -> {
            Platform.runLater(() -> {
                CalcsProcessing.coordsCalculation();
                CalcsProcessing.processAmas();
                CalcsProcessing.processGalaxies();

                // Ajout des amas et des galaxies a l'ecran
                universe.clear();

                for(Galaxy g : DataBase.tableGalaxies) {
                    if(Filter.goodCoordinate(g)) {
                        universe.addGalaxy(g);
                    }
                }

                for(Amas a : DataBase.tableAmas) {
                    if(Filter.goodCoordinate(a)) {
                        universe.addAmas(a);
                    }
                }
                
                boolean animationChanging=false;

                // Affichage de l'avancement de l'animation
                
                ReadOnlyObjectProperty<Duration> prop = universe.getTimeProperty();
                if(prop != null) {
                    prop.addListener((listener) -> {
                    	if(!animationChanging) {
                    		animationProgress.setValue(prop.get().toSeconds());
                    	}
                        
                    });
                }
                
                
                /*
                animationProgress.valueProperty().addListener((l)->{
                	universe.playTransitionsFrom(animationProgress.getValue());
                });
                
                */
                
                
                

                infoLabel.setText(String.format("Il y a %d amas et %d galaxies dans le fichier", DataBase.getNumberAmas(), DataBase.getNumberGalaxies()));

                // Masquage de la barre de chargement
                progressBar.setManaged(false);
                progressBar.setVisible(false);

                // Affichage du controle de la simulation
                dataPane.setVisible(true);
            });

        });

        // Lancement de l'extraction des donnees
        new Thread(parserDataTask).start();

        // Affichage de la barre de chargement
        progressBar.setManaged(true);
        progressBar.setVisible(true);
    }

    /**
     * Arret de la simulation
     * @param event
     */
    @FXML
    private void stopSimulation(MouseEvent event) {
        Platform.runLater(() -> {
            universe.stopTransitions();
        });

        simulationRunning = false;
        playButton.setVisible(true);
        pauseButton.setVisible(false);
        animationProgress.setValue(0.0);
    }

    /**
     * Mise en lecture ou en pause de la simulation
     *
     * @param event
     */
    @FXML
    private void toggleSimulation(MouseEvent event) {
        simulationRunning = !simulationRunning;

        if(simulationRunning) {
            if(!durationFieldControl.isValid()) {
                durationFieldControl.showError();
                return;
            }

            durationFieldControl.hideError();
            animationProgress.setMax(durationFieldControl.getValue());
            Platform.runLater(() -> {
                universe.setSimulationDuration(durationFieldControl.getValue());
                universe.playTransitionsFromStart();
            });
        }
        else {
            Platform.runLater(() -> {
                universe.pauseTransitions();
            });
        }

        playButton.setVisible(!playButton.isVisible());
        pauseButton.setVisible(!pauseButton.isVisible());
    }

    @FXML
    private void updateSimulationPosition(MouseEvent event) {
        universe.playTransitionsFrom(animationProgress.getValue());
    }

}
