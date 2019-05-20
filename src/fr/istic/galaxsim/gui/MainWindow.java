package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.data.ParserAmasDatas;
import fr.istic.galaxsim.data.ParserCosmosDatas;
import fr.istic.galaxsim.data.ParserGalaxiesDatas;
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
import javafx.stage.FileChooser;

import java.io.File;

public class MainWindow {

	@FXML
	private TextField dataFileField;
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
	private GalaxyInfos galaxyInfos;

	private File currentDataFile;
	
	public MainWindow(){
		currentDataFile = null;
    }
	
	@FXML
    public void initialize() {
        dataTypeField.getItems().addAll(DataFileType.getDescriptions());
        dataTypeField.setValue(dataTypeField.getItems().get(0));

        // La barre de chargement est uniquement affichee lorsque des donnees sont traitees
        progressBar.setManaged(false);

        //IntegerFieldControl control = new IntegerFieldControl(distanceField);

		Group sceneRoot = new Group();

		Universe universe = new Universe(pane3D);

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
	}

	@FXML
	private void openFileBrowser(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sélection d'un jeu de données");
		currentDataFile = fileChooser.showOpenDialog(null);

		if(currentDataFile != null) {
            dataFileField.setText(currentDataFile.getAbsolutePath());
        }
	}

	@FXML
	private void startDataAnalysis(ActionEvent event) {
        // @TODO verifier la validite des champs
        if(currentDataFile == null) {
            currentDataFile = new File(dataFileField.getText());
        }

        if(!currentDataFile.isFile()) {
            System.out.println("Le fichier n'existe pas");
            return;
        }

        Task parseDataTask = new Task<ParserCosmosDatas>() {
            @Override
            protected ParserCosmosDatas call() throws Exception {
                progressBar.setManaged(true);
                progressBar.setVisible(true);
                DataFileType type = DataFileType.getTypeFromDescription(dataTypeField.getValue());
                ParserCosmosDatas parser;
                switch(type) {
                    case AMAS:
                        parser = new ParserAmasDatas(currentDataFile.getAbsolutePath());
                        break;
                    case GALAXIES:
                        parser = new ParserGalaxiesDatas(currentDataFile.getAbsolutePath());
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

                parser.toParse();
                return parser;
            }
        };
        parseDataTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, taskEvent -> {
            ParserCosmosDatas parser = (ParserCosmosDatas) parseDataTask.getValue();
			infoLabel.setText("Nombre d'elements trouves : " + parser.getAllDatas().length);
            progressBar.setManaged(false);
            progressBar.setVisible(false);
		});
        new Thread(parseDataTask).start();
	}

}
