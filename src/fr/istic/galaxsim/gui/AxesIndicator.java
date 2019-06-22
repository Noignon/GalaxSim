package fr.istic.galaxsim.gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.fxyz3d.shapes.primitives.ConeMesh;

/**
 * Indicateur representant les axes X, Y et Z.
 */
public class AxesIndicator extends Group {

    /**
     * Creer une nouvelle instance de AxesIndicator.
     *
     * @param scale echelle de l'element
     */
    public AxesIndicator(double scale) {
        // Definition des textures pour chaque axe
        PhongMaterial materialX = new PhongMaterial(Color.RED);
        PhongMaterial materialY = new PhongMaterial(Color.GREEN);
        PhongMaterial materialZ = new PhongMaterial(Color.BLUE);

        Rotate xRotate = new Rotate(90, Rotate.Z_AXIS);
        Rotate zRotate = new Rotate(-90, Rotate.X_AXIS);

        // Creation des cylindres representant chaque axe
        Cylinder xAxis = new Cylinder(0.8f, 10f);
        xAxis.getTransforms().addAll(new Translate(xAxis.getHeight() / 2, 0, 0), xRotate);
        xAxis.setMaterial(materialX);

        Cylinder yAxis = new Cylinder(0.8f, 10f);
        yAxis.setMaterial(materialY);
        yAxis.setTranslateY(yAxis.getTranslateY() - yAxis.getHeight() / 2);

        Cylinder zAxis = new Cylinder(0.8f, 10f);
        zAxis.getTransforms().addAll(new Translate(0, 0, zAxis.getHeight() / 2), zRotate);
        zAxis.setMaterial(materialZ);

        // Un cone est ajoute au bout de chaque cylindre pour representer la direction de l'axe
        ConeMesh coneX = new ConeMesh(1f, 1.5f);
        coneX.setMaterial(materialX);
        coneX.getTransforms().addAll(new Translate(xAxis.getHeight() + coneX.getHeight(), 0, 0), xRotate);

        ConeMesh coneY = new ConeMesh(1f, 1.5f);
        coneY.setMaterial(materialY);
        coneY.getTransforms().add(new Translate(0, -yAxis.getHeight() - coneY.getHeight(), 0));

        ConeMesh coneZ = new ConeMesh(1f, 1.5f);
        coneZ.setMaterial(materialZ);
        coneZ.getTransforms().addAll(new Translate(0, 0, zAxis.getHeight() + coneZ.getHeight()), zRotate);

        getChildren().addAll(xAxis, yAxis, zAxis, coneX, coneY, coneZ);
        getTransforms().add(new Scale(scale, scale, scale));
    }

}
