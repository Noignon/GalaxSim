package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.calcul.Traitement;
import fr.istic.galaxsim.data.*;
import fr.istic.galaxsim.gui.form.BrowseField;
import fr.istic.galaxsim.gui.form.BrowseFieldControl;
import fr.istic.galaxsim.gui.form.FormControl;
import fr.istic.galaxsim.gui.form.IntegerFieldControl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.Optional;

public class MainWindow {

	@FXML
	private StackPane pane3D;
	@FXML
	private ChoiceBox<String> dataTypeField;
	@FXML
    private Label infoLabel;
	@FXML
    private ProgressBar progressBar;
	@FXML
    private TextField distanceField;
	@FXML
    private BrowseField dataFileField;
	@FXML
    private TextField massField;
    @FXML
	private GalaxyInfos galaxyInfos;

    private Universe universe;

    private BrowseFieldControl dataFileFieldControl;
	private IntegerFieldControl distanceFieldControl;
	private IntegerFieldControl massFieldControl;
	
	public MainWindow(){

    }
	
	@FXML
    public void initialize() {
        dataTypeField.getItems().addAll(DataFileType.getDescriptions());
        dataTypeField.setValue(dataTypeField.getItems().get(0));

        // La barre de chargement est uniquement affichee lorsque des donnees sont traitees
        progressBar.setManaged(false);

        // Ajout de controles sur les champs pour verifier la validite des donnees
		dataFileFieldControl = new BrowseFieldControl(dataFileField, true);
		distanceFieldControl = new IntegerFieldControl(distanceField, "distance", false, 0, 100);

		massFieldControl = new IntegerFieldControl(massField, "masse", false);
        massFieldControl.setLowerBound(0);

		Group sceneRoot = new Group();

		universe = new Universe(pane3D, galaxyInfos);

		AxesIndicator axes = new AxesIndicator(0.8f);
        //Translate t = new Translate(-50, 70, -80);
        Rotate rx = new Rotate(0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        rx.angleProperty().bind(universe.rotateX.angleProperty());
        ry.angleProperty().bind(universe.rotateY.angleProperty());
        axes.getTransforms().addAll(new Translate(), rx, ry);

		// Declaration de la camera
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
        galaxyInfos.widthProperty().addListener((obs, oldValue, newValue) -> {
            galaxyInfos.setTranslateX((pane3D.getWidth() - galaxyInfos.getWidth()) / 2 - 7);
        });
        galaxyInfos.heightProperty().addListener((obs, oldValue, newValue) -> {
            galaxyInfos.setTranslateY((pane3D.getHeight() - galaxyInfos.getHeight()) / 2 - 7);
        });

        // La fenetre doit etre affichee au premier plan
        galaxyInfos.setViewOrder(-1.0);
        galaxyInfos.setVisible(false);
	}

	@FXML
	private void startDataAnalysis(ActionEvent event) {
        if(!FormControl.isValid(dataFileFieldControl, distanceFieldControl, massFieldControl)) {
            return;
        }

        Task parseDataTask = new Task<DataFileType>() {
            @Override
            protected DataFileType call() throws Exception {
                progressBar.setManaged(true);
                progressBar.setVisible(true);
                DataFileType type = DataFileType.getTypeFromDescription(dataTypeField.getValue());
                ParserCosmosDatas parser;
                switch(type) {
                    case AMAS:
                        parser = new ParserAmasDatas(dataFileField.getPath());
                        break;
                    case GALAXIES:
                        parser = new ParserGalaxiesDatas(dataFileField.getPath());
                        break;
                    default:
                        cancel(true);
                        return null;
                }

                parser.getBytesReadProperty().addListener((observableValue, oldValue, newValue) -> {
                    Platform.runLater(() -> {
                        double value = (int) newValue;
                        double progress = value / (double) parser.getFileLength();
                        progressBar.setProgress(progress);
                    });
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
                    System.out.println("La masse : " + massFilterValue.get());
                    Filter.setMassFilter(massFilterValue.get());
                }

                // Extraction des donnees
                parser.toParse();

                // Suppression des anciennes donnees
                DataBase.removeAllAmas();
                DataBase.removeAllGalaxies();

                // Ajout des donnees dans la base de donnees
                switch(type) {
                    case AMAS:
                        DataBase.initAmas(parser.getAllDatas());
                        break;
                    case GALAXIES:
                        DataBase.initGalaxies(parser.getAllDatas());
                        break;
                }

                return type;
            }
        };
        parseDataTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, taskEvent -> {
            Traitement.calculCoordonnee();
            Traitement.traitementAmas();
            Traitement.traitementGalaxies();

            universe.clear();
            for(Galaxy g : DataBase.tableGalaxies) {
                universe.addGalaxy(g);
            }

            for(Amas a : DataBase.tableAmas) {
                universe.addAmas(a);
            }

			infoLabel.setText(String.format("Il y a %d amas et %d galaxies dans le fichier", DataBase.getNumberAmas(), DataBase.getNumberGalaxies()));
            progressBar.setManaged(false);
            progressBar.setVisible(false);
		});
        new Thread(parseDataTask).start();
	}

}
