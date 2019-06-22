package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.calcul.CalcsProcessing;
import fr.istic.galaxsim.data.*;
import fr.istic.galaxsim.gui.form.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controleur de la fenetre principale de l'animation
 */
public class MainWindow {

    // Elements de l'interface graphique
    @FXML
    private StackPane pane3D;
    @FXML
    private VBox leftPane;
    @FXML
    private ChoiceBox<String> dataTypeField;
    @FXML
    private Label infoLabel;
    @FXML
    private StackPane progressPane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressStatus;
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
    private ToggleImageButton playPauseButton;
    @FXML
    private ImageView stopButton;
    @FXML
    private TextField durationField;
    @FXML
    private CheckBox showAxesIndicator;
    @FXML
    private CheckBox showTrails;
    @FXML
    private Label elapsedTimeLabel;

    private Universe universe;

    // Controleurs de valeur pour les filtres
    private BrowseFieldControl dataFileFieldControl;
    private IntegerFieldControl distanceFieldControl;
    private IntegerFieldControl massFieldControl;
    private DoubleFieldControl uncertaintyFieldControl;
    private ArrayList<DoubleFieldControl> coordsFilterControls = new ArrayList<>();

    private IntegerFieldControl durationFieldControl;

    private boolean simulationFinished = false;
    private boolean simulationRunning = false;

