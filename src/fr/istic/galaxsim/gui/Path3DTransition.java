package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.data.Coordinate;
import fr.istic.galaxsim.data.CosmosElement;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Point3D;
import javafx.scene.shape.Shape3D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Path3DTransition extends Transition {

    private final Shape3D shape;

    private final Coordinate initialPosition;
    private final double totalDistance;
    private final ArrayList<Point3D> points = new ArrayList<>();
    private Queue<Point3D> targets = new ConcurrentLinkedQueue<>();

    private double speed;

    private Point3D currentTarget;
    private double lastDistance = 0.0;

    public Path3DTransition(double duration, Shape3D shape, CosmosElement element) {
        this.shape = shape;
        double totalDistanceTemp = 0.0;

        initialPosition = element.getCoordinate(0);
        Coordinate lastCoord = initialPosition;

        for(int i = 2;i < element.getSizeCoordinate();i++) {
            Coordinate coord = element.getCoordinate(i);
            Point3D p = new Point3D(coord.getX(), coord.getY(), coord.getZ());

            totalDistanceTemp += p.distance(lastCoord.getX(), lastCoord.getY(), lastCoord.getZ());
            lastCoord = coord;
            points.add(p);
            targets.add(p);
        }
        this.totalDistance = totalDistanceTemp;

        speed = totalDistance / duration;

        if(!targets.isEmpty()) {
            currentTarget = targets.remove();
            lastDistance = new Point3D(initialPosition.getX(), initialPosition.getY(), initialPosition.getZ()).distance(currentTarget);
        }

        setCycleDuration(Duration.seconds(duration));
        setInterpolator(Interpolator.LINEAR);
    }

    private Point3D getShapePosition() {
        return new Point3D(shape.getTranslateX(), shape.getTranslateY(), shape.getTranslateZ());
    }

    @Override
    protected void interpolate(double v) {
        Point3D shapePosition = getShapePosition();
        double d = shapePosition.distance(currentTarget) - 1.0;
        if(lastDistance > d) {
            Point3D direction = currentTarget.subtract(shapePosition).normalize();
            direction.multiply(speed);

            shape.setTranslateX(shape.getTranslateX() + direction.getX());
            shape.setTranslateY(shape.getTranslateY() + direction.getY());
            shape.setTranslateZ(shape.getTranslateZ() + direction.getZ());
            lastDistance = d;
        }
        else {
            if(!targets.isEmpty()) {
                currentTarget = targets.remove();
                lastDistance = currentTarget.distance(shapePosition);
            }
        }
    }

    public void resetInitialPosition() {
        shape.setTranslateX(initialPosition.getX());
        shape.setTranslateY(initialPosition.getY());
        shape.setTranslateZ(initialPosition.getZ());
    }

    public void resetTargets() {
        for(int i = 1;i < points.size();i++) {
            targets.add(points.get(i));
        }
    }

    public void setTransitionPosition(double t) {
        double distanceFromStart = t / speed;

        double d = 0.0;
        Point3D lastPoint = new Point3D(initialPosition.getX(), initialPosition.getY(), initialPosition.getZ());
        Point3D position = lastPoint;
        for(Point3D p : targets) {
            d += lastPoint.distance(p);
            lastPoint = p;

            if(d >= distanceFromStart) {
                position = lastPoint;
                break;
            }
        }

        shape.setTranslateX(position.getX());
        shape.setTranslateY(position.getY());
        shape.setTranslateZ(position.getZ());
    }
}