    /**
     * Initialisation des composants de la fenetre.
     */
    @FXML
    public void initialize() {
        // Ajout des types de donnees possibles dans le formulaire de selection
        dataTypeField.getItems().addAll(DataFileType.getDescriptions());
        dataTypeField.setValue(dataTypeField.getItems().get(0));

        // La barre de chargement est uniquement affichee lorsque des donnees sont traitees
        progressPane.setManaged(false);

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

        AxesIndicator axes = new AxesIndicator(4f);
        axes.visibleProperty().bind(showAxesIndicator.selectedProperty());
        Rotate rx = new Rotate(0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        rx.angleProperty().bind(universe.rotateX.angleProperty());
        ry.angleProperty().bind(universe.rotateY.angleProperty());
        axes.getTransforms().addAll(rx, ry);

        // Creation de la camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-250);
        camera.setFarClip(5000);
        camera.setNearClip(1);

        // Creation de la scene contenant la simulation
        SubScene simScene = new SubScene(sceneRoot, 1, 1, false, SceneAntialiasing.BALANCED);
        simScene.setCamera(camera);
        // La scene possede la meme taille que son pere (pane3d)
        simScene.widthProperty().bind(pane3D.widthProperty());
        simScene.heightProperty().bind(pane3D.heightProperty());
        simScene.setManaged(false);

        sceneRoot.getChildren().addAll(universe);
        
        pane3D.getChildren().addAll(simScene, axes);

        // Le panneau de gauche n'a pas besoin d'etre agrandi
        SplitPane.setResizableWithParent(leftPane, false);

        ChangeListener sizeListener = (obs, oldValue, newValue) -> {
            // Positionnement du panneau en bas a droite de la fenetre
            cosmosElementInfos.setTranslateX((pane3D.getWidth() - cosmosElementInfos.getWidth()) / 2 - 7);
            cosmosElementInfos.setTranslateY((pane3D.getHeight() - cosmosElementInfos.getHeight()) / 2 - 7);

            // Positionnement de l'indicateur d'axe en bas a gauche de la fenetre
            axes.setTranslateX(-pane3D.getWidth() / 2 + 50);
            axes.setTranslateY(pane3D.getHeight() / 2 - 50);
        };

        pane3D.widthProperty().addListener(sizeListener);
        pane3D.heightProperty().addListener(sizeListener);

        // La fenetre doit etre affichee au premier plan
        cosmosElementInfos.setViewOrder(-1.0);
        cosmosElementInfos.setVisible(false);

        durationFieldControl = new IntegerFieldControl(durationField, "duree", true);
        durationFieldControl.getBoundsControl().setLowerBound(1);
        dataPane.setVisible(false);

        universe.getSimulation().setOnFinished((event) -> {
            simulationFinished = true;
            simulationRunning = false;

            playPauseButton.setFirstButtonVisibility(true);
        });

        // On souhaite garder seulement 2 decimales
        DecimalFormat elapsedYearsFormat = new DecimalFormat("#0.00");

        // Affichage de l'avancement de l'animation
        universe.getSimulation().currentTimeProperty().addListener((obs, oldValue, newValue) -> {
            animationProgress.setValue(newValue.toSeconds());

            // Affichage du temps ecoule
            double position = newValue.divide(universe.getSimulation().getTotalDuration().toSeconds()).toSeconds();
            elapsedTimeLabel.setText(elapsedYearsFormat.format(position * CalcsProcessing.T));
        });

        universe.getSimulation().trailVisibility.bind(showTrails.selectedProperty());
    }

    @FXML
    /**
     * Verifie la validite des champs du formulaire de filtres et lance
     * l'extraction des donnees dans le fichier selectionne.
     *
     * @param event evenemment associe au bouton du formulaire (non utilise)
     */
    private void startDataAnalysis(ActionEvent event) {
        // Verification de la validite des champs
        if(!FormControl.isValid(dataFileFieldControl, distanceFieldControl, massFieldControl, uncertaintyFieldControl) ||
                !FormControl.isValid(coordsFilterControls.toArray(new DoubleFieldControl[coordsFilterControls.size()]))) {
            return;
        }

        // Affichage de la barre de chargement
        progressPane.setManaged(true);
        progressPane.setVisible(true);

        DataExtractionTask parserDataTask = new DataExtractionTask(dataTypeField.getValue(), dataFileField.getPath(),
                                                                    distanceFieldControl, massFieldControl,
                                                                    uncertaintyFieldControl, coordsFilterControls);

        CalcsProcessing calcsProcessing = new CalcsProcessing();
        calcsProcessing.setOnRunning((e) -> {
            progressStatus.setText("Calcul des coordonnees");
        });

        /* Mise en relation de la barre de chargement avec l'avancement
           des 2 taches. Il n'est pas possible de bind deux observeurs sur une
           meme propriete, c'est pourquoi il faut connecter le premier
           observeur sur le second */
        calcsProcessing.progressProperty.bindBidirectional(parserDataTask.progressProperty);
        progressBar.progressProperty().bindBidirectional(parserDataTask.progressProperty);

        // Utilisation d'un ExecutorService pour executer les taches les
        // unes apres les autres dans l'ordre
        ExecutorService executor = Executors.newSingleThreadExecutor();

        progressStatus.setText("Extraction des donnees");
        executor.submit(parserDataTask);
        executor.submit(calcsProcessing);

        executor.submit(() -> {
            Platform.runLater(() -> {
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

                infoLabel.setText(String.format("Il y a %d amas et %d galaxies dans le fichier", DataBase.getNumberAmas(), DataBase.getNumberGalaxies()));

                // Masquage de la barre de chargement
                progressPane.setManaged(false);
                progressPane.setVisible(false);

                // Affichage du controle de la simulation
                dataPane.setVisible(true);
            });
        });

        executor.shutdown();
    }

    /**
     * Arret de la simulation.
     *
     * @param event
     */
    @FXML
    private void stopSimulation(MouseEvent event) {
        universe.getSimulation().stopSimulation();

        simulationRunning = false;
        playPauseButton.setFirstButtonVisibility(true);
        animationProgress.setValue(0.0);
    }

    /**
     * Mise en lecture / pause de la simulation.
     *
     * @param event
     */
    @FXML
    private void toggleSimulation(MouseEvent event) {
        simulationRunning = !simulationRunning;
        Simulation sim = universe.getSimulation();

        if(simulationRunning) {
            // Reinitialisation de l'animation si l'utiliseur clique sur
            // le bouton play alors que celle-ci est deja terminee
            if(simulationFinished) {
                simulationFinished = false;
                simulationRunning = false;

                sim.stopSimulation();
                animationProgress.setValue(0.0);
                return;
            }

            // Verification de la valeur du champ de duree
            if(!durationFieldControl.isValid()) {
                durationFieldControl.showError();
                return;
            }

            durationFieldControl.hideError();
            animationProgress.setMax(durationFieldControl.getValue());

            sim.setDuration(Duration.seconds(durationFieldControl.getValue()));
            sim.play();
        }
        else {
            sim.pause();
        }

        playPauseButton.toggle();
    }

    /**
     * Met a jour la position de l'animation en fonction de la valeur
     * du slider.
     *
     * @param event
     */
    @FXML
    private void updateSimulationPosition(MouseEvent event) {
        Platform.runLater(() -> {
            universe.getSimulation().setTimePosition(animationProgress.getValue());
        });
    }

}
